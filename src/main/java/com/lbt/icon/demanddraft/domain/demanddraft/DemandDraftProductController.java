package com.lbt.icon.demanddraft.domain.demanddraft;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lbt.icon.bankproduct.domain.master.dto.BankProductMasterDTO;
import com.lbt.icon.bankproduct.types.BankProductType;
import com.lbt.icon.core.domain.ApiResponseBase;
import com.lbt.icon.core.exception.EntityNotFoundException;
import com.lbt.icon.core.exception.FieldValidationException;
import com.lbt.icon.core.exception.IconException;
import com.lbt.icon.core.exception.IconQueryException;
import com.lbt.icon.demanddraft.domain.demanddraft.dto.CreateDemandDraftProductDTO;
import com.lbt.icon.demanddraft.domain.demanddraft.dto.DemandDraftProductInquiryDTO;
import com.lbt.icon.demanddraft.domain.demanddraft.dto.QueryDemandDraftProductDTO;
import com.lbt.icon.demanddraft.domain.demanddraft.dto.UpdateDemandDraftProductDTO;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

/**
 * @author devbimpe
 * @since 14/03/2019
 */
@Api(value = "demanddrafts Controller", protocols = "https", description = "For demanddrafts Operations.")
@RequestMapping("v1/demanddrafts")
@RestController
public class DemandDraftProductController {
    private final DemandDraftProductService demandDraftProductService;

    public DemandDraftProductController(DemandDraftProductService demandDraftProductService) {
        this.demandDraftProductService = demandDraftProductService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseBase<QueryDemandDraftProductDTO>> create(@RequestBody CreateDemandDraftProductDTO dto) throws IconException {
        ApiResponseBase<QueryDemandDraftProductDTO> apiResponseBase = new ApiResponseBase<>();
        apiResponseBase.setSuccessMessage("Demand draft product created successfully");
        dto.setNaturalId(UUID.randomUUID().toString());
        dto.getBankProduct().setProductTypeCode(BankProductType.DDRAFT);
        apiResponseBase.setResponse(demandDraftProductService.create(dto));
        return new ResponseEntity<>(apiResponseBase, HttpStatus.CREATED);
    }

    @GetMapping("{productCode}")
    public ResponseEntity<ApiResponseBase<DemandDraftProductInquiryDTO>> findByProductCode(@PathVariable("productCode") String productCode) throws IconException {
        ApiResponseBase<DemandDraftProductInquiryDTO> apiResponseBase = new ApiResponseBase();
        DemandDraftProductInquiryDTO findByProductCode = demandDraftProductService.inquireByProductCode(productCode);
        apiResponseBase.setResponse(findByProductCode);
        return new ResponseEntity<>(apiResponseBase, HttpStatus.OK);
    }

    @PutMapping("{productCode}")
    public ResponseEntity<ApiResponseBase<QueryDemandDraftProductDTO>> update(@RequestBody UpdateDemandDraftProductDTO dto, @PathVariable("productCode") String productCode) throws IconException {
        ApiResponseBase<QueryDemandDraftProductDTO> apiResponseBase = new ApiResponseBase<>();
        apiResponseBase.setSuccessMessage("Demand draft product updated successfully");
        apiResponseBase.setResponse(demandDraftProductService.update(productCode,dto));
        return new ResponseEntity<>(apiResponseBase, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponseBase<Page<BankProductMasterDTO>>> findAllByProductType(@PageableDefault(size = 10, page = 0) Pageable pageable, @RequestParam("productType") BankProductType productType) throws IconQueryException{
        ApiResponseBase<Page<BankProductMasterDTO>> apiResponseBase = new ApiResponseBase();
        Page<BankProductMasterDTO> page = demandDraftProductService.findAll(pageable,productType);
        apiResponseBase.setSuccessMessage("Demand draft products fetched successfully");
        apiResponseBase.setResponse(page);
        return new ResponseEntity<>(apiResponseBase, HttpStatus.OK);
    }


    @PutMapping("/disable/{productCode}")
    @ResponseBody
    public ResponseEntity<ApiResponseBase<BankProductMasterDTO>> disableByProductCode(
            @NotBlank @PathVariable(value = "productCode") String productCode)  throws FieldValidationException, EntityNotFoundException{
        ApiResponseBase<BankProductMasterDTO> rsp = new ApiResponseBase<>();
        rsp.setResponse(demandDraftProductService.disableByProductCode(productCode));
        rsp.setSuccessMessage("Demand draft product successfully disabled");
        return new ResponseEntity<>(rsp, HttpStatus.OK);
    }

    @PutMapping("/enable/{productCode}")
    @ResponseBody
    public ResponseEntity<ApiResponseBase<BankProductMasterDTO>> enableByProductCode(
            @NotBlank @PathVariable(value = "productCode") String productCode)  throws FieldValidationException, EntityNotFoundException{
        ApiResponseBase<BankProductMasterDTO> rsp = new ApiResponseBase<>();
        rsp.setResponse(demandDraftProductService.enableByProductCode(productCode));
        rsp.setSuccessMessage("Demand draft product successfully enabled");
        return new ResponseEntity<>(rsp, HttpStatus.OK);
    }


}


