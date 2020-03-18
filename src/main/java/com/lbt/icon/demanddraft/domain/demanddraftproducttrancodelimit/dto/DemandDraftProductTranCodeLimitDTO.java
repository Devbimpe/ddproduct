package com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.Serializable;
import lombok.*;
import java.math.BigDecimal;

/**
 * @author devbimpe
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@NoArgsConstructor
public class DemandDraftProductTranCodeLimitDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private String productCode;

    private BigDecimal debitAmountLimit;

    private BigDecimal creditAmountLimit;

    private String tranReportCode;


}
