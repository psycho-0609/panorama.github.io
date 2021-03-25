package com.uog.managerarticle.dto;

import java.util.Date;

public class TopicDtoReport {

    private Long id;
    private String name;
    private String code;
    private Long totalArticle;
    private Date deadline;
    private Date finalCloseDate;
    private Long acceptedArticle;
    private Long rejectedArticle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getTotalArticle() {
        return totalArticle;
    }

    public void setTotalArticle(Long totalArticle) {
        this.totalArticle = totalArticle;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getFinalCloseDate() {
        return finalCloseDate;
    }

    public void setFinalCloseDate(Date finalCloseDate) {
        this.finalCloseDate = finalCloseDate;
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
}
