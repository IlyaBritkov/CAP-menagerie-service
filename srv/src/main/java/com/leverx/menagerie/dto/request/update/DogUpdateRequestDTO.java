package com.leverx.menagerie.dto.request.update;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class DogUpdateRequestDTO extends PetUpdateRequestDTO {

    private Boolean noseIsDry;
}
