
package com.lbt.icon.demanddraft.domain.demanddraft;


import com.lbt.icon.bankproduct.domain.master.BankProductMasterValidator;
import com.lbt.icon.bankproduct.domain.master.dto.AddBankProductMasterDTO;
import com.lbt.icon.bankproduct.types.BankProductType;
import com.lbt.icon.core.exception.FieldValidationError;
import com.lbt.icon.core.exception.FieldValidationException;
import com.lbt.icon.core.exception.FieldValidationRuntimeException;
import com.lbt.icon.core.exception.IconException;
import com.lbt.icon.core.setup.common.currencyrate.CurrencyRateService;
import com.lbt.icon.core.setup.common.financialinstitution.FinancialInstitutionService;
import com.lbt.icon.core.setup.globalparams.globalcode.GlobalCodeService;
import com.lbt.icon.core.util.CommonUtils;
import com.lbt.icon.demanddraft.domain.demanddraft.dto.CreateDemandDraftProductDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproductcharges.dto.DemandDraftProductChargesDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit.dto.DemandDraftProductTranCodeLimitDTO;
import lombok.RequiredArgsConstructor;
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
    private  final  CurrencyRateService currencyRateService;
    private final FinancialInstitutionService financialInstitutionService;
    private final BankProductMasterValidator bankProductMasterValidator;

    public void validateFields(Object obj) {
        List<FieldValidationError> fieldValidationErrors =
                CommonUtils.getStaticFieldValidationErrors(
                        obj,
                        validator
                );

        if (fieldValidationErrors.isEmpty()) {
            return;
        }

        throw new FieldValidationRuntimeException(fieldValidationErrors);
    }

    public void validate(CreateDemandDraftProductDTO dto) throws IconException{
        List<FieldValidationError> fieldValidationErrors = CommonUtils.getStaticFieldValidationErrors(
                dto, validator
        );
        try {
            validateBankProductMaster(dto.getBankProduct(),fieldValidationErrors);
        } catch (FieldValidationException e) {

            e.printStackTrace();
            throw e;
        }
        validateFields(dto.getDemandDraftProduct());
        validateProductCode(dto.getBankProduct().getProductCode(), fieldValidationErrors);
//        validateInventoryCategory(dto.getDemandDraftProduct().getInventoryCategory(), fieldValidationErrors);
//        validateIssueBankAndBranch(dto, fieldValidationErrors);
//        validateDemandDraftType(dto);
        validateCurrencyRate(dto, fieldValidationErrors);
        validateTranCodeLimit(dto.getDemandDraftProductTranCodeLimits(), fieldValidationErrors);


    }

    private void validateCurrencyRate(CreateDemandDraftProductDTO dto, List<FieldValidationError> fieldValidationErrors) {
        for (DemandDraftProductChargesDTO charge:dto.getDemandDraftProductCharges()) {
            if(!StringUtils.isEmpty(charge.getExchangeRateCode())) {
                Boolean currencyRateExists = currencyRateService.existsByRateCode(charge.getExchangeRateCode());
                if (!currencyRateExists) {
                    FieldValidationError error = new FieldValidationError("exchangeRateCode", "Exchange Rate code not found-> " + charge.getExchangeRateCode());
                    fieldValidationErrors.add(error);
                }
            }
        }
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
           if(!StringUtils.isEmpty(tranCodeLim.getTranCode())){
               Boolean tranCodeExists = globalCodeService.existsByTypeAndCode("TRAN_CODE",tranCodeLim.getTranCode());
               if(!tranCodeExists) {
                   FieldValidationError error = new FieldValidationError("tranReportCode", "Transaction report code not found-> " + tranCodeLim.getTranCode());
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

//    private void validateInventoryCategory(String inventoryCategory, List<FieldValidationError> fieldValidationErrors) {
//        if (!StringUtils.isEmpty(inventoryCategory))
//        {
//            Boolean inventoryCategoryCodeExists = globalCodeService.existsByTypeAndCode(
//                    "INSTRUMENT_CATEGORY", inventoryCategory);
//
//            if (!inventoryCategoryCodeExists) {
//                FieldValidationError error = new FieldValidationError("inventoryCategory", "Inventory category not found-> " + inventoryCategory);
//                fieldValidationErrors.add(error);
//            }
//        }
//    }


//    private void validateDemandDraftType(CreateDemandDraftProductDTO dto) throws IconException {
//        if (dto.getBankProduct().getProductTypeCode().equals(BankProductType.DDRAFT) && dto.getDemandDraftProduct() == null) {
//            throw new IconException(" Demand draft Type Is Mandatory For Demand Draft ");
//        }
//    }

}