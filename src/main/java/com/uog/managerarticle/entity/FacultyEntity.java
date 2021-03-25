package com.uog.managerarticle.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "faculty")
public class FacultyEntity {

    @Id
    @Column(name = "id")
    @NotBlank(message = "is required")
    private String id;

    @Column(name = "name")
    @NotBlank(message = "is required")
    private String name;

    @Column(name = "code")
    private String code;

    @OneToMany(mappedBy = "faculty")
    private Set<StudentEntity> students;

    @OneToMany(mappedBy = "faculty")
    private List<MarketingCoordinatorEntity> marketingCoordinators;

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

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setFacultyCode(String code) {
        this.code = code;
    }

    public Set<StudentEntity> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentEntity> students) {
        this.students = students;
    }

    public List<MarketingCoordinatorEntity> getMarketingCoordinators() {
        return marketingCoordinators;
    }

    public void setMarketingCoordinators(List<MarketingCoordinatorEntity> marketingCoordinators) {
        this.marketingCoordinators = marketingCoordinators;
    }
}
