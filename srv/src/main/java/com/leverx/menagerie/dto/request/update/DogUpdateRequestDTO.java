package com.leverx.menagerie.dto.request.update;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class DogUpdateRequestDTO extends PetUpdateRequestDTO {

    private Boolean noseIsDry;
}
