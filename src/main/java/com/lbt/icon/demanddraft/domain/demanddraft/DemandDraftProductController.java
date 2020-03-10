package com.lbt.icon.demanddraft.domain.demanddraft;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lbt.icon.bankproduct.domain.master.dto.BankProductMasterDTO;
import com.lbt.icon.bankproduct.domain.master.search.BankProductContextSearch;
import com.lbt.icon.bankproduct.domain.master.search.BankProductMasterSearchParams;
import com.lbt.icon.bankproduct.types.BankProductType;
import com.lbt.icon.core.domain.ApiResponseBase;
import com.lbt.icon.core.exception.EntityNotFoundException;
import com.lbt.icon.core.exception.FieldValidationException;
import com.lbt.icon.core.exception.IconException;
import com.lbt.icon.core.exception.IconQueryException;
import com.lbt.icon.demanddraft.domain.demanddraft.dto.*;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
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
    private final BankProductContextSearch accountProductService;


    public DemandDraftProductController(DemandDraftProductService demandDraftProductService, BankProductContextSearch accountProductService) {
        this.demandDraftProductService = demandDraftProductService;
        this.accountProductService = accountProductService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseBase<QueryDemandDraftProductDTO>> create(@RequestBody CreateDemandDraftProductDTO dto) throws IconException {
        ApiResponseBase<QueryDemandDraftProductDTO> apiResponseBase = new ApiResponseBase<>();
        apiResponseBase.setSuccessMessage("Demand draft product created successfully");
        dto.setNaturalId(dto.getBankProduct().getProductCode());
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
    public ResponseEntity<ApiResponseBase<UpdateDemandDraftProductWithDependenciesDTO>> updateWithDependencies(@RequestBody UpdateDemandDraftProductWithDependenciesDTO dto, @PathVariable("productCode") String productCode) throws IconException {
        ApiResponseBase<UpdateDemandDraftProductWithDependenciesDTO> apiResponseBase = new ApiResponseBase<>();
        apiResponseBase.setSuccessMessage("Demand draft product updated successfully");
        dto.setProductCode(productCode);
        apiResponseBase.setResponse(demandDraftProductService.updateDemandDraftProductWithDependencies(productCode,dto));
        return new ResponseEntity<>(apiResponseBase, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponseBase<Page<BankProductMasterDTO>>> findAllByProductType(Pageable pageable, @RequestParam("productTypeCode") BankProductType productType) throws IconQueryException{
        ApiResponseBase<Page<BankProductMasterDTO>> apiResponseBase = new ApiResponseBase();
        Page<BankProductMasterDTO> page = demandDraftProductService.findAll(pageable,productType);
        apiResponseBase.setSuccessMessage("Demand draft products fetched successfully");
        apiResponseBase.setResponse(page);
        return new ResponseEntity<>(apiResponseBase, HttpStatus.OK);
    }


    @PutMapping("/{productCode}/disable")
    @ResponseBody
    public ResponseEntity<ApiResponseBase<BankProductMasterDTO>> disableByProductCode(
            @NotBlank @PathVariable(value = "productCode") String productCode)  throws FieldValidationException, EntityNotFoundException{
        ApiResponseBase<BankProductMasterDTO> rsp = new ApiResponseBase<>();
        rsp.setResponse(demandDraftProductService.disableByProductCode(productCode));
        rsp.setSuccessMessage("Demand draft product successfully disabled");
        return new ResponseEntity<>(rsp, HttpStatus.OK);
    }

    @PutMapping("/{productCode}/enable")
    @ResponseBody
    public ResponseEntity<ApiResponseBase<BankProductMasterDTO>> enableByProductCode(
            @NotBlank @PathVariable(value = "productCode") String productCode)  throws FieldValidationException, EntityNotFoundException{
        ApiResponseBase<BankProductMasterDTO> rsp = new ApiResponseBase<>();
        rsp.setResponse(demandDraftProductService.enableByProductCode(productCode));
        rsp.setSuccessMessage("Demand draft product successfully enabled");
        return new ResponseEntity<>(rsp, HttpStatus.OK);
    }



    @GetMapping("/{productCode}/findbyproductcodelike")
    public ResponseEntity<ApiResponseBase<List<BankProductMasterDTO>>> getByProductCodeLike(@PathVariable String productCode) throws IconException {

        List<BankProductMasterDTO> queryDtos = demandDraftProductService.findProductsByProductCodeLike(productCode);
        ApiResponseBase<List<BankProductMasterDTO>> apiResponseBase = new ApiResponseBase();
        apiResponseBase.setSuccessMessage("Demand draft products fetched successfully");
        apiResponseBase.setResponse(queryDtos);
        return new ResponseEntity<>(apiResponseBase, HttpStatus.OK);
    }

	@GetMapping("/{productCode}/hasaccountnogencode")
    public boolean hasAccountNumberGenCode(@PathVariable String productCode) {
		return demandDraftProductService.hasAccountNumberGenCode(productCode);
    }

	@GetMapping("/{productCode}/findbranchcodes")
    public ResponseEntity<?> findBranchesByProductCode(@PathVariable String productCode) throws EntityNotFoundException, IconQueryException, IconException {
		
		List<DemandDraftProductBranchDto> queryDtos = demandDraftProductService.findBranchesByProductCode(productCode); 	
    	return new ResponseEntity<>(
    		new ApiResponseBase<>(
    			queryDtos,
    			HttpStatus.OK.name(),
    			false,
    			null,
    			null,
    			null,
    			null,
    			null,
    			null), 
    		HttpStatus.OK);
    }

	@GetMapping("/{productCode}/findcurrencies")
    public ResponseEntity<?> findCurrenciesByProductCode(@PathVariable String productCode) throws EntityNotFoundException, IconQueryException, IconException {
		
		List<DemandDraftProductCurrencyDto> queryDtos = demandDraftProductService.findCurrenciesByProductCode(productCode); 	
    	return new ResponseEntity<>(
    		new ApiResponseBase<>(
    			queryDtos,
    			HttpStatus.OK.name(),
    			false,
    			null,
    			null,
    			null,
    			null,
    			null,
    			null), 
    		HttpStatus.OK);
    }

	@GetMapping("/{productCode}/findspacers")
    public ResponseEntity<?> findSpacersByProductCode(@PathVariable String productCode) throws EntityNotFoundException, IconQueryException, IconException {
		
		List<DemandDraftProductSpacerCodeDto> queryDtos = demandDraftProductService.findSpacersByProductCode(productCode); 	
    	return new ResponseEntity<>(
    		new ApiResponseBase<>(
    			queryDtos,
    			HttpStatus.OK.name(),
    			false,
    			null,
    			null,
    			null,
    			null,
    			null,
    			null), 
    		HttpStatus.OK);
    }



    @GetMapping("/query")
    @ResponseBody
    public ResponseEntity<ApiResponseBase<Page<BankProductMasterDTO>>> search(
            @NotNull BankProductMasterSearchParams params, Pageable pageable) throws IconQueryException,FieldValidationException
    {
        ApiResponseBase<Page<BankProductMasterDTO>> rsp = new ApiResponseBase<>();
        HttpStatus status = HttpStatus.OK;

        Page<BankProductMasterDTO> accountMasterView = null;
        accountMasterView = accountProductService.search(params, pageable);
        rsp.setResponse(accountMasterView);

        return new ResponseEntity<>(rsp, status);
    }


//	@GetMapping("/{productCode}/findglcodes")
//    public ResponseEntity<?> findGlsByProductCode(@PathVariable String productCode) throws EntityNotFoundException, IconQueryException, IconException {
//
//		List<DemandDraftProductGlDto> queryDtos = demandDraftProductService.findGlsByProductCode(productCode);
//    	return new ResponseEntity<>(
//    		new ApiResponseBase<>(
//    			queryDtos,
//    			HttpStatus.OK.name(),
//    			false,
//    			null,
//    			null,
//    			null,
//    			null,
//    			null,
//    			null),
//    		HttpStatus.OK);
//    }
}


