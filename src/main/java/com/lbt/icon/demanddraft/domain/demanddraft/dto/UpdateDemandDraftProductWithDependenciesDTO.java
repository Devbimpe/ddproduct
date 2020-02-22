package com.lbt.icon.demanddraft.domain.demanddraft.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lbt.icon.bankproduct.domain.master.dto.UpdateBankProductMasterDTO;
import com.lbt.icon.core.domain.BaseDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproductcharges.dto.DemandDraftProductChargesDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproductcharges.dto.QueryDemandDraftProductChargesDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproductinstr.dto.QueryDemandDraftProductInstrDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit.dto.QueryDemandDraftProductTranCodeLimitDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author devbimpe
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@NoArgsConstructor
public class UpdateDemandDraftProductWithDependenciesDTO extends BaseDTO implements Serializable {

    private static final long serialVersionUUID = 1L;

    @JsonIgnore
    private String productCode;
    private UpdateBankProductMasterDTO bankProduct;
    private QueryDemandDraftProductDTO demandDraftProduct;
    private List<QueryDemandDraftProductChargesDTO> demandDraftProductCharges;
    private List<QueryDemandDraftProductInstrDTO> demandDraftProductInstruments;
    private List<QueryDemandDraftProductTranCodeLimitDTO> demandDraftProductTranCodeLimits;



}
