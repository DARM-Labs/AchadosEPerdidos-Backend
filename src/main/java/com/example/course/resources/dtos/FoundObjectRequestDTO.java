package com.example.course.resources.dtos;

import lombok.Getter;

@Getter
public class FoundObjectRequestDTO {
    private String name;
    private String placeIfce;
    private String urlImage;
    private boolean deliverReception;
    private boolean deliveredOwner;

}
