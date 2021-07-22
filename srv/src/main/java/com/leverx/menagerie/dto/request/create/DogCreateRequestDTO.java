package com.leverx.menagerie.dto.request.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class DogCreateRequestDTO {
    @JsonProperty("ID")
    private Integer ID;

    private String name;

    private Integer age;

    private Boolean isAlive;

    private String gender;

    private String animalKind;

    private Integer owner_ID;

    private Boolean noseIsDry;
}
