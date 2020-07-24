package com.lbt.icon.demanddraft.domain.demanddraftproductinstr.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author devbimpe
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@NoArgsConstructor
public class DemandDraftProductInstrDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private String productCode;

    @NotBlank(message = "{demandDraftProductInstrDTO[NotBlank.instrCode]}")
    private String instrCode;

    private String instrumentCategory;

    @NotNull(message = "{demandDraftProductInstrDTO[NotNull.isDefault]}")
    private boolean isDefault;

}
