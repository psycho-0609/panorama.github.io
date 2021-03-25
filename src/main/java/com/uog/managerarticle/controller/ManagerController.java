package com.uog.managerarticle.controller;

import com.uog.managerarticle.entity.MarketingManagerEntity;
import com.uog.managerarticle.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("manager")
public class ManagerController {

    @Autowired
    private IManagerService managerService;

    @GetMapping("/list")
    public String showAll(Model model){
        model.addAttribute("managers",managerService.findAll());
        return "/manager/list";
    }

    @GetMapping("/edit")
    public String addManager(Model model){
        MarketingManagerEntity entity = new MarketingManagerEntity();
        model.addAttribute("manager",entity);
        return "/manager/edit";
    }
    @GetMapping("/edit/{id}")
    public String editManager(@PathVariable("id") String id, Model model) throws Exception {
        MarketingManagerEntity entity = managerService.findById(id);
        model.addAttribute("manager",entity);
        return "/manager/edit";
    }

    @PostMapping("/save")
    public String save(MarketingManagerEntity managerEntity, RedirectAttributes ra){
        try {
            managerService.findById(managerEntity.getId());
            ra.addFlashAttribute("message","Id existed");
        }catch (Exception exception){
            managerService.save(managerEntity);
            ra.addFlashAttribute("message","Add manager successfully");
        }

        return "redirect:/manager/list";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("manager") MarketingManagerEntity managerEntity, RedirectAttributes ra) throws Exception {
        managerService.update(managerEntity);
        ra.addFlashAttribute("message","Update successfully");
        return "redirect:/manager/list";
    }
}
