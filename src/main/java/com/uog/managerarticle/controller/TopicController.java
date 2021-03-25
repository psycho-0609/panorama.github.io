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
@RequestMapping("topic")
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
    @RequestMapping({"/manager","/post"})
    public String managerArticle(Model model){
        List<TopicEntity> topicEntities = topicService.findAll();
        model.addAttribute("topics",topicEntities);
        return "topic/topic-manager";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam(value = "id",required = false) Long id, Model model){
        TopicEntity topicEntity = new TopicEntity();
        if(id != null){
            try {
                topicEntity = topicService.findById(id);
            }catch (Exception e){
                return "";
            }

        }
        model.addAttribute("topic",topicEntity);
        return "topic/edit";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("topic") TopicEntity topic, BindingResult bindingResult, RedirectAttributes ra){
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.getFieldError());
            return "topic/edit";
        }
        TopicEntity topicSaved = topicService.save(topic);
        if(topicSaved != null){
            ra.addFlashAttribute("message","add success");
        }
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
