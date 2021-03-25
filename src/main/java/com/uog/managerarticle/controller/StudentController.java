package com.uog.managerarticle.controller;

import com.uog.managerarticle.entity.ArticleEntity;
import com.uog.managerarticle.entity.StudentEntity;
import com.uog.managerarticle.service.IArticleService;
import com.uog.managerarticle.service.IFacultyService;
import com.uog.managerarticle.service.IStudentService;
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
        if (bindingResult.hasErrors()) {
            model.addAttribute("faculties", facultyService.findAll());
            return "student/edit";
        }
        try {
            studentService.findById(studentEntity.getId());
            ra.addFlashAttribute("message","Student existed");
        }catch (Exception ex){
            studentService.save(studentEntity);
            ra.addFlashAttribute("message", "add student success");
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
        return "student/student-article";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") String id) throws Exception {
        studentService.delete(id);
        return "redirect:/student/list";
    }

}
