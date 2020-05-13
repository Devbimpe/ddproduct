package com.lbt.icon.demanddraft.domain.demanddraftproductinstr.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lbt.icon.core.domain.BaseDTO;
import lombok.*;

import java.io.Serializable;

/**
 * @author devbimpe
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@NoArgsConstructor
public class QueryDemandDraftProductInstrDTO extends BaseDTO implements Serializable {

    private static final long serialVersionUUID = 1L;



    private String productCode;

    private String instrCode;

    private String instrumentCategory;

    private boolean isDefault;
//    private String instrTranType;


}
