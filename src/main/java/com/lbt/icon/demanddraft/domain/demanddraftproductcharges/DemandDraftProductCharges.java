
package com.lbt.icon.demanddraft.domain.demanddraftproductcharges;


import com.lbt.icon.core.domain.BaseEntity;
import com.lbt.icon.demanddraft.type.*;
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
@Table(schema = "bankcore", name = "demand_draft_prod_charges")
public class DemandDraftProductCharges extends BaseEntity {

    @NotNull(message = "")
    @Column(name = "product_code", unique = true)
    private String productCode;

    @Column(name = "charge_type")
    private String chargeType;

    @Column(name = "charge_code")
    private String chargeCode;

    @Column(name = "charge_currency")
    @Enumerated(EnumType.STRING)
    private ChargeCurrency chargeCurrency;

    @Column(name = "exchange_rate_code")
    private String exchangeRateCode;






}
