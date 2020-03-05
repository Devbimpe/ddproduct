package com.lbt.icon.demanddraft.domain.demanddraft;
import com.lbt.icon.core.domain.BaseEntity;
import com.lbt.icon.demanddraft.type.DDTransferFrequency;
import com.lbt.icon.demanddraft.type.DemandDraftType;
import lombok.*;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


/**
 * @author devbimpe
 * @since 14/03/2019
 */
@Where(clause = "deleted=false")
@Setter
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Audited
@Entity
@Table(schema = "bankcore", name = "demand_draft")
public class DemandDraftProduct extends BaseEntity {

    @NotNull(message = "")
    @Column(name = "product_code", unique = true)
    private String productCode;

    @Column(name = "demand_draft_type")
    @Enumerated(EnumType.STRING)
    private DemandDraftType demandDraftType;

    @Column(name = "inventory_type")
    private String inventoryType;

    @Column(name = "issue_bank")
    private String issueBank;

    @Column(name = "issue_branch")
    private String issueBranch;

    @Column(name = "dupl_issue_report")
    private boolean duplicateIssueReport;

    @Column(name = "cons_part_tran")
    private boolean consolidatePartTrans;

    @Column(name = "micr_inventory")
    private boolean micrInventory;

    @Column(name = "dd_transfer_freq")
    @Enumerated(EnumType.STRING)
    private DDTransferFrequency ddTransferFrequency;

    @Column(name = "dd_transfer_spacer")
    private String ddTransferSpacer;
//new

    @Column(name = "caution_state_period")
    private String cautionStatePeriod;


    @Column(name = "revalidate_period")
    private String revalidatePeriod;


    @Column(name = "allow_revalidate", nullable = false)
    private boolean allowRevalidate;

//
//    @Column(name = "buy_exchange_rate_code")
//    private String buyExchangeRateCode;


    @Column(name = "exchange_rate_code", nullable = false)
    private String exchangeRateCode;

    @Column(name = "cash_transfer_allowed")
    private boolean cashTransferAllowed;


    @Column(name = "transfer_trans_allowed")
    private boolean transferTransAllowed;


    @Column(name = "custodian_print_allow")
    private boolean custodianPrintAllow;


    @Column(name = "dd_sequence_code")
    private String ddSequenceCode;


    @Column(name = "common_dd_account_id")
    private String commonDDAccountId;


    @Column(name = "defaultCustodian")
    private String defaultCustodian;





}
