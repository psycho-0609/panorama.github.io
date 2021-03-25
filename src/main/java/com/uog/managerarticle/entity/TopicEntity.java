package com.uog.managerarticle.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "topic")
public class TopicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotNull(message = "Please input Name")
    private String name;

    @Column(name = "code", nullable = false)
    @NotNull(message = "Please input Sub-name")
    private String code;

    @Column(name = "deadline")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Please input Closure Date")
    private Date deadline;

    @Column(name = "close_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Please input Final Closure Date")
    private Date closeDate;

    private String description;



    @OneToMany(mappedBy = "topic",cascade = CascadeType.ALL)
    private Set<ArticleEntity> articles;

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


    public Set<ArticleEntity> getArticles() {
        return articles;
    }

    public void setArticles(Set<ArticleEntity> articles) {
        this.articles = articles;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
