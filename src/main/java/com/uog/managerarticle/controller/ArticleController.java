package com.uog.managerarticle.controller;

import com.uog.managerarticle.entity.*;
import com.uog.managerarticle.service.*;

import com.uog.managerarticle.user.CustomUserDetail;
import com.uog.managerarticle.user.UserInfor;
import com.uog.managerarticle.utils.EmailUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IStudentService studentService;

    @Autowired
    private ITopicService topicService;

    @Autowired
    private ICoordinatorService coordinatorService;

    @Autowired
    private EmailUntil emailUntil;

    @Autowired
    private IGuestService guestService;

    @GetMapping("/list")
    public String showAllAcceptedArticle(Model model) {
        model.addAttribute("articles", articleService.findAllByStatus(1));
        return "index";
    }

//    @GetMapping("/post/{code}/edit")
//    public String editArticle(@PathVariable("code") String topicCode, Model model, RedirectAttributes ra) throws Exception {
//        TopicEntity topicEntity = topicService.findByCode(topicCode);
//        Date now = new Date();
//        if(now.after(topicEntity.getDeadline())){
//            ra.addFlashAttribute("message","Deadline has passed so you can not submit article");
//            return "redirect:/article/post/"+topicCode;
//        }
//        ArticleEntity article = new ArticleEntity();
//        article.setTopic(topicEntity);
//        model.addAttribute("topicEntity", topicEntity);
//        model.addAttribute("article", article);
//        return "article-student-post";
//    }

    @GetMapping("/post/{code}")
    public String showPostOfStudentByTopic(@PathVariable("code") String topicCode, Model model) throws Exception {
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        StudentEntity student = studentService.findByEmail(userDetail.getUsername());
        if (student != null) {
            model.addAttribute("articles", articleService.findAllByStudentIdAndTopicCode(student.getId(), topicCode));
            model.addAttribute("topic", topicService.findByCode(topicCode));
        }else{
            throw new Exception("Not Found");
        }

        TopicEntity topicEntity = topicService.findByCode(topicCode);
        Date now = new Date();
        ArticleEntity article = new ArticleEntity();
        article.setTopic(topicEntity);
        model.addAttribute("topicEntity", topicEntity);
        model.addAttribute("article", article);

        return "article/article-student-post";
    }

    @GetMapping("/manager/{code}")
    public String managerArticle(@PathVariable("code") String topicCode, Model model) throws Exception {
        CustomUserDetail entity = UserInfor.getPrincipal();
        if (entity != null && entity.getPosition().equals("coordinator")) {
            MarketingCoordinatorEntity coordinator = coordinatorService.findByEmail(entity.getUsername());
            model.addAttribute("articlesActive", articleService.findByFacultyAndTopicAndStatus(coordinator.getFaculty().getCode(), topicCode, 1));
            model.addAttribute("articlesEnable", articleService.findByFacultyAndTopicAndStatus(coordinator.getFaculty().getCode(), topicCode, 0));
            model.addAttribute("newArticle", articleService.findByFacultyAndTopicAndStatus(coordinator.getFaculty().getCode(), topicCode, -1));
            model.addAttribute("topic", topicService.findByCode(topicCode));
            return "article/article-manager";
        } else {
            throw new Exception("Not Found");
        }
    }

    @GetMapping("/post/{code}/edit/{id}")
    public String updateArticle(@PathVariable("code") String topicCode, @PathVariable("id") Long id, Model model, RedirectAttributes ra) throws Exception {
        ArticleEntity article = articleService.findById(id);
        Date now = new Date();
        if(now.before(article.getTopic().getCloseDate())){
            ra.addFlashAttribute("message","Closure date has passed so you can not submit article");
            return "redirect:/article/post/"+topicCode;
        }
        model.addAttribute("article", article);
        return "article/update";
    }


    @PostMapping("/post/update")
    public String Update(HttpServletRequest request, @RequestParam("fileEditUpload") MultipartFile fileUpload,
                         @RequestParam("fileEditImage") MultipartFile fileImage) throws Exception {
        String id = request.getParameter("id");
        String title = request.getParameter("title");
        ArticleEntity entity = new ArticleEntity();
        entity.setId(Long.parseLong(id));
        entity.setTitle(title);
        entity = articleService.update(entity, fileUpload, fileImage);
        String message = "<p>Student " + entity.getStudent().getEmail() + " have updated a new article on topic " + entity.getTopic().getName() + "</p>";
        List<MarketingCoordinatorEntity> coordinators = coordinatorService.findByFacultyId(entity.getStudent().getFaculty().getId());
        for (MarketingCoordinatorEntity coordinatorEntity:coordinators){
            emailUntil.sendEmailNotification(coordinatorEntity.getEmail(), message);
        }
        return "redirect:/article/post/" + entity.getTopic().getCode();
    }


    @PostMapping("/post/upload")
    public String save(@Valid @ModelAttribute("article") ArticleEntity article,
                       @Param("fileUpload") MultipartFile fileUpload,
                       @Param("fileImage") MultipartFile fileImage,
                        RedirectAttributes ra, Model model) throws Exception {

        try {
            if (article.getId() == null) {
                ArticleEntity articleSaved = articleService.save(article, fileUpload, fileImage);
                String message = "<p>Student " + articleSaved.getStudent().getEmail() + " have submitted a new article on topic " + articleSaved.getTopic().getName() + "</p>";
                List<MarketingCoordinatorEntity> coordinators = coordinatorService.findByFacultyId(articleSaved.getStudent().getFaculty().getId());
                for (MarketingCoordinatorEntity coordinatorEntity:coordinators){
                    emailUntil.sendEmailNotification(coordinatorEntity.getEmail(), message);
                }
            } else {
                articleService.update(article, fileUpload, fileImage);
            }

        } catch (IOException e) {
            model.addAttribute("article", new ArticleEntity());
            model.addAttribute("topic", topicService.findByCode(article.getTopic().getCode()));
            model.addAttribute("message", "Can not add or update article");
            return "article-student-post";
        }
        ra.addFlashAttribute("message", "Submit article successfully");
        return "redirect:/article/post/" + article.getTopic().getCode();
    }

    @GetMapping("/download")
    public void download(@Param("id") Long id, HttpServletResponse response) throws Exception {
        ArticleEntity articleEntity = articleService.findById(id);
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + articleEntity.getName();

        response.setHeader(headerKey, headerValue);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(articleEntity.getContent());
        outputStream.close();

    }

    @GetMapping("manager/status/{code}/{id}/{status}")
    public String changeStatus(@PathVariable("status") Integer status, @PathVariable("id") Long id,
                               @PathVariable("code") String code, RedirectAttributes ra) {
        try {
            articleService.changeStatus(id, status);
            ra.addFlashAttribute("message", "Your action success");
        } catch (Exception e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/article/detail/" + id;
    }

    @RequestMapping("/delete/{code}/{id}")
    public String deleteArticle(@PathVariable("id") Long id, @PathVariable("code") String code, RedirectAttributes ra) {
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        try {
            articleService.delete(id);
            ra.addFlashAttribute("message", "Delete success");
        } catch (Exception e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        if (userDetail.getPosition().equals("student")) {
            return "redirect:/article/post/" + code;
        }
        return "redirect:/article/list";
    }




    @PostMapping("/manager/comment")
    public String commentArticle(@RequestParam("id") Long articleId,
                                 @RequestParam("comment") String comment,
                                 RedirectAttributes ra) {
        try {
            ArticleEntity articleEntity = articleService.saveComment(articleId, comment);
            ra.addFlashAttribute("message", "Comment Success");
            return "redirect:/article/detail/" + articleEntity.getId();
        } catch (Exception e) {
            ra.addFlashAttribute("messageError", e.getMessage());
        }
        return "redirect:/topic/manager";

    }

    @RequestMapping("/detail/{id}")
    public String detailArticle(@PathVariable("id") Long id, Model model) throws Exception {
        ArticleEntity articleEntity = articleService.findById(id);
        final SimpleDateFormat df = new SimpleDateFormat( "dd-MM-yyyy" );
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(articleEntity.getCreatedDate());
        calendar.add(GregorianCalendar.DATE,14);
        Date date = df.parse(df.format(calendar.getTime()));
        model.addAttribute("disableCommentDate",date);
        model.addAttribute("article", articleEntity);
        return "article/detail";
    }

    @RequestMapping("/guest")
    public String findAllForGuest(Model model) {
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        GuestEntity guestEntity = guestService.findByUserName(userDetail.getUsername());
        System.out.println(guestEntity.getEmail());
        System.out.println(guestEntity.getFaculty().getId());
        Iterable<ArticleEntity> list = articleService.findAllForGuest(guestEntity.getFaculty().getId());
        model.addAttribute("articles", list);
        return "index";
    }

    @GetMapping("/statistics-report/{id}")
    public String findArticleForStatisticsByFaculty(@PathVariable("id") String id, Model model){
        model.addAttribute("acceptedArticle",articleService.findAllAcceptedArticleByFaculty(id));
        model.addAttribute("rejectedArticle",articleService.findAllRejectedArticleByFaculty(id));
        return "article/statistic-report";
    }

    @GetMapping("/exception-report/{id}")
    public String findArticleForExceptionReport(@PathVariable("id") String id, Model model) throws ParseException {
        model.addAttribute("articlesNoCom",articleService.findAllArticleWithoutCommentByFaculty(id));
        model.addAttribute("articlesNoComAf14",articleService.findAllArticleWithoutCommentAfter14DayByFaculty(id));
        return "article/exception-report";
    }


}
