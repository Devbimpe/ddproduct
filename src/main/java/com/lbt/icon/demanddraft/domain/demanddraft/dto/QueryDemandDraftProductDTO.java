package com.lbt.icon.demanddraft.domain.demanddraft.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lbt.icon.bankproduct.domain.master.dto.BankProductMasterDTO;
import com.lbt.icon.core.domain.BaseDTO;
import com.lbt.icon.core.domain.BaseQueryDto;
import com.lbt.icon.demanddraft.type.DDTransferFrequency;
import com.lbt.icon.demanddraft.type.DemandDraftType;
import com.lbt.icon.demanddraft.type.InstrumentSeries;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;


/**
 * @author devbimpe
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryDemandDraftProductDTO extends BaseQueryDto implements Serializable {

    private static final long serialVersionUUID = 1L;


    private String productCode;

    private DemandDraftType demandDraftType;

    private String inventoryType;

    private String issueBank;

    private String issueBranch;

    private Boolean duplicateIssueReport;

    private Boolean consolidatePartTrans;

    private Boolean micrInventory;

    private DDTransferFrequency ddTransferFrequency;

    private String ddTransferSpacer;

    private BankProductMasterDTO bankProductMasterDTO;


//    @Pattern(regexp = "^P[0-9]+[Y][0-9]+[M][0-9]+[D]$", message = "{dd.revalidatePeriod.Pattern}")
    private String cautionStatePeriod;

//    @Pattern(regexp = "^P[0-9]+[Y][0-9]+[M][0-9]+[D]$", message = "{dd.revalidatePeriod.Pattern}")
   // private String revalidatePeriod;

    @NotNull(message = "{demandDraft[NotNull.allowRevalidate]}")
    private Boolean allowRevalidate;

//    @NotNull(message = "{demandDraft[NotNull.buyExchangeRateCode]}")
//    private String buyExchangeRateCode;

    @NotNull(message = "{demandDraft[NotNull.exchangeRateCode]}")
    private String exchangeRateCode;

    private Boolean cashTransferAllowed;


    private Boolean transferTransAllowed;


    private Boolean custodianPrintAllow;


//    private String ddSequenceCode;


    private String commonDDAccountId;

    private String defaultCustodian;

   // private InstrumentSeries instrumentSeries;

    private String instrumentSeries;
}
