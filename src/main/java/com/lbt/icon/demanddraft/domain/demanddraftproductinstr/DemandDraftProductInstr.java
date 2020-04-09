
package com.lbt.icon.demanddraft.domain.demanddraftproductinstr;
import com.lbt.icon.core.domain.BaseEntity;
import com.lbt.icon.demanddraft.type.InstrumentTransactionType;
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
@ToString
@EqualsAndHashCode
@Audited
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(schema = "bankcore", name = "demand_draft_prod_instr")
public class DemandDraftProductInstr extends BaseEntity {

    @NotNull
    @Column(name = "product_code")
    private String productCode;

    @Column(name = "instr_code")
    private String instrCode;

    @Column(name = "instr_tran_type")
    @Enumerated(EnumType.STRING)
    private InstrumentTransactionType instrTranType;


}
