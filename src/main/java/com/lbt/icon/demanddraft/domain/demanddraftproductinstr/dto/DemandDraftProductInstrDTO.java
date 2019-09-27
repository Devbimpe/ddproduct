package com.lbt.icon.demanddraft.domain.demanddraftproductinstr.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.io.Serializable;

/**
 * @author devbimpe
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@NoArgsConstructor
public class DemandDraftProductInstrDTO implements Serializable {

    @JsonIgnore
    private String productCode;

    private String instrCode;

    private String instrTranType;

}
