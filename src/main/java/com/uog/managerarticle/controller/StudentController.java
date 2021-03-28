package com.uog.managerarticle.controller;

import com.uog.managerarticle.entity.AccountEntity;
import com.uog.managerarticle.entity.ArticleEntity;
import com.uog.managerarticle.entity.GuestEntity;
import com.uog.managerarticle.entity.StudentEntity;
import com.uog.managerarticle.service.*;
import com.uog.managerarticle.user.CustomUserDetail;
import com.uog.managerarticle.user.UserInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("student")
public class StudentController {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IStudentService studentService;

    @Autowired
    private IFacultyService facultyService;

    @Autowired
    private IAccountService accountService;

    @GetMapping("/list")
    public String showAllStudent(Model model) {
        model.addAttribute("students", studentService.findAll());
        return "student/list";
    }

    @GetMapping(value = {"/edit"})
    public String edit(@RequestParam(value = "id", required = false) String id, Model model) throws Exception {
        StudentEntity studentEntity = new StudentEntity();
        model.addAttribute("student", studentEntity);
        model.addAttribute("faculties", facultyService.findAll());
        return "student/add";
    }

    @GetMapping(value = {"/edit/{id}"})
    public String update(@PathVariable(value = "id") String id, Model model) throws Exception {
        StudentEntity studentEntity = studentEntity = studentService.findById(id);
        model.addAttribute("student", studentEntity);
        model.addAttribute("faculties", facultyService.findAll());
        return "student/edit";
    }


    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("student") StudentEntity studentEntity, BindingResult bindingResult, RedirectAttributes ra, Model model) {
        System.out.println(studentEntity.getId());
        model.addAttribute("faculties", facultyService.findAll());
        if (bindingResult.hasErrors()) {
            model.addAttribute("faculties", facultyService.findAll());
            return "student/add";
        }
//        StudentEntity entity = studentService.findByEmail(studentEntity.getEmail());
        AccountEntity entity = accountService.findAccountByUserName(studentEntity.getEmail());
        try {
            studentService.findById(studentEntity.getId());
            model.addAttribute("message","Student ID existed.");
            return "student/add";
        }catch (Exception ex){
            if(entity != null) {
                model.addAttribute("message", "Email existed.");
                return "student/add";
            }
            studentService.save(studentEntity);
            ra.addFlashAttribute("message", "Add Student successfully.");
        }
        return "redirect:/student/list";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("student") StudentEntity studentEntity, BindingResult bindingResult, RedirectAttributes ra, Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            model.addAttribute("faculties", facultyService.findAll());
            return "student/edit";
        }
        studentService.update(studentEntity);
        ra.addFlashAttribute("message", "update student success");
        return "redirect:/student/list";
    }

    @PostMapping("/saveedit")
    public String saveedit(@Valid @ModelAttribute("student") StudentEntity user, BindingResult bindingResult, RedirectAttributes ra, Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            return "redirect:/user/profile";
        }
        System.out.println(user);
        studentService.update(user);
        System.out.println("123");
        return "redirect:/user/profile";
    }

    @RequestMapping("/article/{id}")
    public String showArticleOfOneStudent(@PathVariable("id") String id, Model model) throws Exception {
        StudentEntity studentEntity = studentService.findById(id);
        Iterable<ArticleEntity> articles = articleService.finByStudentIdAndStatus(studentEntity.getId(), 1);
        model.addAttribute("articles", articles);
        model.addAttribute("student", studentEntity);
        return "student/student-article";
    }

    @RequestMapping("/article/list")
    public String listArticleOfStudent(Model model) {
        CustomUserDetail userDetail = UserInfor.getPrincipal();
        StudentEntity studentEntity = studentService.findByEmail(userDetail.getUsername());
        model.addAttribute("articles", articleService.findAllByStudentId(studentEntity.getId()));
        model.addAttribute("student", studentEntity);
        model.addAttribute("numberArticleActive", articleService.countAllByStatusAndStudentId(1, studentEntity.getId()));
        model.addAttribute("numberArticleDisable", articleService.countAllByStatusAndStudentId(0, studentEntity.getId()));
        model.addAttribute("articlesActive", articleService.findAllByStudentIdAndStatus(studentEntity.getId(), 1));
        model.addAttribute("articlesDisable", articleService.findAllByStudentIdAndStatus(studentEntity.getId(), 0));
        model.addAttribute("articlesNew", articleService.findAllByStudentIdAndStatus(studentEntity.getId(), -1));

        return "student/student-article";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") String id, RedirectAttributes ra) throws Exception {
        studentService.delete(id);
        ra.addFlashAttribute("message","Delete Successfully");
        return "redirect:/student/list";
    }

}
