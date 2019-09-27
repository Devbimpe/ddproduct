package com.lbt.icon.demanddraft.domain.demanddraftproductinstr.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lbt.icon.core.domain.BaseDTO;
import lombok.*;

/**
 * @author devbimpe
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@NoArgsConstructor
public class QueryDemandDraftProductInstrDTO extends BaseDTO {


    private String productCode;

    private String instrCode;

    private String instrTranType;


}
