package com.example.course.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "tb_foundObject")
public class FoundObject implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String placeIfce;
    private String urlImage;
    private boolean deliverReception;
    private boolean deliveredOwner;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Instant instant = Instant.now();

    @ManyToOne
    @JoinColumn(name = "fk_found_user_id")
    private User foundUser;

    public FoundObject() {
    }

    public FoundObject(Long id, String name, String placeIfce, String urlImage, boolean deliverReception, boolean deliveredOwner, User user) {
        super();
        this.id = id;
        this.name = name;
        this.placeIfce = placeIfce;
        this.urlImage = urlImage;
        this.deliverReception = deliverReception;
        this.deliveredOwner = deliveredOwner;
        this.foundUser = user;
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

    public String getPlaceIfce() {
        return placeIfce;
    }

    public void setPlaceIfce(String placeIfce) {
        this.placeIfce = placeIfce;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public void setDeliverReception(boolean deliverReception) {
        this.deliverReception = deliverReception;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    public User getFoundUser() {
        return foundUser;
    }

    public void setFoundUser(User foundUser) {
        this.foundUser = foundUser;
    }

    public void setDeliveredOwner(boolean deliveredOwner) {
        this.deliveredOwner = deliveredOwner;
    }


    public boolean isDeliverReception() {
        return deliverReception;
    }


    public boolean isDeliveredOwner() {
        return deliveredOwner;
    }


}
