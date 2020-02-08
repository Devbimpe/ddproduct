package com.lbt.icon.demanddraft.domain.demanddraft.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lbt.icon.demanddraft.type.DDTransferFrequency;
import com.lbt.icon.demanddraft.type.DemandDraftType;
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
@NoArgsConstructor
public class DemandDraftProductDTO implements Serializable {

    @JsonIgnore
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

//new
    @Pattern(regexp = "^P[0-9]+[Y][0-9]+[M][0-9]+[D]$", message = "{dd.cautionStatePeriod.Pattern}")
    private String cautionStatePeriod;

    @Pattern(regexp = "^P[0-9]+[Y][0-9]+[M][0-9]+[D]$", message = "{dd.revalidatePeriod.Pattern}")
    private String revalidatePeriod;

    @NotNull(message = "{demandDraft[NotNull.allowRevalidate]}")
    private Boolean allowRevalidate;

    @NotNull(message = "{demandDraft[NotNull.buyExchangeRateCode]}")
    private String buyExchangeRateCode;

    @NotNull(message = "{demandDraft[NotNull.sellExchangeRateCode]}")
    private String sellExchangeRateCode;

    private Boolean cashTransferAllowed;

    @NotNull(message = "{demandDraft[NotNull.transferTransAllowed]}")
    private Boolean transferTransAllowed;


    private Boolean custodianPrintAllow;

    @NotNull(message = "{demandDraft[NotNull.ddSequenceCode]}")
    private String ddSequenceCode;


    private String commonDDAccountId;


}
