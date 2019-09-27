package com.lbt.icon.demanddraft.domain.demanddraftproductcharges.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lbt.icon.core.domain.BaseDTO;
import com.lbt.icon.demanddraft.type.ChargeCurrency;
import lombok.*;


/**
 * @author devbimpe
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@NoArgsConstructor
public class QueryDemandDraftProductChargesDTO extends BaseDTO {

    private String productCode;

    private String chargeType;

    private String chargeCode;

    private ChargeCurrency chargeCurrency;

    private String exchangeRateCode;

}
