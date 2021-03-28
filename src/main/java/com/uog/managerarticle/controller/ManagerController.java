package com.uog.managerarticle.controller;

import com.uog.managerarticle.entity.AccountEntity;
import com.uog.managerarticle.entity.MarketingManagerEntity;
import com.uog.managerarticle.service.IAccountService;
import com.uog.managerarticle.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @Autowired
    private IManagerService managerService;

    @Autowired
    private IAccountService accountService;

    @GetMapping("/list")
    public String showAll(Model model){
        model.addAttribute("managers",managerService.findAll());
        return "manager/list";
    }

    @GetMapping("/edit")
    public String addManager(Model model){
        MarketingManagerEntity entity = new MarketingManagerEntity();
        model.addAttribute("manager",entity);
        return "manager/add";
    }
    @GetMapping("/edit/{id}")
    public String editManager(@PathVariable("id") String id, Model model) throws Exception {
        MarketingManagerEntity entity = managerService.findById(id);
        model.addAttribute("manager",entity);
        return "manager/edit";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("manager") MarketingManagerEntity managerEntity, BindingResult bindingResult, RedirectAttributes ra, Model model){
        if(bindingResult.hasErrors()) {
            return "manager/add";
        }
        AccountEntity entity = accountService.findAccountByUserName(managerEntity.getEmail());
        try {
            managerService.findById(managerEntity.getId());
            model.addAttribute("message","Manager ID existed.");
            return "manager/add";
        }catch (Exception exception){
            if(entity != null) {
                model.addAttribute("message", "Email existed.");
                return "manager/add";
            }
            managerService.save(managerEntity);
            ra.addFlashAttribute("message","Add Manager successfully.");
        }

        return "redirect:/manager/list";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("manager") MarketingManagerEntity managerEntity, BindingResult bindingResult, RedirectAttributes ra,  Model model) throws Exception {
        if (bindingResult.hasErrors()){
            return "manager/edit";
        }
        managerService.update(managerEntity);
        ra.addFlashAttribute("message","Update successfully");
        return "redirect:/manager/list";
    }

    @PostMapping("/saveedit")
    public String saveedit(@ModelAttribute("manager") MarketingManagerEntity managerEntity, RedirectAttributes ra) throws Exception {
        managerService.update(managerEntity);
        ra.addFlashAttribute("message","Update successfully");
        return "redirect:/user/profile";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) throws Exception {
        managerService.delete(id);
        return "redirect:/manager/list";
    }
}
