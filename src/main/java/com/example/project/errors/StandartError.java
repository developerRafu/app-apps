package com.example.project.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StandartError {
    private String msg;
    private Integer cod;
    private Long timestamp;
}
