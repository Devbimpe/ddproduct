package com.lbt.icon.demanddraft.domain.demanddraft;

import com.lbt.icon.bankcommons.domain.company.bankbranch.BankBranch;
import com.lbt.icon.bankcommons.domain.company.bankbranch.BankBranchRepo;
import com.lbt.icon.bankcommons.domain.globalparams.globalcode.GlobalCodeService;
import com.lbt.icon.bankcommons.domain.globalparams.globalcode.dto.GlobalCodeDTO;
import com.lbt.icon.bankproduct.domain.branch.BankProductBranch;
import com.lbt.icon.bankproduct.domain.branch.BankProductBranchRepo;
import com.lbt.icon.bankproduct.domain.master.BankProductMasterRepo;
import com.lbt.icon.bankproduct.domain.master.BankProductMasterService;
import com.lbt.icon.bankproduct.domain.master.dto.BankProductMasterDTO;
import com.lbt.icon.bankproduct.domain.master.dto.UpdateBankProductMasterDTO;
import com.lbt.icon.bankproduct.domain.subgl.BankProductGL;
import com.lbt.icon.bankproduct.domain.subgl.BankProductGLRepo;
import com.lbt.icon.bankproduct.types.BankProductType;
import com.lbt.icon.core.exception.*;
import com.lbt.icon.demanddraft.domain.demanddraft.dto.*;
import com.lbt.icon.demanddraft.domain.demanddraftproductcharges.DemandDraftProductChargesRepository;
import com.lbt.icon.demanddraft.domain.demanddraftproductcharges.DemandDraftProductChargesService;
import com.lbt.icon.demanddraft.domain.demanddraftproductcharges.dto.DemandDraftProductChargesDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproductcharges.dto.QueryDemandDraftProductChargesDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproductinstr.DemandDraftProductInstrService;
import com.lbt.icon.demanddraft.domain.demanddraftproductinstr.dto.DemandDraftProductInstrDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproductinstr.dto.QueryDemandDraftProductInstrDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit.DemandDraftProductTranCodeLimitService;
import com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit.dto.DemandDraftProductTranCodeLimitDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit.dto.QueryDemandDraftProductTranCodeLimitDTO;
import com.lbt.icon.functional.mapper.PatchMapper;
import com.lbt.icon.ledger.setup.glcodes.subcategory.GLSubCategoryService;
import com.lbt.icon.ledger.setup.glcodes.subcategory.dto.GLSubCategoryDto;
import com.lbt.icon.makerchecker.annotation.Checkable;
import com.lbt.icon.makerchecker.annotation.DtoValidator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author devbimpe
 * @since 14/03/2019
 */

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class DemandDraftProductServiceImpl implements DemandDraftProductService {

    private final DemandDraftProductRepository demandDraftProductRepository;
    private final DemandDraftProductValidator demandDraftProductValidator;
    private final ModelMapper modelMapper;
    private final BankProductMasterService bankProductMasterService;
    private final BankProductMasterRepo bankProductMasterRepo;
    private final DemandDraftProductChargesService demandDraftProductChargesService;
    private final DemandDraftProductInstrService demandDraftProductInstrService;
    private final DemandDraftProductTranCodeLimitService demandDraftProductTranCodeLimitService;
    private final DemandDraftProductChargesRepository demanddraftProductChargesRepository;
    private final BankProductBranchRepo bankProductBranchRepo;
	private final BankBranchRepo bankBranchRepo;
	private final GlobalCodeService globalCodeService;
	private final GLSubCategoryService gLSubCategoryService;
	private final BankProductGLRepo bankProductGLRepo;

    @Override

//    @Checkable(
//            naturalIdentifier = "naturalId",
//            code = "CREATE_DEMAND_DRAFT",
//            operation = Checkable.Operation.Insert,
//            description = "create demand draft product record",
//            dtoClass = CreateDemandDraftProductDTO.class,
//            returnClass = QueryDemandDraftProductDTO.class,
//            dtoValidators = @DtoValidator(validatorClass = DemandDraftProductValidator.class, paramTypes = CreateDemandDraftProductDTO.class, validateMethod = "validate"))
    public QueryDemandDraftProductDTO create(CreateDemandDraftProductDTO dto) throws IconException{


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
                new EntityNotFoundException(String.format("Demand Draft Product %s Not found",productCode)));

        List<QueryDemandDraftProductChargesDTO> charges = demandDraftProductChargesService.findByProductCode(productCode);
        List<QueryDemandDraftProductInstrDTO> instruments = demandDraftProductInstrService.findByProductCode(productCode);
        List<QueryDemandDraftProductTranCodeLimitDTO> tranCodeLimits = demandDraftProductTranCodeLimitService.findByProductCode(productCode);
        bankProductMasterService.findAllByProductCode(productCode).ifPresent(d ->
                demandDraftProductInquiryDTO.setBankProduct(d));
        demandDraftProductInquiryDTO.setDemandDraftProductCharges(charges);
        demandDraftProductInquiryDTO.setDemandDraftProductInstruments(instruments);
        demandDraftProductInquiryDTO.setDemandDraftProductTranCodeLimits(tranCodeLimits);
        demandDraftProductInquiryDTO.setDemandDraftProduct(modelMapper.map(demandDraftProduct,QueryDemandDraftProductDTO.class));
        return demandDraftProductInquiryDTO;
    }

    @Override
    public DemandDraftProductInquiryDTO findById(Long id) throws IconException {
        DemandDraftProductInquiryDTO demandDraftProductInquiryDTO = new DemandDraftProductInquiryDTO();
        DemandDraftProduct demandDraftProduct = demandDraftProductRepository.findByIdOrThrow(id, new EntityNotFoundException(String.format("Demand Draft Product %s Not found",id)).toString());
        List<QueryDemandDraftProductChargesDTO> charges = demandDraftProductChargesService.findByProductCode(demandDraftProduct.getProductCode());
        List<QueryDemandDraftProductInstrDTO> instruments = demandDraftProductInstrService.findByProductCode(demandDraftProduct.getProductCode());
        List<QueryDemandDraftProductTranCodeLimitDTO> tranCodeLimits = demandDraftProductTranCodeLimitService.findByProductCode(demandDraftProduct.getProductCode());

        demandDraftProductInquiryDTO.setDemandDraftProductCharges(charges);
        demandDraftProductInquiryDTO.setDemandDraftProductInstruments(instruments);
        demandDraftProductInquiryDTO.setDemandDraftProductTranCodeLimits(tranCodeLimits);
        demandDraftProductInquiryDTO.setDemandDraftProduct(modelMapper.map(demandDraftProduct,QueryDemandDraftProductDTO.class));
        return demandDraftProductInquiryDTO;
    }

    @Override
    public UpdateDemandDraftProductDTO update(String productCode, UpdateDemandDraftProductDTO dto) throws IconException{
        demandDraftProductValidator.validateFields(dto.getDemandDraftProduct());
        DemandDraftProduct demandDraftProduct = demandDraftProductRepository.findByProductCode(productCode).orElseThrow(() ->
                new EntityNotFoundException(String.format("Demand DraftProduct %s Not found",productCode)));
        demandDraftProduct = PatchMapper.of(() -> dto.getDemandDraftProduct()).map(demandDraftProduct).get();
        demandDraftProduct=demandDraftProductRepository.update(demandDraftProduct);
        BankProductMasterDTO bankProductMasterDTO = bankProductMasterService.updateBasicDetails(dto.getBankProduct());
        UpdateDemandDraftProductDTO update = new UpdateDemandDraftProductDTO();
        update.setDemandDraftProduct(modelMapper.map(demandDraftProduct,QueryDemandDraftProductDTO.class));
        update.setBankProduct(modelMapper.map(bankProductMasterDTO,UpdateBankProductMasterDTO.class));

        return update;
    }

    @Override
    public UpdateDemandDraftProductWithDependenciesDTO updateDemandDraftProductWithDependencies(UpdateDemandDraftProductWithDependenciesDTO dto, String productCode) throws IconException {
        demandDraftProductValidator.validateFields(dto.getDemandDraftProduct());
        DemandDraftProduct demandDraftProduct = demandDraftProductRepository.findByProductCode(productCode).orElseThrow(() ->
                new EntityNotFoundException(String.format("Demand DraftProduct %s Not found",productCode)));
        demandDraftProduct = PatchMapper.of(() -> dto.getDemandDraftProduct()).map(demandDraftProduct).get();

        UpdateDemandDraftProductWithDependenciesDTO update = new UpdateDemandDraftProductWithDependenciesDTO();
        demandDraftProduct=demandDraftProductRepository.update(demandDraftProduct);
        BankProductMasterDTO bankProductMasterDTO = bankProductMasterService.updateBasicDetails(dto.getBankProduct());
        update.setDemandDraftProduct(modelMapper.map(demandDraftProduct,QueryDemandDraftProductDTO.class));
        update.setBankProduct(modelMapper.map(bankProductMasterDTO,UpdateBankProductMasterDTO.class));
        update.setDemandDraftProductCharges(this.updateCharges(dto.getDemandDraftProductCharges(),productCode));
        update.setDemandDraftProductInstruments(this.updateInstrument(dto.getDemandDraftProductInstruments(),productCode));
        update.setDemandDraftProductTranCodeLimits(this.updateTranCode(dto.getDemandDraftProductTranCodeLimits(),productCode));

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
    @Transactional
    public BankProductMasterDTO enableByProductCode(@NotBlank String productCode) throws EntityNotFoundException, FieldValidationException {
        return bankProductMasterService.enableByProductCode(productCode);
    }

    @Override
    @Transactional
    public BankProductMasterDTO disableByProductCode(@NotBlank String productCode) throws EntityNotFoundException, FieldValidationException {
        return bankProductMasterService.disableByProductCode(productCode);
    }

    @Override
    public List<BankProductMasterDTO> findProductsByProductCodeLike(String productCode) {
        List<DemandDraftProduct> demandDraftProducts = demandDraftProductRepository.findByProductCodeContaining(productCode.toUpperCase());
        List<BankProductMasterDTO> masterDtos = new ArrayList<>();
        for(DemandDraftProduct d : demandDraftProducts) {
            bankProductMasterService.findByProductCode(d.getProductCode()).ifPresent(m->masterDtos.add(m));
        }
        return masterDtos;

    }


    private List<QueryDemandDraftProductTranCodeLimitDTO> updateTranCode(List<QueryDemandDraftProductTranCodeLimitDTO> demandDraftProductTranCodeLimits, String productCode) throws IconException{
        return demandDraftProductTranCodeLimitService.updateTranCodeBatch(productCode,demandDraftProductTranCodeLimits);
    }

    private List<QueryDemandDraftProductInstrDTO> updateInstrument(List<QueryDemandDraftProductInstrDTO> demandDraftProductInstruments, String productCode) throws IconException{
        return demandDraftProductInstrService.updateInstrBatch(productCode,demandDraftProductInstruments);
    }

    private List<QueryDemandDraftProductChargesDTO> updateCharges(List<QueryDemandDraftProductChargesDTO> demandDraftProductCharges, String productCode) throws IconException{
        return  demandDraftProductChargesService.updateChargeBatch(productCode,demandDraftProductCharges);
    }
    


	

	@Override
	public boolean hasAccountNumberGenCode(String productCode) {
		String genCode = getAccountNumberGenCodeByProductCode(productCode);
		return !genCode.isEmpty();
	}
	
	@Override
	public String getAccountNumberGenCodeByProductCode(String productCode) {
		Optional<String> optional = bankProductMasterRepo.getAcctGenCodeUsingProductCode(productCode);
		return optional.isPresent() ? optional.get().trim() : "";
	}

	@Override
	public List<DemandDraftProductBranchDto> findBranchesByProductCode(String productCode) {
		
		List<DemandDraftProductBranchDto> demandDraftProductBranchDtos = new ArrayList<DemandDraftProductBranchDto>();
		
		List<BankProductBranch> bankProductBranches = bankProductBranchRepo.findAllByProductCodeIgnoreCase(productCode);
		for(BankProductBranch bankProductBranch : bankProductBranches) {
			String branchCode = bankProductBranch.getBranchCode() != null ? bankProductBranch.getBranchCode().trim() : "";
			Optional<BankBranch> optional = bankBranchRepo.findFirstByBranchCodeIgnoreCase(branchCode);
			if(optional.isPresent()) {
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
		if(optional.isPresent()) {
			
			DemandDraftProduct bankDemandDraftProduct = optional.get();

			//List<DemandDraftProductSpacerCode> demandDraftProductSpacerCodes = demandDraftSpacerCodeRepo.findAllByProductCode(productCode);
			//List<String> spacerCodes = demandDraftProductSpacerCodes.stream().map(o -> o.getSpacerCode()).collect(Collectors.toList());
			List<String> spacerCodes = new ArrayList<String>(); // above line PENDING implementation
			List<DemandDraftProductSpacerCodeDto> demandDraftProductSpacerCodeDtos = new ArrayList<>();
			for(String spacerCode : spacerCodes) {
				
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
		}
		else
			return new ArrayList<>();
	}

	@Override
	public List<DemandDraftProductGlDto> findGlsByProductCode(String productCode) {//gLSubCategoryService
		
		List<DemandDraftProductGlDto> demandDraftProductGlDtos = new ArrayList<DemandDraftProductGlDto>();
		
		List<BankProductGL> bankProductGls = bankProductGLRepo.findByProductCodeIgnoreCase(productCode);
		for(BankProductGL bankProductGl : bankProductGls) {
			String glSubCode = bankProductGl.getGlsubCode() != null ? bankProductGl.getGlsubCode().trim() : "";			
			Optional<GLSubCategoryDto> optional = null;
			try {
				optional = gLSubCategoryService.findFirstByCode(glSubCode);
				
				if(optional.isPresent()) {
					demandDraftProductGlDtos.add(
						DemandDraftProductGlDto.builder()
							.glSubCode(glSubCode)
							.description(optional.get().getDescription())
							.build()
					);
				}
			} catch (EntityNotFoundException iqe) {				
			} catch (IconQueryException iqe) {				
			}
		}
		
		
		return demandDraftProductGlDtos;
	}
}
