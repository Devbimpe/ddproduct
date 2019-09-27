
package com.lbt.icon.demanddraft.domain.demanddraft;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lbt.icon.bankproduct.domain.master.dto.BankProductMasterDTO;
import com.lbt.icon.bankproduct.types.BankProductType;
import com.lbt.icon.core.exception.EntityNotFoundException;
import com.lbt.icon.core.exception.FieldValidationException;
import com.lbt.icon.core.exception.IconException;
import com.lbt.icon.core.exception.IconQueryException;
import com.lbt.icon.demanddraft.domain.demanddraft.dto.CreateDemandDraftProductDTO;
import com.lbt.icon.demanddraft.domain.demanddraft.dto.DemandDraftProductInquiryDTO;
import com.lbt.icon.demanddraft.domain.demanddraft.dto.QueryDemandDraftProductDTO;
import com.lbt.icon.demanddraft.domain.demanddraft.dto.UpdateDemandDraftProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotBlank;

/**
 * @author devbimpe
 * @since 14/03/2019
 */

public interface DemandDraftProductService {

    QueryDemandDraftProductDTO create(CreateDemandDraftProductDTO demandDraftProductDTO) throws IconException;


    DemandDraftProductInquiryDTO inquireByProductCode(String productCode) throws IconException;


    QueryDemandDraftProductDTO update(String productCode, UpdateDemandDraftProductDTO dto) throws IconException;


    Page<BankProductMasterDTO> findAll(Pageable pageable, BankProductType productType) throws IconQueryException;

    BankProductMasterDTO enableByProductCode(@NotBlank String productCode) throws EntityNotFoundException, FieldValidationException;

    BankProductMasterDTO disableByProductCode(@NotBlank String productCode) throws EntityNotFoundException, FieldValidationException;
}

