package com.uog.managerarticle.controller;

import com.uog.managerarticle.entity.AccountEntity;
import com.uog.managerarticle.entity.GuestEntity;
import com.uog.managerarticle.entity.StudentEntity;
import com.uog.managerarticle.repository.GuestRepository;
import com.uog.managerarticle.service.IAccountService;
import com.uog.managerarticle.service.IFacultyService;
import com.uog.managerarticle.service.IGuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/guest")
public class GuestController {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @Autowired
    private IGuestService guestService;

    @Autowired
    private IFacultyService facultyService;

    @Autowired
    private IAccountService accountService;

    @GetMapping("/list")
    public String findAll(Model model) {
        model.addAttribute("guests", guestService.findAll());
        return "guest/list";
    }

    @GetMapping("/edit")
    public String addGuest(Model model, @RequestParam(value = "id", required = false) String id) {
        model.addAttribute("guest", new GuestEntity());
        model.addAttribute("faculties", facultyService.findAll());
        return "guest/add";
    }

    @GetMapping(value = {"/edit/{id}"})
    public String update(@PathVariable(value = "id") String id, Model model) throws Exception {
        GuestEntity guestEntity = guestEntity = guestService.findById(id);
        model.addAttribute("guest", guestEntity);
        model.addAttribute("faculties", facultyService.findAll());
        return "guest/edit";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("guest") GuestEntity guestEntity, BindingResult bindingResult, RedirectAttributes ra, Model model) {
        System.out.println(guestEntity.getId());
        model.addAttribute("faculties", facultyService.findAll());
        if (bindingResult.hasErrors()) {
            model.addAttribute("faculties", facultyService.findAll());
            return "guest/add";
        }
//        GuestEntity entity = guestService.findByEmail(guestEntity.getEmail());
        AccountEntity entity = accountService.findAccountByUserName(guestEntity.getEmail());
        try {
            guestService.findById(guestEntity.getId());
            model.addAttribute("message", "Guest ID existed");
            return "guest/add";
        } catch (Exception ex) {
            if (entity != null) {
                model.addAttribute("message", "Email existed");
                return "guest/add";
            }
            guestService.save(guestEntity);
            ra.addFlashAttribute("message", "Add Guest success");
        }
        return "redirect:/guest/list";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("guest") GuestEntity guestEntity, BindingResult bindingResult, RedirectAttributes ra, Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            model.addAttribute("faculties", facultyService.findAll());
            return "redirect:edit/" + guestEntity.getId();
        }
        guestService.update(guestEntity);
        ra.addFlashAttribute("message", "Update succesfully.");
        return "redirect:list";
    }

    @PostMapping("/saveedit")
    public String saveedit(@Valid @ModelAttribute("guest") GuestEntity user, BindingResult bindingResult, RedirectAttributes ra, Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            return "redirect:/user/profile";
        }
        System.out.println(user);
        guestService.update(user);
        System.out.println("123");
        return "redirect:/user/profile";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) throws Exception {
        guestService.delete(id);
        return "redirect:/guest/list";
    }
}
