package com.uog.managerarticle.dto;

import com.uog.managerarticle.entity.MarketingCoordinatorEntity;

import java.util.List;

public class FacultyDtoReport {

    private String id;
    private String name;
    private Long totalArticle;
    private Float percent;
    private Long acceptedArticle;
    private Long rejectedArticle;
    private List<MarketingCoordinatorEntity> coordinatorEntity;
    private Long totalArticleNoComment;
    private Integer totalArNoComAf14;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTotalArticle() {
        return totalArticle;
    }

    public void setTotalArticle(Long totalArticle) {
        this.totalArticle = totalArticle;
    }

    public Float getPercent() {
        return percent;
    }

    public void setPercent(Float percent) {
        this.percent = percent;
    }

    public Long getAcceptedArticle() {
        return acceptedArticle;
    }

    public void setAcceptedArticle(Long acceptedArticle) {
        this.acceptedArticle = acceptedArticle;
    }

    public Long getRejectedArticle() {
        return rejectedArticle;
    }

    public void setRejectedArticle(Long rejectedArticle) {
        this.rejectedArticle = rejectedArticle;
    }

    public List<MarketingCoordinatorEntity> getCoordinatorEntity() {
        return coordinatorEntity;
    }

    public void setCoordinatorEntity(List<MarketingCoordinatorEntity> coordinatorEntity) {
        this.coordinatorEntity = coordinatorEntity;
    }

    public Long getTotalArticleNoComment() {
        return totalArticleNoComment;
    }

    public void setTotalArticleNoComment(Long totalArticleNoComment) {
        this.totalArticleNoComment = totalArticleNoComment;
    }

    public Integer getTotalArNoComAf14() {
        return totalArNoComAf14;
    }

    public void setTotalArNoComAf14(Integer totalArNoComAf14) {
        this.totalArNoComAf14 = totalArNoComAf14;
    }
}
