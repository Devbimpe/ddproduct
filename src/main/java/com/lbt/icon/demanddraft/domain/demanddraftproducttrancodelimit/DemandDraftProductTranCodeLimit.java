
package com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit;


import com.lbt.icon.core.domain.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


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
@Table(schema = "bankcore", name = "demand_draft_prod_tr_code_limit")
public class DemandDraftProductTranCodeLimit extends BaseEntity {

    @NotNull
    @Column(name = "product_code")
    private String productCode;

    @Column(name = "debit_amt_limit")
    private BigDecimal debitAmountLimit;

    @Column(name = "credit_amt_limit")
    private BigDecimal creditAmountLimit;

    @Column(name = "tran_report_code")
    private String tranReportCode;


}
