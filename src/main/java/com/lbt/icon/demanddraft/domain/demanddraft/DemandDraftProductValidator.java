
package com.lbt.icon.demanddraft.domain.demanddraft;


import com.lbt.icon.bankcommons.domain.common.currencyrate.CurrencyRateService;
import com.lbt.icon.bankcommons.domain.company.financialinstitution.FinancialInstitutionService;
import com.lbt.icon.bankcommons.domain.globalparams.globalcode.GlobalCodeService;
import com.lbt.icon.bankcommons.domain.nextnumbergenerator.NextNumberGeneratorService;
import com.lbt.icon.bankproduct.domain.master.BankProductMasterValidator;
import com.lbt.icon.bankproduct.domain.master.dto.AddBankProductMasterDTO;
import com.lbt.icon.bankproduct.domain.master.dto.UpdateBankProductMasterDTO;
import com.lbt.icon.core.exception.FieldValidationError;
import com.lbt.icon.core.exception.FieldValidationException;
import com.lbt.icon.core.exception.FieldValidationRuntimeException;
import com.lbt.icon.core.exception.IconException;

import com.lbt.icon.core.util.CommonUtils;
import com.lbt.icon.demanddraft.domain.demanddraft.dto.CreateDemandDraftProductDTO;
import com.lbt.icon.demanddraft.domain.demanddraft.dto.UpdateDemandDraftProductDTO;
import com.lbt.icon.demanddraft.domain.demanddraft.dto.UpdateDemandDraftProductWithDependenciesDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproductcharges.dto.DemandDraftProductChargesDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit.dto.DemandDraftProductTranCodeLimitDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.validation.Validator;
import java.util.List;

@RequiredArgsConstructor
@Component
public class DemandDraftProductValidator {

    private final Validator validator;
    private final DemandDraftProductRepository repo;
    private final GlobalCodeService globalCodeService;
    private  final CurrencyRateService currencyRateService;
    private final FinancialInstitutionService financialInstitutionService;
    private final BankProductMasterValidator bankProductMasterValidator;
    private final NextNumberGeneratorService nextNumberGeneratorService;
    private final ModelMapper modelMapper;

    public void validateFields(Object obj) throws FieldValidationException {
        List<FieldValidationError> fieldValidationErrors =
                CommonUtils.getStaticFieldValidationErrors(
                        obj,
                        validator
                );

        if (fieldValidationErrors.isEmpty()) {
            return;
        }

        throw new FieldValidationException("Field validation error occur", fieldValidationErrors);
    }

    public void validate(CreateDemandDraftProductDTO dto) throws IconException{
        List<FieldValidationError> fieldValidationErrors = CommonUtils.getStaticFieldValidationErrors(
                dto, validator
        );
        try {
            validateBankProductMaster(dto.getBankProduct(),fieldValidationErrors);
            if(dto.getDemandDraftProduct().getAllowRevalidate() != null && dto.getDemandDraftProduct().getAllowRevalidate() && StringUtils.isEmpty(dto.getDemandDraftProduct().getRevalidatePeriod())){
                FieldValidationError error = new FieldValidationError("revalidatePeriod", "Revalidate period cannot be null when allowRevalidate  is true " );
                fieldValidationErrors.add(error);
            }
        } catch (FieldValidationException e) {

            e.printStackTrace();
            throw e;
        }
        validateFields(dto.getDemandDraftProduct());
        validateProductCode(dto.getBankProduct().getProductCode(), fieldValidationErrors);
        if(!nextNumberGeneratorService.existsByCode(dto.getDemandDraftProduct().getDdSequenceCode())){
            FieldValidationError error = new FieldValidationError("ddSequenceCode", "DD Sequence Code is not available " );
            fieldValidationErrors.add(error);
        }
        if(!nextNumberGeneratorService.existsByCode(dto.getBankProduct().getAccountNoGenCode())){
            FieldValidationError error = new FieldValidationError("accountNoGenCode", "Account No Gen Code Code is not available " );
            fieldValidationErrors.add(error);
        }



//        validateInventoryCategory(dto.getDemandDraftProduct().getInventoryType(), fieldValidationErrors);
//        validateIssueBankAndBranch(dto, fieldValidationErrors);
//        validateDemandDraftType(dto);
        validateCurrencyRate(dto, fieldValidationErrors);
        validateTranCodeLimit(dto.getDemandDraftProductTranCodeLimits(), fieldValidationErrors);


    }

    private void validateCurrencyRate(CreateDemandDraftProductDTO dto, List<FieldValidationError> fieldValidationErrors) {
      //  for (DemandDraftProductChargesDTO charge:dto.getDemandDraftProductCharges()) {
            if(!StringUtils.isEmpty(dto.getDemandDraftProduct().getSellExchangeRateCode())) {
                Boolean currencyRateExists = currencyRateService.existsByRateCode(dto.getDemandDraftProduct().getSellExchangeRateCode());
                if (!currencyRateExists) {
                    FieldValidationError error = new FieldValidationError("exchangeRateCode", "Exchange Rate code not found-> " + dto.getDemandDraftProduct().getSellExchangeRateCode());
                    fieldValidationErrors.add(error);
                }
            }
     //   }
    }
//
//    private void validateIssueBankAndBranch(CreateDemandDraftProductDTO dto, List<FieldValidationError> fieldValidationErrors) {
//        if(!StringUtils.isEmpty(dto.getDemandDraftProduct().getIssueBank())){
//            Boolean bankCodeExists = financialInstitutionService.existsByBankCode(dto.getDemandDraftProduct().getIssueBank());
//            if(!bankCodeExists){
//                FieldValidationError error = new FieldValidationError("issueBank", "Issue bank does not exist-> " + dto.getDemandDraftProduct().getIssueBank());
//                fieldValidationErrors.add(error);
//            }
//        }
//        if(!StringUtils.isEmpty(dto.getDemandDraftProduct().getIssueBranch())){
//            Boolean bankBranchCodeExists = financialInstitutionService.existsByBankCodeAndBranchCode(dto.getDemandDraftProduct().getIssueBank(), dto.getDemandDraftProduct().getIssueBranch());
//            if(!bankBranchCodeExists){
//                FieldValidationError error = new FieldValidationError("issueBranch", "Issue branch  does not exist for bank-> " + dto.getDemandDraftProduct().getIssueBranch());
//                fieldValidationErrors.add(error);
//            }
//        }
//    }

    private  void validateBankProductMaster(AddBankProductMasterDTO dto, List<FieldValidationError> fieldValidationErrors) throws FieldValidationException {
        bankProductMasterValidator.validateInsert(dto);
    }

    private void validateTranCodeLimit(DemandDraftProductTranCodeLimitDTO[] dtoList, List<FieldValidationError> fieldValidationErrors) {
        for (DemandDraftProductTranCodeLimitDTO tranCodeLim: dtoList) {
           if(!StringUtils.isEmpty(tranCodeLim.getTranReportCode())){
               Boolean tranCodeExists = globalCodeService.existsByTypeAndCode("TRAN_CODE",tranCodeLim.getTranReportCode());
               if(!tranCodeExists) {
                   FieldValidationError error = new FieldValidationError("tranReportCode", "Transaction report code not found-> " + tranCodeLim.getTranReportCode());
                   fieldValidationErrors.add(error);
               }
           }
        }
    }

    private void validateProductCode(String productCode, List<FieldValidationError> fieldValidationErrors) {
        Boolean productCodeExists = repo.existsByProductCode(productCode);
        if(productCodeExists){
            FieldValidationError error = new FieldValidationError("productCode", "Product code already exist " +productCode);
            fieldValidationErrors.add(error);
        }
    }

//    private void validateInventoryCategory(String inventoryType, List<FieldValidationError> fieldValidationErrors) {
//        if (!StringUtils.isEmpty(inventoryType))
//        {
//            Boolean inventoryCategoryCodeExists = globalCodeService.existsByTypeAndCode(
//                    "INSTRUMENT_CATEGORY", inventoryType);
//
//            if (!inventoryCategoryCodeExists) {
//                FieldValidationError error = new FieldValidationError("inventoryType", "Inventory category not found-> " + inventoryType);
//                fieldValidationErrors.add(error);
//            }
//        }
//    }


//    private void validateDemandDraftType(CreateDemandDraftProductDTO dto) throws IconException {
//        if (dto.getBankProduct().getProductTypeCode().equals(BankProductType.DDRAFT) && dto.getDemandDraftProduct() == null) {
//            throw new IconException(" Demand draft Type Is Mandatory For Demand Draft ");
//        }
//    }



    public void validateUpdate(String productCode, UpdateDemandDraftProductWithDependenciesDTO dto) throws IconException {
        List<FieldValidationError> fieldValidationErrors = CommonUtils.getStaticFieldValidationErrors(
                dto.getDemandDraftProduct(), validator
        );
        if (!fieldValidationErrors.isEmpty()) {
            throw new FieldValidationException("Insert validation failed", fieldValidationErrors);
        }


        if (null == productCode)  {
            FieldValidationError error = new FieldValidationError("productCode", "Invalid productCode");
            fieldValidationErrors.add(error);
            throw new FieldValidationException("Insert validation failed", fieldValidationErrors);
        }

        Boolean productCodeExists = repo.existsByProductCode(productCode);
        if(!productCodeExists){
            FieldValidationError error = new FieldValidationError();
            error.setFieldName("productCode");
            error.setMessage("Product code not found -> " + productCode);

            fieldValidationErrors.add(error);
            throw new FieldValidationException("Update validation failed", fieldValidationErrors);

        }

        try {
            bankProductMasterValidator.validateProductUpdate(modelMapper.map(dto.getBankProduct(), UpdateBankProductMasterDTO.class));
        } catch (FieldValidationException e) {
            fieldValidationErrors.addAll(e.getFieldValidationErrors());
            throw new FieldValidationException("Update validation failed", fieldValidationErrors);
        }




    }

}