package com.lbt.icon.demanddraft.domain.demanddraftproductcharges.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lbt.icon.bankproduct.domain.master.dto.UpdateBankProductMasterDTO;
import com.lbt.icon.core.domain.BaseDTO;
import com.lbt.icon.demanddraft.type.ChargeCurrency;
import lombok.*;

import java.util.List;

/**
 * @author devbimpe
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@NoArgsConstructor
public class UpdateDemandDraftProductChargesDTO extends BaseDTO {

    @JsonIgnore
    private String productCode;

    private List<QueryDemandDraftProductChargesDTO> demandDraftProductCharges;


}
