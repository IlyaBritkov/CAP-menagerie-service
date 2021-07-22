package com.leverx.menagerie.dto.request.create;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CatCreateRequestDTO {
    private Integer ID;

    private String name;

    private Integer age;

    private Boolean isAlive;

    private String gender;

    private String animalKind;

    private Integer owner_ID;

    private Boolean isCastrated;
}
