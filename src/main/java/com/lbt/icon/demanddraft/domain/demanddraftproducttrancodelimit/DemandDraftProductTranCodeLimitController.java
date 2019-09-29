package com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit;

import com.lbt.icon.core.domain.ApiResponseBase;
import com.lbt.icon.core.exception.EntityNotFoundException;
import com.lbt.icon.core.exception.IconException;
import com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit.dto.QueryDemandDraftProductTranCodeLimitDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit.dto.UpdateDemandDraftProductTranCodeLimitDTO;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author devbimpe
 * @since 14/03/2019
 */
@Api(value = "demanddraftproducttranscodelimits Controller", protocols = "https", description = "For demanddraftproducttranscodelimits Operations.")
@RequestMapping("v1/demanddraftproducttranscodelimits")
@RestController
public class DemandDraftProductTranCodeLimitController {

    private final DemandDraftProductTranCodeLimitService demandDraftProductTranCodeLimitService;

    public DemandDraftProductTranCodeLimitController(DemandDraftProductTranCodeLimitService demandDraftProductTranCodeLimitService) {
        this.demandDraftProductTranCodeLimitService = demandDraftProductTranCodeLimitService;
    }

    @PutMapping("{productCode}")
    public ResponseEntity<ApiResponseBase<UpdateDemandDraftProductTranCodeLimitDTO>> update(@RequestBody UpdateDemandDraftProductTranCodeLimitDTO dto,  @PathVariable("productCode") String productCode) throws IconException, EntityNotFoundException {
        ApiResponseBase<UpdateDemandDraftProductTranCodeLimitDTO> apiResponseBase = new ApiResponseBase();
        apiResponseBase.setSuccessMessage(" :::::::: Update Successful ::::::");
        apiResponseBase.setResponse(demandDraftProductTranCodeLimitService.update(dto,productCode));
        return new ResponseEntity<>(apiResponseBase, HttpStatus.OK);
    }


}

