package com.lbt.icon.demanddraft.domain.demanddraft.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lbt.icon.bankproduct.domain.master.dto.BankProductMasterDTO;
import com.lbt.icon.core.domain.BaseDTO;
import com.lbt.icon.demanddraft.type.DDTransferFrequency;
import com.lbt.icon.demanddraft.type.DemandDraftType;
import lombok.*;


/**
 * @author devbimpe
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@NoArgsConstructor
public class QueryDemandDraftProductDTO extends BaseDTO {

    private String productCode;

    private DemandDraftType demandDraftType;

    private String inventoryCategory;

    private String issueBank;

    private String issueBranch;

    private boolean duplicateIssueReport;

    private boolean consolidatePartTrans;

    private boolean micrInventory;

    private DDTransferFrequency ddTransferFrequency;

    private String ddTransferSpacer;

    private BankProductMasterDTO bankProductMasterDTO;


}
