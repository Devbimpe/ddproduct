package com.lbt.icon.demanddraft.domain.demanddraft.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lbt.icon.bankproduct.domain.master.dto.AddBankProductMasterDTO;
import com.lbt.icon.core.domain.BaseQueryDto;
import com.lbt.icon.demanddraft.domain.demanddraftproductcharges.dto.DemandDraftProductChargesDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproductinstr.dto.DemandDraftProductInstrDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit.dto.DemandDraftProductTranCodeLimitDTO;
import lombok.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * @author devbimpe
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@NoArgsConstructor
@ToString
public class CreateDemandDraftProductDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull
    private AddBankProductMasterDTO bankProduct;

    @NotNull
    private DemandDraftProductDTO demandDraftProduct;

    @NotNull
    private DemandDraftProductChargesDTO[] demandDraftProductCharges;

    @NotNull
    private DemandDraftProductInstrDTO[] demandDraftProductInstruments;

    @NotNull
    private DemandDraftProductTranCodeLimitDTO[] demandDraftProductTranCodeLimits;

    private String productCode;


}
