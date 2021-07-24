package com.leverx.menagerie.dto.request.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PetUpdateRequestDTO {

    @JsonProperty("ID")
    private String id;

    private String name;

    private Integer age;

    private Boolean isAlive;

    private String owner_ID;
}
