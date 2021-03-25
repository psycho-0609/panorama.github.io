package com.uog.managerarticle.controller;

import com.uog.managerarticle.entity.GuestEntity;
import com.uog.managerarticle.service.IFacultyService;
import com.uog.managerarticle.service.IGuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("guest")
public class GuestController {

    @Autowired
    private IGuestService guestService;
    @Autowired
    private IFacultyService facultyService;

    @GetMapping("/list")
    public String findAll(Model model){
        model.addAttribute("guests",guestService.findAll());
        return "/guest/list";
    }

    @GetMapping("/edit")
    public String addGuest(Model model, @RequestParam(value = "id",required = false) String id){
        model.addAttribute("guest",new GuestEntity());
        model.addAttribute("faculties",facultyService.findAll());
        return "/guest/add";
    }

    @GetMapping("/edit/{id}")
    public String addGuest(@PathVariable("id") String id, Model model) throws Exception {
        model.addAttribute("guest",guestService.findById(id));
        model.addAttribute("faculties",facultyService.findAll());
        return "/guest/update";
    }

    @PostMapping("/add")
    public String addGuest(@Valid @ModelAttribute("guest") GuestEntity guestEntity, BindingResult bindingResult, RedirectAttributes ra, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("faculties",facultyService.findAll());
            return "redirect:edit";
        }
        try {
            guestService.findById(guestEntity.getId());
            ra.addFlashAttribute("message","Id user existed");
        }catch (Exception ex){
           guestService.save(guestEntity);
        }
        return "redirect:list";
    }

    @PostMapping("/update")
    public String update (@Valid @ModelAttribute("guest") GuestEntity guestEntity, BindingResult bindingResult, RedirectAttributes ra, Model model) throws Exception {
        if(bindingResult.hasErrors()){
            model.addAttribute("faculties",facultyService.findAll());
            return "redirect:edit/"+guestEntity.getId();
        }
        try {
            guestService.findById(guestEntity.getId());
            ra.addFlashAttribute("message","Id user existed");
        }catch (Exception ex){
            guestService.update(guestEntity);
        }
        return "redirect:list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) throws Exception {
        guestService.delete(id);
        return "redirect:list";
    }
}
