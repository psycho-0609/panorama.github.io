package com.uog.managerarticle.controller;

import com.uog.managerarticle.entity.AccountEntity;
import com.uog.managerarticle.entity.MarketingCoordinatorEntity;
import com.uog.managerarticle.service.IAccountService;
import com.uog.managerarticle.service.ICoordinatorService;
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

@Controller
@RequestMapping("coordinator")
public class CoordinatorController {

    @Autowired
    private ICoordinatorService coordinatorService;

    @Autowired
    private IFacultyService facultyService;

    @Autowired
    private IAccountService accountService;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
    @GetMapping("/list")
    public String list(Model model){
        model.addAttribute("coordinators",coordinatorService.findAll());
        return "coordinator/list";
    }

    @GetMapping("/edit")
    public String addCoordinator(Model model){
        MarketingCoordinatorEntity entity = new MarketingCoordinatorEntity();
        model.addAttribute("coordinator",entity);
        model.addAttribute("faculties",facultyService.findAll());
        return "coordinator/add";
    }

    @GetMapping("/edit/{id}")
    public String updateCoordinator(@PathVariable("id") String id, Model model) throws Exception {
        MarketingCoordinatorEntity coordinatorEntity = coordinatorService.findById(id);
        model.addAttribute("coordinator",coordinatorEntity);
        model.addAttribute("faculties",facultyService.findAll());
        return "coordinator/edit";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("coordinator") MarketingCoordinatorEntity entity, BindingResult bindingResult, RedirectAttributes ra, Model  model){
        model.addAttribute("faculties", facultyService.findAll());
        if (bindingResult.hasErrors()){
            model.addAttribute("faculties",facultyService.findAll());
            return "coordinator/add";
        }

        AccountEntity userEntity = accountService.findAccountByUserName(entity.getEmail());
        try {
            coordinatorService.findById(entity.getId());
            model.addAttribute("message","Coordinator ID existed.");
            return "coordinator/add";
        }catch (Exception ex){
            if(userEntity != null) {
                model.addAttribute("message", "Email existed.");
                return "coordinator/add";
            }
            coordinatorService.addNew(entity);
            ra.addFlashAttribute("message", "Add Coordinator successfully.");
        }

        return "redirect:/coordinator/list";
    }
    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("coordinator") MarketingCoordinatorEntity entity, BindingResult bindingResult, RedirectAttributes ra, Model  model) throws Exception {
        if (bindingResult.hasErrors()){
            model.addAttribute("faculties",facultyService.findAll());
            return "coordinator/edit";
        }
        coordinatorService.update(entity);
        ra.addFlashAttribute("message","Update Successfully");
        return "redirect:list";
    }

    @PostMapping("/saveedit")
    public String saveedit (@Valid @ModelAttribute("coordinator") MarketingCoordinatorEntity entity, BindingResult bindingResult, RedirectAttributes ra, Model  model) throws Exception {
        if (bindingResult.hasErrors()){
            return "redirect:/user/profile";
        }
        coordinatorService.update(entity);
        ra.addFlashAttribute("message","Update Successfully");
        return "redirect:/user/profile";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) throws Exception {
        coordinatorService.delete(id);
        return "redirect:/coordinator/list";
    }
}
