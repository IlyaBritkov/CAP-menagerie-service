package com.leverx.menagerie.dto.request.create;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class CatCreateRequestDTO extends PetCreateRequestDTO {

    private Boolean isCastrated;
}
