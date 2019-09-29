package com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lbt.icon.core.domain.BaseDTO;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author devbimpe
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@NoArgsConstructor
public class UpdateDemandDraftProductTranCodeLimitDTO extends BaseDTO {

    @JsonIgnore
    private String productCode;

    private List<QueryDemandDraftProductTranCodeLimitDTO> demandDraftProductTranCodeLimits;


}
