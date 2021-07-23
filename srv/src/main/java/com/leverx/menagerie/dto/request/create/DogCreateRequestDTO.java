package com.leverx.menagerie.dto.request.create;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class DogCreateRequestDTO {

    private String name;

    private Integer age;

    private Boolean isAlive;

    private String gender;

    private String animalKind;

    private String owner_ID;

    private Boolean noseIsDry;
}
