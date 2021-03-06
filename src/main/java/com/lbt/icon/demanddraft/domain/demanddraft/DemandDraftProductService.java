
package com.lbt.icon.demanddraft.domain.demanddraft;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lbt.icon.bankproduct.domain.master.BankProductMaster;
import com.lbt.icon.bankproduct.domain.master.dto.BankProductMasterDTO;
import com.lbt.icon.bankproduct.types.BankProductType;
import com.lbt.icon.core.exception.EntityNotFoundException;
import com.lbt.icon.core.exception.FieldValidationException;
import com.lbt.icon.core.exception.IconException;
import com.lbt.icon.core.exception.IconQueryException;
import com.lbt.icon.demanddraft.domain.demanddraft.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author devbimpe
 * @since 14/03/2019
 */

public interface DemandDraftProductService {

    QueryDemandDraftProductDTO create(CreateDemandDraftProductDTO demandDraftProductDTO) throws FieldValidationException,EntityNotFoundException,IconQueryException, IconException;


    DemandDraftProductInquiryDTO inquireByProductCode(String productCode) throws IconException;

    DemandDraftProductInquiryDTO findById(Long id) throws IconException;


    //UpdateDemandDraftProductDTO update(String productCode, UpdateDemandDraftProductDTO dto) throws IconException;

   // UpdateDemandDraftProductWithDependenciesDTO updateDemandDraftProductWithDependencies(String productCode, UpdateDemandDraftProductWithDependenciesDTO dto) throws IconException;
    UpdateDemandDraftProductWithDependenciesDTO updateDemandDraftProductWithDependenciesById(Long id, UpdateDemandDraftProductWithDependenciesDTO dto) throws IconException;

    Page<BankProductMasterDTO> findAll(Pageable pageable, BankProductType productType) throws IconQueryException;

    DemandDraftProductInquiryDTO enableByProductCode(@NotBlank String productCode) throws EntityNotFoundException, FieldValidationException,IconException;

    DemandDraftProductInquiryDTO disableByProductCode(@NotBlank String productCode) throws EntityNotFoundException, FieldValidationException,IconException;

    List<BankProductMasterDTO> findProductsByProductCodeLike(String productCode);

    BankProductMasterDTO findByProductCode(String productCode);

	String getAccountNumberGenCodeByProductCode(String productCode);

	boolean hasAccountNumberGenCode(String productCode);

	List<DemandDraftProductBranchDto> findBranchesByProductCode(String productCode);


	List<DemandDraftProductCurrencyDto> findCurrenciesByProductCode(String productCode);


	List<DemandDraftProductSpacerCodeDto> findSpacersByProductCode(String productCode);


	List<DemandDraftProductGlDto> findGlsByProductCode(String productCode);


    Page<BankProductMaster> findBankProductMasterByBranchCurrencyGlOrInstrument(OfficeProductContextSearchDto officeProductContextSearchDto, PageRequest pageRequest) throws EntityNotFoundException, IconQueryException;



    //public Page<BankProductMasterDTO> search(String reportDescription, String fromDate, String toDate, Pageable pageable) throws IconException
}

