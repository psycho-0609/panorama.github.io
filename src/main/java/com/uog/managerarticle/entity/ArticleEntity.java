package com.uog.managerarticle.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "article")
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "size")
    private Long size;

    @Column(nullable = false)
    private byte[] content;

    @Column(name = "thumbnail")
    private String thumbnail;

    @CreatedDate
    @Column(name = "created_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdDate;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "status")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private StudentEntity student;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private TopicEntity topic;

    private String comment;

    public ArticleEntity() {
    }
    public ArticleEntity(Long id, String name, Long size, String thumbnail, String title, Integer status, StudentEntity student, TopicEntity topic, Date createdDate, String comment) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.thumbnail = thumbnail;
        this.title = title;
        this.status = status;
        this.student = student;
        this.topic = topic;
        this.createdDate = createdDate;
        this.comment = comment;
    }

    public TopicEntity getTopic() {
        return topic;
    }

    public void setTopic(TopicEntity topic) {
        this.topic = topic;
    }

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

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Transient
    public String getImagePath(){
        if(thumbnail == null|| id==null) return null;
        return "/file-article/" + id + "/" + thumbnail;
    }

    public String getFilePath(){
        if(name == null || id == null) return null;
        return "/file-article/"+id +"/"+name;
    }
}
