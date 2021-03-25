package com.uog.managerarticle.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "marketing_manager")
public class MarketingManagerEntity {
    @Id
    @Column(name = "id")
    @NotNull(message = "Please input ID")
    private String id;

    @Column(name = "name")
    @NotNull(message = "Please input Name")
    private String name;

    @NotNull(message = "Please input Email")
    @Email(message = "Email Invalid")
    @Column(name = "email")
    private String email;

    @NotNull(message = "Please input address")
    private String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Please input date of birth")
    private Date dob;

    @NotNull(message = "Please input phone number")
    private Integer phone;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private AccountEntity accountEntity;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }
}
