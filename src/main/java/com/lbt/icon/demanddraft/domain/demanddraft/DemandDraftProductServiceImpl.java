package com.lbt.icon.demanddraft.domain.demanddraft;

import com.lbt.icon.audit.stereotype.FuncAudit;
import com.lbt.icon.bankcommons.domain.company.bankbranch.BankBranch;
import com.lbt.icon.bankcommons.domain.company.bankbranch.BankBranchRepo;
import com.lbt.icon.bankcommons.domain.globalparams.globalcode.GlobalCodeService;
import com.lbt.icon.bankcommons.domain.globalparams.globalcode.dto.GlobalCodeDTO;
import com.lbt.icon.bankcommons.domain.nextnumbergenerator.NextNumberGeneratorService;
import com.lbt.icon.bankcommons.domain.nextnumbergenerator.dto.NextNumberGeneratorCodeDTO;
import com.lbt.icon.bankproduct.domain.branch.BankProductBranch;
import com.lbt.icon.bankproduct.domain.branch.BankProductBranchRepo;
import com.lbt.icon.bankproduct.domain.master.BankProductMasterRepo;
import com.lbt.icon.bankproduct.domain.master.BankProductMasterService;
import com.lbt.icon.bankproduct.domain.master.BankProductMasterValidator;
import com.lbt.icon.bankproduct.domain.master.dto.BankProductMasterDTO;
import com.lbt.icon.bankproduct.domain.master.dto.UpdateBankProductMasterDTO;
import com.lbt.icon.bankproduct.domain.subgl.BankProductGL;
import com.lbt.icon.bankproduct.domain.subgl.BankProductGLRepo;
import com.lbt.icon.bankproduct.types.BankProductType;
import com.lbt.icon.core.exception.*;
import com.lbt.icon.demanddraft.config.DDProductPermissionEnum;
import com.lbt.icon.demanddraft.domain.demanddraft.dto.*;
import com.lbt.icon.demanddraft.domain.demanddraftproductcharges.DemandDraftProductChargesService;
import com.lbt.icon.demanddraft.domain.demanddraftproductcharges.dto.DemandDraftProductChargesDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproductcharges.dto.QueryDemandDraftProductChargesDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproductinstr.DemandDraftProductInstrService;
import com.lbt.icon.demanddraft.domain.demanddraftproductinstr.dto.DemandDraftProductInstrDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproductinstr.dto.QueryDemandDraftProductInstrDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit.DemandDraftProductTranCodeLimitService;
import com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit.dto.DemandDraftProductTranCodeLimitDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit.dto.QueryDemandDraftProductTranCodeLimitDTO;
import com.lbt.icon.excd.domain.exceptiondefinition.ExceptionDefinitionService;
import com.lbt.icon.excd.domain.exceptiondefinition.dto.ExceptionDefinitionQueryDto;
import com.lbt.icon.excd.domain.exceptiondefinition.dto.ExceptionDefinitionUpdatedDto;
import com.lbt.icon.functional.mapper.PatchMapper;

import com.lbt.icon.ledger.setup.glcodes.subcategory.GlSubCategoryService;
import com.lbt.icon.ledger.setup.glcodes.subcategory.domain.dto.GLSubCategoryDto;
import com.lbt.icon.makerchecker.annotation.Checkable;
import com.lbt.icon.makerchecker.annotation.DtoValidator;
import com.lbt.icon.makerchecker.annotation.IdentifierFinderConfig;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import javax.validation.constraints.NotBlank;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author devbimpe
 * @since 14/03/2019
 */

@Service
@RequiredArgsConstructor
public class DemandDraftProductServiceImpl implements DemandDraftProductService {

    private final BankBranchRepo bankBranchRepo;
    private final BankProductBranchRepo bankProductBranchRepo;
    private final BankProductGLRepo bankProductGLRepo;
    private final BankProductMasterRepo bankProductMasterRepo;
    private final BankProductMasterService bankProductMasterService;
    private final DemandDraftProductChargesService demandDraftProductChargesService;
    private final DemandDraftProductInstrService demandDraftProductInstrService;
    private final DemandDraftProductRepository demandDraftProductRepository;
    private final DemandDraftProductTranCodeLimitService demandDraftProductTranCodeLimitService;

    private final DemandDraftProductValidator demandDraftProductValidator;
    private final GlSubCategoryService gLSubCategoryService;
    private final GlobalCodeService globalCodeService;
    private final ModelMapper modelMapper;
    private final ExceptionDefinitionService exceptionDefinitionService;



    @Override
    @Checkable(
            naturalIdentifier = "naturalId",
            code = "CREATE_DEMAND_DRAFT",
            operation = Checkable.Operation.INSERT,
            description = "create demand draft product record",
            dtoClass = CreateDemandDraftProductDTO.class,
            returnClass = QueryDemandDraftProductDTO.class,

            dtoValidators = @DtoValidator(validatorClass = DemandDraftProductValidator.class,
                    paramTypes = CreateDemandDraftProductDTO.class,
                    validateMethod = "validate"
            ) ,approvalPermissions = {DDProductPermissionEnum.Authority.AUTHORIZE_DD_PRODUCT})
    @FuncAudit(operation = {DDProductPermissionEnum.Authority.CREATE_DD_PRODUCT}, module = "DEMAND DRAFT PRODUCT")
    @Transactional(rollbackFor = Exception.class, noRollbackFor = {FieldValidationException.class,IconException.class} )
    @PreAuthorize("hasAuthority('" + DDProductPermissionEnum.Authority.CREATE_DD_PRODUCT + "')")
    public QueryDemandDraftProductDTO create(CreateDemandDraftProductDTO dto) throws IconException {
        BankProductMasterDTO bpm = null;
        demandDraftProductValidator.validate(dto);
        QueryDemandDraftProductDTO queryDemandDraftProductDTO = null;
        bpm = bankProductMasterService.create(dto.getBankProduct());
        String productCode = bpm.getProductCode();
        dto.getDemandDraftProduct().setProductCode(productCode);
        for (DemandDraftProductChargesDTO charge : dto.getDemandDraftProductCharges()) {
            charge.setProductCode(productCode);
            demandDraftProductChargesService.create(charge);
        }
        for (DemandDraftProductInstrDTO instr : dto.getDemandDraftProductInstruments()) {
            instr.setProductCode(productCode);
            demandDraftProductInstrService.create(instr);
        }
        for (DemandDraftProductTranCodeLimitDTO tranCodeLim : dto.getDemandDraftProductTranCodeLimits()) {
            tranCodeLim.setProductCode(productCode);
            demandDraftProductTranCodeLimitService.create(tranCodeLim);
        }


        if (dto.getBankProduct().getExceptionIdentifierCodes() != null) {
            try {
                exceptionDefinitionService.updateExceptionDefinitions(productCode,dto.getBankProduct().getProductTypeCode().getCode(), ExceptionDefinitionUpdatedDto.builder().identifierCodes(dto.getBankProduct().getExceptionIdentifierCodes()).build());
            } catch (IconException e) {
                e.printStackTrace();
                throw new IconException(e.getMessage());
            }

        }

        DemandDraftProduct demandDraftProduct = modelMapper.map(dto.getDemandDraftProduct(), DemandDraftProduct.class);
        queryDemandDraftProductDTO = new QueryDemandDraftProductDTO();
        queryDemandDraftProductDTO.setBankProductMasterDTO(bpm);
        modelMapper.map(demandDraftProductRepository.create(demandDraftProduct), queryDemandDraftProductDTO);
        return queryDemandDraftProductDTO;


    }

    @Override
    public DemandDraftProductInquiryDTO inquireByProductCode(String productCode) throws IconException {
        DemandDraftProductInquiryDTO demandDraftProductInquiryDTO = new DemandDraftProductInquiryDTO();
        DemandDraftProduct demandDraftProduct = demandDraftProductRepository.findByProductCode(productCode).orElseThrow(() ->
                new EntityNotFoundException(String.format("Demand Draft Product %s Not found", productCode)));

        List<QueryDemandDraftProductChargesDTO> charges = demandDraftProductChargesService.findByProductCode(productCode);
        List<QueryDemandDraftProductInstrDTO> instruments = demandDraftProductInstrService.findByProductCode(productCode);
        List<QueryDemandDraftProductTranCodeLimitDTO> tranCodeLimits = demandDraftProductTranCodeLimitService.findByProductCode(productCode);

        List<ExceptionDefinitionQueryDto> exceptionDTOS = exceptionDefinitionService.findByProductCodeAndProductTypeCode(productCode,BankProductType.DDRAFT.getCode());
        if (exceptionDTOS != null && !exceptionDTOS.isEmpty()) {
            demandDraftProductInquiryDTO.setExceptionDto(exceptionDTOS);
        }

        demandDraftProductInquiryDTO.setProductCode(productCode);
        demandDraftProductInquiryDTO.setBankProduct(bankProductMasterService.findByDemandDraftProductCode(productCode));

        demandDraftProductInquiryDTO.setDemandDraftProductCharges(charges);
        demandDraftProductInquiryDTO.setDemandDraftProductInstruments(instruments);
        demandDraftProductInquiryDTO.setDemandDraftProductTranCodeLimits(tranCodeLimits);
        demandDraftProductInquiryDTO.setDemandDraftProduct(modelMapper.map(demandDraftProduct, QueryDemandDraftProductDTO.class));
        return demandDraftProductInquiryDTO;
    }

    @Override
    public DemandDraftProductInquiryDTO findById(Long id) throws IconException {
        DemandDraftProductInquiryDTO demandDraftProductInquiryDTO = new DemandDraftProductInquiryDTO();
        DemandDraftProduct demandDraftProduct = demandDraftProductRepository.findByIdOrThrow(id, new EntityNotFoundException(String.format("Demand Draft Product %s Not found", id)).toString());
        List<QueryDemandDraftProductChargesDTO> charges = demandDraftProductChargesService.findByProductCode(demandDraftProduct.getProductCode());
        List<QueryDemandDraftProductInstrDTO> instruments = demandDraftProductInstrService.findByProductCode(demandDraftProduct.getProductCode());
        List<QueryDemandDraftProductTranCodeLimitDTO> tranCodeLimits = demandDraftProductTranCodeLimitService.findByProductCode(demandDraftProduct.getProductCode());

        demandDraftProductInquiryDTO.setDemandDraftProductCharges(charges);
        demandDraftProductInquiryDTO.setDemandDraftProductInstruments(instruments);
        demandDraftProductInquiryDTO.setDemandDraftProductTranCodeLimits(tranCodeLimits);
        demandDraftProductInquiryDTO.setDemandDraftProduct(modelMapper.map(demandDraftProduct, QueryDemandDraftProductDTO.class));
        return demandDraftProductInquiryDTO;
    }





//    @Override
//    @Checkable(
//            naturalIdentifier = "productCode",
//            code = "UPDATE_DEMAND_DRAFT",
//            operation = Checkable.Operation.Update,
//            description = "update demand draft product record",
//            dtoClass = UpdateDemandDraftProductDTO.class,
//            returnClass = UpdateDemandDraftProductDTO.class,
//
//            identifierFinderConfigs = {
//                    @IdentifierFinderConfig(
//                            finderClass = DemandDraftProductServiceImpl.class,
//                            finderMethod = "inquireByProductCode",
//                            identifierClass = String.class
//                    )
//            },
//
//            dtoValidators = @DtoValidator(validatorClass = DemandDraftProductValidator.class,
//                    paramTypes = {String.class, UpdateDemandDraftProductDTO.class},
//                    validateMethod = "validateUpdate"
//            ))
//    @PreAuthorize("hasAuthority('" + DDProductPermissionEnum.Authority.UPDATE_DD_PRODUCT + "')")
//    public UpdateDemandDraftProductDTO update(String productCode, UpdateDemandDraftProductDTO dto) throws IconException {
//        //demandDraftProductValidator.validateUpdate(productCode,dto);
//        DemandDraftProduct demandDraftProduct = demandDraftProductRepository.findByProductCode(productCode).orElseThrow(() ->
//                new EntityNotFoundException(String.format("Demand DraftProduct %s Not found", productCode)));
//        if (dto.getDemandDraftProduct().getAllowRevalidate() != null && dto.getDemandDraftProduct().getAllowRevalidate() && StringUtils.isEmpty(dto.getDemandDraftProduct().getRevalidatePeriod())) {
//            FieldValidationError error = new FieldValidationError("revalidatePeriod", "Revalidate period cannot be null when allowRevalidate  is true ");
//            throw new FieldValidationException("revalidatePeriod", Collections.singletonList(error));
//
//        }
//        demandDraftProduct = PatchMapper.of(() -> dto.getDemandDraftProduct()).map(demandDraftProduct).get();
//        demandDraftProduct = demandDraftProductRepository.update(demandDraftProduct);
//        BankProductMasterDTO bankProductMasterDTO = bankProductMasterService.updateBasicDetails(dto.getBankProduct());
//        UpdateDemandDraftProductDTO update = new UpdateDemandDraftProductDTO();
//        update.setDemandDraftProduct(modelMapper.map(demandDraftProduct, QueryDemandDraftProductDTO.class));
//        update.setBankProduct(modelMapper.map(bankProductMasterDTO, UpdateBankProductMasterDTO.class));
//
//        return update;
//    }

    @Override
    @Checkable(
            naturalIdentifier = "productCode",
            code = "UPDATE_DEMAND_DRAFT",
            operation = Checkable.Operation.UPDATE,
            description = "update demand draft product record",
            dtoClass = UpdateDemandDraftProductWithDependenciesDTO.class,
            returnClass = UpdateDemandDraftProductWithDependenciesDTO.class,

            identifierFinderConfigs = {
                    @IdentifierFinderConfig(
                            finderClass = DemandDraftProductServiceImpl.class,
                            finderMethod = "inquireByProductCode",
                            identifierClass = String.class
                    )
            },

            dtoValidators = @DtoValidator(validatorClass = DemandDraftProductValidator.class,
                    paramTypes = {String.class, UpdateDemandDraftProductWithDependenciesDTO.class},
                    validateMethod = "validateUpdate"
            ),approvalPermissions = {DDProductPermissionEnum.Authority.AUTHORIZE_DD_PRODUCT})
    @FuncAudit(operation = {DDProductPermissionEnum.Authority.UPDATE_DD_PRODUCT}, module = "DEMAND DRAFT PRODUCT")
    @Transactional(rollbackFor = Exception.class, noRollbackFor = {FieldValidationException.class,IconException.class} )
    @PreAuthorize("hasAuthority('" + DDProductPermissionEnum.Authority.UPDATE_DD_PRODUCT + "')")
    public UpdateDemandDraftProductWithDependenciesDTO updateDemandDraftProductWithDependencies(String productCode,UpdateDemandDraftProductWithDependenciesDTO dto) throws IconException {

        DemandDraftProduct demandDraftProduct = demandDraftProductRepository.findByProductCode(productCode).orElseThrow(() ->
                new EntityNotFoundException(String.format("Demand DraftProduct %s Not found", productCode)));
//        if (dto.getDemandDraftProduct().getAllowRevalidate() != null && dto.getDemandDraftProduct().getAllowRevalidate() && StringUtils.isEmpty(dto.getDemandDraftProduct().getRevalidatePeriod())) {
//            FieldValidationError error = new FieldValidationError("revalidatePeriod", "Revalidate period cannot be null when allowRevalidate  is true ");
//            throw new FieldValidationException("revalidatePeriod", Collections.singletonList(error));
//
//        }
        demandDraftProductValidator.validateUpdate(productCode,dto);

        demandDraftProduct = PatchMapper.of(() -> dto.getDemandDraftProduct()).map(demandDraftProduct).get();

        UpdateDemandDraftProductWithDependenciesDTO update = new UpdateDemandDraftProductWithDependenciesDTO();
        demandDraftProduct = demandDraftProductRepository.update(demandDraftProduct);
        BankProductMasterDTO bankProductMasterDTO = bankProductMasterService.updateOne(dto.getBankProduct());
        if (dto.getBankProduct().getExceptionIdentifierCodes() != null) {
            try {
                exceptionDefinitionService.updateExceptionDefinitions(productCode,dto.getBankProduct().getProductTypeCode().getCode(), ExceptionDefinitionUpdatedDto.builder().identifierCodes(dto.getBankProduct().getExceptionIdentifierCodes()).build());
            } catch (IconException e) {
                e.printStackTrace();
                throw new IconException(e.getMessage());
            }

        }
        update.setDemandDraftProduct(modelMapper.map(demandDraftProduct, QueryDemandDraftProductDTO.class));
        update.setBankProduct(modelMapper.map(bankProductMasterDTO, UpdateBankProductMasterDTO.class));
        update.setDemandDraftProductCharges(this.updateCharges(dto.getDemandDraftProductCharges(), productCode));
        update.setDemandDraftProductInstruments(this.updateInstrument(dto.getDemandDraftProductInstruments(), productCode));
        update.setDemandDraftProductTranCodeLimits(this.updateTranCode(dto.getDemandDraftProductTranCodeLimits(), productCode));

        return update;
    }


    @Override
    @Checkable(
            naturalIdentifier = "productCode",
            code = "UPDATE_DEMAND_DRAFT",
            operation = Checkable.Operation.UPDATE,
            description = "update demand draft product record",
            dtoClass = UpdateDemandDraftProductWithDependenciesDTO.class,
            returnClass = UpdateDemandDraftProductWithDependenciesDTO.class,

            identifierFinderConfigs = {
                    @IdentifierFinderConfig(
                            finderClass = DemandDraftProductServiceImpl.class,
                            finderMethod = "findById",
                            identifierClass = Long.class
                    )
            },

            dtoValidators = @DtoValidator(validatorClass = DemandDraftProductValidator.class,
                    paramTypes = {String.class, UpdateDemandDraftProductWithDependenciesDTO.class},
                    validateMethod = "validateUpdate"
            ),approvalPermissions = {DDProductPermissionEnum.Authority.AUTHORIZE_DD_PRODUCT})
    @FuncAudit(operation = {DDProductPermissionEnum.Authority.UPDATE_DD_PRODUCT}, module = "DEMAND DRAFT PRODUCT")
    @Transactional(rollbackFor = Exception.class, noRollbackFor = {FieldValidationException.class,IconException.class} )
    @PreAuthorize("hasAuthority('" + DDProductPermissionEnum.Authority.UPDATE_DD_PRODUCT + "')")
    public UpdateDemandDraftProductWithDependenciesDTO updateDemandDraftProductWithDependencies(Long id, UpdateDemandDraftProductWithDependenciesDTO dto) throws IconException {
        DemandDraftProduct demandDraftProduct = demandDraftProductRepository.findByProductCode(dto.getBankProduct().getProductCode()).orElseThrow(() ->
                new EntityNotFoundException(String.format("Demand DraftProduct %s Not found", dto.getBankProduct().getProductCode())));
        demandDraftProductValidator.validateUpdate(dto.getProductCode(),dto);

        demandDraftProduct = PatchMapper.of(() -> dto.getDemandDraftProduct()).map(demandDraftProduct).get();

        UpdateDemandDraftProductWithDependenciesDTO update = new UpdateDemandDraftProductWithDependenciesDTO();
        demandDraftProduct = demandDraftProductRepository.update(demandDraftProduct);
        BankProductMasterDTO bankProductMasterDTO = bankProductMasterService.updateOne(dto.getBankProduct());
        if (dto.getBankProduct().getExceptionIdentifierCodes() != null) {
            try {
                exceptionDefinitionService.updateExceptionDefinitions(dto.getBankProduct().getProductCode(),dto.getBankProduct().getProductTypeCode().getCode(), ExceptionDefinitionUpdatedDto.builder().identifierCodes(dto.getBankProduct().getExceptionIdentifierCodes()).build());
            } catch (IconException e) {
                e.printStackTrace();
                throw new IconException(e.getMessage());
            }

        }
        update.setDemandDraftProduct(modelMapper.map(demandDraftProduct, QueryDemandDraftProductDTO.class));
        update.setBankProduct(modelMapper.map(bankProductMasterDTO, UpdateBankProductMasterDTO.class));
        update.setDemandDraftProductCharges(this.updateCharges(dto.getDemandDraftProductCharges(), dto.getBankProduct().getProductCode()));
        update.setDemandDraftProductInstruments(this.updateInstrument(dto.getDemandDraftProductInstruments(), dto.getBankProduct().getProductCode()));
        update.setDemandDraftProductTranCodeLimits(this.updateTranCode(dto.getDemandDraftProductTranCodeLimits(), dto.getBankProduct().getProductCode()));

        return update;
    }

    @Override
    public Page<BankProductMasterDTO> findAll(Pageable pageable, BankProductType productType) throws IconQueryException {
        PageImpl<BankProductMasterDTO> page = bankProductMasterService.listAll(productType, pageable);
        List<BankProductMasterDTO> findAll = page
                .get()
                .map(crud -> modelMapper.map(crud, BankProductMasterDTO.class))
                .collect(Collectors.toList());
        return new PageImpl<>(
                findAll,
                page.getPageable(),
                findAll.size()
        );


    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = {FieldValidationException.class,EntityNotFoundException.class} )
    @Checkable(
            code="ENABLE_DEMAND_DRAFT",
            description="Enable a demand draft product",
            operation=Checkable.Operation.OTHERS,
            returnClass=BankProductMasterDTO.class,
            dtoValidators = @DtoValidator(
                    validatorClass = BankProductMasterValidator.class,
                    validateMethod = "validateEnable",
                    paramTypes = {String.class}
            ),
            identifierFinderConfigs = {
                    @IdentifierFinderConfig(
                            finderClass = DemandDraftProductServiceImpl.class,
                            finderMethod = "inquireByProductCode",
                            identifierClass = String.class
                    )
            },
            naturalIdentifier="productCode"
            ,approvalPermissions = {DDProductPermissionEnum.Authority.AUTHORIZE_DD_PRODUCT})
    @FuncAudit(operation = {DDProductPermissionEnum.Authority.ENABLE_DD_PRODUCT}, module = "DEMAND DRAFT PRODUCT")
    @PreAuthorize("hasAuthority('" + DDProductPermissionEnum.Authority.ENABLE_DD_PRODUCT + "')")
    public BankProductMasterDTO enableByProductCode(@NotBlank String productCode) throws EntityNotFoundException, FieldValidationException {
        return bankProductMasterService.enableByProductCode(productCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, noRollbackFor = {FieldValidationException.class,EntityNotFoundException.class} )
    @Checkable(
            code="DISABLE_DEMAND_DRAFT",
            description="Disable a demand draft product",
            operation=Checkable.Operation.OTHERS,
            returnClass=BankProductMasterDTO.class,
            dtoValidators = @DtoValidator(
                    validatorClass = BankProductMasterValidator.class,
                    validateMethod = "validateDisable",
                    paramTypes = {String.class}
            ),
            identifierFinderConfigs = {
                    @IdentifierFinderConfig(
                            finderClass = DemandDraftProductServiceImpl.class,
                            finderMethod = "inquireByProductCode",
                            identifierClass = String.class
                    )
            },
            naturalIdentifier="productCode"
            ,approvalPermissions = {DDProductPermissionEnum.Authority.AUTHORIZE_DD_PRODUCT})
    @PreAuthorize("hasAuthority('" + DDProductPermissionEnum.Authority.DISABLE_DD_PRODUCT + "')")
    @FuncAudit(operation = {DDProductPermissionEnum.Authority.DISABLE_DD_PRODUCT}, module = "DEMAND DRAFT PRODUCT")
    public BankProductMasterDTO disableByProductCode(@NotBlank String productCode) throws EntityNotFoundException, FieldValidationException {
        return bankProductMasterService.disableByProductCode(productCode);
    }


    @Override
    public List<BankProductMasterDTO> findProductsByProductCodeLike(String productCode) {
        List<DemandDraftProduct> demandDraftProducts = demandDraftProductRepository.findByProductCodeContaining(productCode.toUpperCase());
        List<BankProductMasterDTO> masterDtos = new ArrayList<>();
        for (DemandDraftProduct d : demandDraftProducts) {
            bankProductMasterService.findByProductCode(d.getProductCode()).ifPresent(m -> masterDtos.add(m));
        }
        return masterDtos;

    }

    @Override
    public String getAccountNumberGenCodeByProductCode(String productCode) {
        Optional<String> optional = bankProductMasterRepo.getAcctGenCodeUsingProductCode(productCode);
        return optional.isPresent() ? optional.get().trim() : "";
    }

    @Override
    public boolean hasAccountNumberGenCode(String productCode) {
        String genCode = getAccountNumberGenCodeByProductCode(productCode);
        return !genCode.isEmpty();
    }

    @Override
    public List<DemandDraftProductBranchDto> findBranchesByProductCode(String productCode) {

        List<DemandDraftProductBranchDto> demandDraftProductBranchDtos = new ArrayList<DemandDraftProductBranchDto>();

        List<BankProductBranch> bankProductBranches = bankProductBranchRepo.findAllByProductCodeIgnoreCase(productCode);
        for (BankProductBranch bankProductBranch : bankProductBranches) {
            String branchCode = bankProductBranch.getBranchCode() != null ? bankProductBranch.getBranchCode().trim() : "";
            Optional<BankBranch> optional = bankBranchRepo.findFirstByBranchCodeIgnoreCase(branchCode);
            if (optional.isPresent()) {
                DemandDraftProductBranchDto demandDraftProductBranchDto = DemandDraftProductBranchDto.builder()
                        .branchCode(branchCode)
                        .description(optional.get().getShortName())
                        .build();
                demandDraftProductBranchDtos.add(demandDraftProductBranchDto);
            }
        }
        return demandDraftProductBranchDtos;
    }

    @Override
    public List<DemandDraftProductCurrencyDto> findCurrenciesByProductCode(String productCode) {

        List<DemandDraftProductCurrencyDto> demandDraftProductCurrencyDtos = demandDraftProductRepository.findCurrencyDtoForProduct(productCode);
        return demandDraftProductCurrencyDtos;
    }

    @Override
    public List<DemandDraftProductSpacerCodeDto> findSpacersByProductCode(String productCode) {
        Optional<DemandDraftProduct> optional = demandDraftProductRepository.findByProductCode(productCode);
        if (optional.isPresent()) {

            DemandDraftProduct bankDemandDraftProduct = optional.get();

            //List<DemandDraftProductSpacerCode> demandDraftProductSpacerCodes = demandDraftSpacerCodeRepo.findAllByProductCode(productCode);
            //List<String> spacerCodes = demandDraftProductSpacerCodes.stream().map(o -> o.getSpacerCode()).collect(Collectors.toList());
            List<String> spacerCodes = new ArrayList<String>(); // above line PENDING implementation
            List<DemandDraftProductSpacerCodeDto> demandDraftProductSpacerCodeDtos = new ArrayList<>();
            for (String spacerCode : spacerCodes) {

                GlobalCodeDTO globalCodeDto = null;
                try {
                    globalCodeDto = globalCodeService.findByTypeAndCode("SPACER", spacerCode);
                } catch (IconQueryException e) {
                }
                String description = (globalCodeDto != null ? globalCodeDto.getDescription() : "");
                DemandDraftProductSpacerCodeDto demandDraftProductSpacerDto = DemandDraftProductSpacerCodeDto.builder()
                        .spacerCode(spacerCode)
                        .description(description)
                        .build();
                demandDraftProductSpacerCodeDtos.add(demandDraftProductSpacerDto);
            }
            return demandDraftProductSpacerCodeDtos;
        } else
            return new ArrayList<>();
    }

    @Override
    public List<DemandDraftProductGlDto> findGlsByProductCode(String productCode) {//gLSubCategoryService

        List<DemandDraftProductGlDto> demandDraftProductGlDtos = new ArrayList<DemandDraftProductGlDto>();

        List<BankProductGL> bankProductGls = bankProductGLRepo.findByProductCodeIgnoreCase(productCode);
        for (BankProductGL bankProductGl : bankProductGls) {
            String glSubCode = bankProductGl.getGlsubCode() != null ? bankProductGl.getGlsubCode().trim() : "";
            GLSubCategoryDto optional = null;
            try {
                optional = gLSubCategoryService.findByCode(glSubCode);

                    demandDraftProductGlDtos.add(
                            DemandDraftProductGlDto.builder()
                                    .glSubCode(glSubCode)
                                    .description(optional.getDescription())
                                    .build()
                    );

            } catch (IconQueryException iqe) {
            }
        }


        return demandDraftProductGlDtos;
    }

    private List<QueryDemandDraftProductChargesDTO> updateCharges(List<QueryDemandDraftProductChargesDTO> demandDraftProductCharges, String productCode) throws IconException {

        return demandDraftProductChargesService.updateChargeBatch(productCode, demandDraftProductCharges);
    }

    private List<QueryDemandDraftProductInstrDTO> updateInstrument(List<QueryDemandDraftProductInstrDTO> demandDraftProductInstruments, String productCode) throws IconException {
        return demandDraftProductInstrService.updateInstrBatch(productCode, demandDraftProductInstruments);
    }

    private List<QueryDemandDraftProductTranCodeLimitDTO> updateTranCode(List<QueryDemandDraftProductTranCodeLimitDTO> demandDraftProductTranCodeLimits, String productCode) throws IconException {
        return demandDraftProductTranCodeLimitService.updateTranCodeBatch(productCode, demandDraftProductTranCodeLimits);
    }


}
