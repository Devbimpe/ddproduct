package com.lbt.icon.demanddraft.domain.demanddraftproductinstr.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lbt.icon.core.domain.BaseDTO;
import lombok.*;

import java.util.List;

/**
 * @author devbimpe
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@NoArgsConstructor
public class UpdateDemandDraftProductInstrDTO extends BaseDTO {

    @JsonIgnore
    private String productCode;

    private List<QueryDemandDraftProductInstrDTO> demandDraftProductInstruments;


}
