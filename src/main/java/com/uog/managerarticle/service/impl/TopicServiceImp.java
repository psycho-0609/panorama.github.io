package com.uog.managerarticle.service.impl;

import com.uog.managerarticle.dto.TopicDtoReport;
import com.uog.managerarticle.entity.StudentEntity;
import com.uog.managerarticle.entity.TopicEntity;
import com.uog.managerarticle.repository.ArticleRepository;
import com.uog.managerarticle.repository.TopicRepository;
import com.uog.managerarticle.service.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TopicServiceImp implements ITopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public List<TopicEntity> findAll() {
        return topicRepository.findAll();
    }

    @Override
    public TopicEntity findByCode(String code) throws Exception {
        Optional<TopicEntity> entity = topicRepository.findByCode(code);
        if (!entity.isPresent()){
            throw new Exception("Not Found Article");
        }
        return entity.get();
    }

    @Override
    public TopicEntity save(TopicEntity topicEntity) {
        return topicRepository.save(topicEntity);
    }

    @Override
    public TopicEntity findById(Long id) throws Exception {
        Optional<TopicEntity> entity = topicRepository.findById(id);
        if(entity.isPresent()){
            return entity.get();
        }else {
            throw new Exception("Not Found Topic");
        }
    }

    @Override
    public List<TopicDtoReport> findAllForReport() {
        List<TopicEntity> entities = topicRepository.findAll();
        List<TopicDtoReport> topics = new ArrayList<>();
        for (TopicEntity topic:entities){
            TopicDtoReport topicReport = new TopicDtoReport();
            topicReport.setId(topic.getId());
            topicReport.setName(topic.getName());
            topicReport.setCode(topic.getCode());
            topicReport.setTotalArticle(articleRepository.countAllByTopicId(topic.getId()));
            topicReport.setDeadline(topic.getDeadline());
            topicReport.setFinalCloseDate(topic.getCloseDate());
            topicReport.setAcceptedArticle(articleRepository.countAllByTopicIdAndStatus(topic.getId(),1));
            topicReport.setRejectedArticle(articleRepository.countAllByTopicIdAndStatus(topic.getId(),0));
            topics.add(topicReport);
        }
        return topics;
    }

    @Override
    public void delete(Long id) throws Exception {
        Optional<TopicEntity> entity = topicRepository.findById(id);
        if(!entity.isPresent()){
            throw new Exception("Not Found Topic");
        }
        topicRepository.delete(entity.get());
    }

    @Override
    public void update(TopicEntity topic) throws Exception {
        Optional<TopicEntity> entity = topicRepository.findById(topic.getId());
        if (!entity.isPresent()) {
            throw new Exception("Not Found");
        }
        topicRepository.save(topic);
    }
}
