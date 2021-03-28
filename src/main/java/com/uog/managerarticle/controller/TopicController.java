package com.uog.managerarticle.controller;

import com.uog.managerarticle.entity.TopicEntity;
import com.uog.managerarticle.service.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
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
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    private ITopicService topicService;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/list")
    public String showAll(Model model){
        model.addAttribute("topics",topicService.findAll());
        return "topic/list";
    }
    @RequestMapping("/manager")
    public String managerArticle_1(Model model){
        List<TopicEntity> topicEntities = topicService.findAll();
        model.addAttribute("topics",topicEntities);
        return "topic/topic-manager";
    }

    @RequestMapping("/post")
    public String managerArticle_2(Model model){
        List<TopicEntity> topicEntities = topicService.findAll();
        model.addAttribute("topics",topicEntities);
        return "topic/topic-class";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam(value = "id",required = false) Long id, Model model) throws Exception {
        TopicEntity topicEntity = null;
        if(id == null){
            topicEntity = new TopicEntity();
            model.addAttribute("topic", topicEntity);
            return "/topic/add";
        } else {
            topicEntity = topicService.findById(id);
            model.addAttribute("topic", topicEntity);
            return "topic/edit";
        }
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("topic") TopicEntity topic, BindingResult bindingResult, RedirectAttributes ra, Model model){
        if(bindingResult.hasErrors()){
            return "topic/add";
        }

        try {
            topicService.findById(topic.getId());
            model.addAttribute("message", "Topic ID existed.");
            return "topic/add";
        } catch (Exception ex) {
            if(topic.getDeadline().after(topic.getCloseDate())) {
                model.addAttribute("message", "Closure Date must happen before Final Closure Date.");
                return "topic/add";
            }
            try {
                topicService.findByCode(topic.getCode());
                model.addAttribute("message", "Topic Sub-name existed.");
                return "topic/add";
            } catch (Exception e) {
                topicService.save(topic);
                ra.addFlashAttribute("message","Add Topic successfully.");
            }
        }
        return "redirect:/topic/manager";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("topic") TopicEntity topic, BindingResult bindingResult, RedirectAttributes ra, Model model) throws Exception {
        if(bindingResult.hasErrors()){
            return "topic/edit";
        }
        if(topic.getDeadline().after(topic.getCloseDate())) {
            model.addAttribute("message", "Closure Date must happen before Final Closure Date.");
            return "topic/add";
        }
        topicService.update(topic);
        ra.addFlashAttribute("message", "Update Topic successfully.");
        return "redirect:/topic/manager";
    }


    @GetMapping("/report")
    public String report(Model model){
        model.addAttribute("topics",topicService.findAllForReport());

        return "topic/report";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes ra) throws Exception {
        topicService.delete(id);
        ra.addFlashAttribute("message","Delete success");
        return "redirect:/topic/manager";
    }
}
