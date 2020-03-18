package com.lbt.icon.demanddraft.domain.demanddraft.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lbt.icon.bankproduct.domain.master.dto.BankProductMasterDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproductcharges.dto.QueryDemandDraftProductChargesDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproductinstr.dto.QueryDemandDraftProductInstrDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit.dto.QueryDemandDraftProductTranCodeLimitDTO;
import com.lbt.icon.excd.domain.exceptiondefinition.dto.ExceptionDefinitionQueryDto;
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
    private String  productCode;
    private QueryDemandDraftProductDTO demandDraftProduct;
    private BankProductMasterDTO bankProduct;
    private List<QueryDemandDraftProductChargesDTO> demandDraftProductCharges;
    private List<QueryDemandDraftProductInstrDTO> demandDraftProductInstruments;
    private List<QueryDemandDraftProductTranCodeLimitDTO> demandDraftProductTranCodeLimits;
    private List<ExceptionDefinitionQueryDto> exceptionDto;
}
