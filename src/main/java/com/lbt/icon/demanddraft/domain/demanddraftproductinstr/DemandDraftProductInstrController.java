package com.lbt.icon.demanddraft.domain.demanddraftproductinstr;

import com.lbt.icon.core.domain.ApiResponseBase;
import com.lbt.icon.core.exception.EntityNotFoundException;
import com.lbt.icon.core.exception.IconException;
import com.lbt.icon.demanddraft.domain.demanddraftproductinstr.DemandDraftProductInstrService;
import com.lbt.icon.demanddraft.domain.demanddraftproductinstr.dto.UpdateDemandDraftProductInstrDTO;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author devbimpe
 * @since 14/03/2019
 */
@Api(value = "demanddraftproductinstrs Controller", protocols = "https", description = "For demanddraftproductinstrs Operations.")
@RequestMapping("v1/demanddraftproductinstrs")
@RestController
public class DemandDraftProductInstrController {

    private final DemandDraftProductInstrService demandDraftProductInstrService;

    public DemandDraftProductInstrController(DemandDraftProductInstrService demandDraftProductInstrService) {
        this.demandDraftProductInstrService = demandDraftProductInstrService;
    }

    @PutMapping("{productCode}")
    public ResponseEntity<ApiResponseBase<UpdateDemandDraftProductInstrDTO>> update(@RequestBody UpdateDemandDraftProductInstrDTO dto, @PathVariable("productCode") String productCode) throws IconException, EntityNotFoundException {
        ApiResponseBase<UpdateDemandDraftProductInstrDTO> apiResponseBase = new ApiResponseBase();
        apiResponseBase.setSuccessMessage(" :::::::: Update Successful ::::::");
        apiResponseBase.setResponse(demandDraftProductInstrService.update(dto,productCode));
        return new ResponseEntity<>(apiResponseBase, HttpStatus.OK);
    }

}

