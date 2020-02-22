package com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lbt.icon.core.domain.BaseDTO;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author devbimpe
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@NoArgsConstructor
public class QueryDemandDraftProductTranCodeLimitDTO extends BaseDTO implements Serializable {

    private static final long serialVersionUUID = 1L;



    private String productCode;

    private BigDecimal debitAmountLimit;

    private BigDecimal creditAmountLimit;

    private String tranReportCode;


}
