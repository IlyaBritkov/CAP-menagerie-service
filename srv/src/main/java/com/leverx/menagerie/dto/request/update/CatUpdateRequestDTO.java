package com.leverx.menagerie.dto.request.update;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class CatUpdateRequestDTO extends PetUpdateRequestDTO {

    private Boolean isCastrated;
}
