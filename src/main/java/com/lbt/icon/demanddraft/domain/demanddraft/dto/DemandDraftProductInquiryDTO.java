package com.lbt.icon.demanddraft.domain.demanddraft.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lbt.icon.bankproduct.domain.master.dto.BankProductMasterDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproductcharges.dto.QueryDemandDraftProductChargesDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproductinstr.dto.QueryDemandDraftProductInstrDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit.dto.QueryDemandDraftProductTranCodeLimitDTO;
import lombok.*;

import java.util.List;

/**
 * @author devbimpe
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@NoArgsConstructor
public class DemandDraftProductInquiryDTO {
    private QueryDemandDraftProductDTO demandDraftProduct;
    private BankProductMasterDTO bankProductMasterDTO;
    private List<QueryDemandDraftProductChargesDTO> demandDraftProductCharges;
    private List<QueryDemandDraftProductInstrDTO> demandDraftProductInstruments;
    private List<QueryDemandDraftProductTranCodeLimitDTO> demandDraftProductTranCodeLimits;
}
