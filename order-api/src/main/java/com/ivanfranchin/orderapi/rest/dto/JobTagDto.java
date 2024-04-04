package com.ivanfranchin.orderapi.rest.dto;

import com.ivanfranchin.orderapi.model.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class JobTagDto {
    private Integer id;

    private String name;


}
