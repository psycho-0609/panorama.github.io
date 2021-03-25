package com.uog.managerarticle.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "student")
public class StudentEntity {

    @Id
    @Column(name = "id")
    @NotNull(message = "Please input ID")
    private String id;

    @Column(name = "name", nullable = false)
    @NotNull(message = "Please input Name Student")
    private String name;

    @Column(name = "email", unique = true, nullable = false)
    @Email(message = "Email invalid")
    @NotNull(message = "Please input email")
    private String email;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private AccountEntity accountEntity;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private Set<ArticleEntity> articles;

    @NotNull(message = "Please input phone")
    private Integer phone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Please input date of birth")
    private Date dob;

    @NotNull(message = "Please input address")
    private String address;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private FacultyEntity faculty;

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

    public AccountEntity getAccountEntity() {
        return accountEntity;
    }

    public void setAccountEntity(AccountEntity accountEntity) {
        this.accountEntity = accountEntity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<ArticleEntity> getArticles() {
        return articles;
    }

    public void setArticles(Set<ArticleEntity> articles) {
        this.articles = articles;
    }

    public FacultyEntity getFaculty() {
        return faculty;
    }

    public void setFaculty(FacultyEntity faculty) {
        this.faculty = faculty;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
