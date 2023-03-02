package com.example.course.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@EqualsAndHashCode
@Entity
@Table(name = "tb_lostObject")
public class LostObject implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String placeIfce;
    private boolean deliverReception;
    private boolean deliveredOwner;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "UTC")
    private Instant instant = Instant.now();

    @ManyToOne
    @JoinColumn(name = "fk_user_id")
    private User lostUser;

    public LostObject() {
    }

    public LostObject(Long id, String name, String placeIfce, String urlImage, boolean deliverReception, String contactNumber, boolean deliveredOwner, User user) {
        super();
        this.id = id;
        this.name = name;
        this.placeIfce = placeIfce;
        this.deliverReception = deliverReception;
        this.deliveredOwner = deliveredOwner;
        this.lostUser = user;
    }

    public Long getId() {
        return id;
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

    public void setDeliverReception(boolean deliverReception) {
        this.deliverReception = deliverReception;
    }

    public void setDeliveredOwner(boolean deliveredOwner) {
        this.deliveredOwner = deliveredOwner;
    }

    public void setLostUser(User lostUser) {
        this.lostUser = lostUser;
    }

    public boolean isDeliverReception() {
        return deliverReception;
    }

    public boolean isDeliveredOwner() {
        return deliveredOwner;
    }

}
