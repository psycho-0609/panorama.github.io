package com.uog.managerarticle.controller;

import com.uog.managerarticle.entity.FacultyEntity;
import com.uog.managerarticle.service.IFacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.ParseException;

@Controller
@RequestMapping("/faculty")
public class FacultyController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @Autowired
    private IFacultyService facultyService;

    @GetMapping("/statistics-report")
    public String report(Model model){
        model.addAttribute("faculties",facultyService.findAllForStatisticsReportByFaculty());
        return "faculty/report";
    }

    @GetMapping("/exception-report")
    public String exceptionReport(Model model) throws ParseException {
        model.addAttribute("faculties",facultyService.findAllForExceptionReport());
        return "faculty/exception-report";
    }

    @GetMapping("/list")
    public String showAll(Model model){
        model.addAttribute("faculties",facultyService.findAll());
        return "faculty/list";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam(value = "id", required = false) String id, Model model) throws Exception {
        FacultyEntity facultyEntity = null;
        if(id == null){
            facultyEntity = new FacultyEntity();
            model.addAttribute("faculty",facultyEntity);
            return "faculty/add";

        }else{
            facultyEntity = facultyService.findById(id);
            model.addAttribute("faculty",facultyEntity);
            return "faculty/update";
        }
    }

    @PostMapping("/add")
    public String save(@Valid @ModelAttribute("faculty") FacultyEntity facultyEntity, BindingResult bindingResult, RedirectAttributes ra, Model model){
        if(bindingResult.hasErrors()){
            return "faculty/add";
        }

        try {
            facultyService.findById(facultyEntity.getId());
            model.addAttribute("message", "Faculty ID existed");
            return "faculty/add";

        }catch (Exception e){
            FacultyEntity facultySaved = facultyService.save(facultyEntity);
            ra.addFlashAttribute("message","Add faculty successfully");
        }

        return "redirect:/faculty/list";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("faculty") FacultyEntity facultyEntity, BindingResult bindingResult, RedirectAttributes ra) throws Exception {
        if(bindingResult.hasErrors()){
            return "faculty/update";
        }
        facultyService.update(facultyEntity);
        return "redirect:/faculty/list";

    }

    @GetMapping("/delete/{id}")
    public String deleteFaculty(@PathVariable("id") String id, RedirectAttributes ra) throws Exception {
        facultyService.delete(id);
        ra.addFlashAttribute("message","Delete Successfully");
        return "redirect:/faculty/list";
    }

}
