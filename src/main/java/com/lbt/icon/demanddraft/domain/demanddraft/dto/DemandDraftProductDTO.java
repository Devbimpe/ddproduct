package com.lbt.icon.demanddraft.domain.demanddraft.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lbt.icon.demanddraft.type.DDTransferFrequency;
import com.lbt.icon.demanddraft.type.DemandDraftType;
import lombok.*;

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

    private String inventoryCategory;

    private String issueBank;

    private String issueBranch;

    private boolean duplicateIssueReport;

    private boolean consolidatePartTrans;

    private boolean micrInventory;

    private DDTransferFrequency ddTransferFrequency;

    private String ddTransferSpacer;


}
