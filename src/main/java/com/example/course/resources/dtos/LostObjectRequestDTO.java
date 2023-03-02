package com.example.course.resources.dtos;

import lombok.Getter;

@Getter
public class LostObjectRequestDTO {
    private String name;
    private String placeIfce;
    private boolean deliverReception;
    private boolean deliveredOwner;
}
