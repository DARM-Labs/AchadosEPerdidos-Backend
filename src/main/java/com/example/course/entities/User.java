package com.example.course.entities;

import com.example.course.entities.enuns.Office;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@EqualsAndHashCode
@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false)
    private String name;
    @Column(nullable=false)
    private String email;
    private Office userOffice;
    private String curso;
    private String semestre;
    private String matricula;
    @Column(nullable=false)
    private String senha;
    private String contactNumber;
    private String urlImageUser;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd", timezone = "UTC")
    private Instant instant = Instant.now();

    @JsonIgnore
    @OneToMany(mappedBy = "foundUser")
    private List<FoundObject> foundObjects = new ArrayList<>();
    @OneToMany(mappedBy = "lostUser")
    private List<LostObject> lostObjects = new ArrayList<>();

    public User() {
    }

    public User(String name, String email, String curso, String semestre, String matricula, String senha,String contactNumber, String urlImageUser, Office userOffice) {
        super();
        this.name = name;
        this.email = email;
        this.curso = curso;
        this.semestre = semestre;
        this.matricula = matricula;
        this.senha = senha;
        this.contactNumber = contactNumber;
        this.urlImageUser = urlImageUser;
        this.userOffice = userOffice;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getContactNumber() {
        return contactNumber;
    }
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    public String getUrlImageUser() {
        return urlImageUser;
    }
    public void setUrlImageUser(String urlImageUser) {
        this.urlImageUser = urlImageUser;
    }

    public Office getUserOffice() {
        return userOffice;
    }

    public void setUserOffice(Office userOffice) {
        this.userOffice = userOffice;
    }

}
