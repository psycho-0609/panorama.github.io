package com.uog.managerarticle.service;

import com.uog.managerarticle.dto.TopicDtoReport;
import com.uog.managerarticle.entity.TopicEntity;

import java.util.List;
import java.util.Optional;

public interface ITopicService {

    List<TopicEntity> findAll();
    TopicEntity findByCode(String code) throws Exception;
    TopicEntity save(TopicEntity topicEntity);
    TopicEntity findById(Long id) throws Exception;

    List<TopicDtoReport> findAllForReport();

    void delete(Long id) throws Exception;

    void update(TopicEntity topic) throws Exception;
}
