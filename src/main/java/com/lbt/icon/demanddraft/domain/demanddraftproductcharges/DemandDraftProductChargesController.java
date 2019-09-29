package com.lbt.icon.demanddraft.domain.demanddraftproductcharges;

import com.lbt.icon.core.domain.ApiResponseBase;
import com.lbt.icon.core.exception.EntityNotFoundException;
import com.lbt.icon.core.exception.IconException;
import com.lbt.icon.demanddraft.domain.demanddraftproductcharges.dto.UpdateDemandDraftProductChargesDTO;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author devbimpe
 * @since 14/03/2019
 */
@Api(value = "demanddraftsproductcharges Controller", protocols = "https", description = "For demanddrafts Operations.")
@RequestMapping("v1/demanddraftsproductcharges")
@RestController
public class DemandDraftProductChargesController {
    private final DemandDraftProductChargesService demandDraftProductChargesService;

    public DemandDraftProductChargesController(DemandDraftProductChargesService demandDraftProductChargesService) {
        this.demandDraftProductChargesService = demandDraftProductChargesService;
    }

    @PutMapping("{productCode}")
    public ResponseEntity<ApiResponseBase<UpdateDemandDraftProductChargesDTO>> update(@RequestBody UpdateDemandDraftProductChargesDTO dto,@PathVariable("productCode") String productCode) throws IconException, EntityNotFoundException {
        ApiResponseBase<UpdateDemandDraftProductChargesDTO> apiResponseBase = new ApiResponseBase();
        apiResponseBase.setSuccessMessage(" :::::::: Update Successful ::::::");
        apiResponseBase.setResponse(demandDraftProductChargesService.update(dto,productCode));
        return new ResponseEntity<>(apiResponseBase, HttpStatus.OK);
    }

}

