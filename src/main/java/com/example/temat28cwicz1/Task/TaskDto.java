package com.example.temat28cwicz1.Task;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
public class TaskDto {

    private Long id;
    private String title;
    private String description;
    private LocalDate created;
    private LocalDate deadLine;
    private boolean finished;
}
