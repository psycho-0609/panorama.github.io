package com.uog.managerarticle.repository;

import com.uog.managerarticle.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TopicRepository extends JpaRepository<TopicEntity,Long> {

    Optional<TopicEntity> findByCode(String code);
}
