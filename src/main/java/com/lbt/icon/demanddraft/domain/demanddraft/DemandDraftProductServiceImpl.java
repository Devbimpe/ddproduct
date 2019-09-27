package com.lbt.icon.demanddraft.domain.demanddraft;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lbt.icon.bankproduct.domain.master.BankProductMasterService;
import com.lbt.icon.bankproduct.domain.master.dto.BankProductMasterDTO;
import com.lbt.icon.bankproduct.domain.master.dto.UpdateBankProductMasterDTO;
import com.lbt.icon.bankproduct.types.BankProductType;
import com.lbt.icon.core.exception.*;
import com.lbt.icon.demanddraft.domain.demanddraft.dto.CreateDemandDraftProductDTO;
import com.lbt.icon.demanddraft.domain.demanddraft.dto.DemandDraftProductInquiryDTO;
import com.lbt.icon.demanddraft.domain.demanddraft.dto.QueryDemandDraftProductDTO;
import com.lbt.icon.demanddraft.domain.demanddraft.dto.UpdateDemandDraftProductDTO;
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
import java.util.List;
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
    private final DemandDraftProductChargesService demandDraftProductChargesService;
    private final DemandDraftProductInstrService demandDraftProductInstrService;
    private final DemandDraftProductTranCodeLimitService demandDraftProductTranCodeLimitService;

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

            for (DemandDraftProductChargesDTO charge:dto.getDemandDraftProductCharges()) {
                charge.setProductCode(productCode);
                demandDraftProductChargesService.create(charge);
            }

            for (DemandDraftProductInstrDTO instr: dto.getDemandDraftProductInstruments()) {
                instr.setProductCode(productCode);
                demandDraftProductInstrService.create(instr);
            }

            for (DemandDraftProductTranCodeLimitDTO tranCodeLim: dto.getDemandDraftProductTranCodeLimits()) {
                tranCodeLim.setProductCode(productCode);
                demandDraftProductTranCodeLimitService.create(tranCodeLim);
            }

            DemandDraftProduct demandDraftProduct = modelMapper.map(dto.getDemandDraftProduct(),DemandDraftProduct.class);
            queryDemandDraftProductDTO = new QueryDemandDraftProductDTO();
            queryDemandDraftProductDTO.setBankProductMasterDTO(bpm);
            modelMapper.map(demandDraftProductRepository.create(demandDraftProduct),queryDemandDraftProductDTO);
        return  queryDemandDraftProductDTO;

    }

    @Override
    public DemandDraftProductInquiryDTO inquireByProductCode(String productCode) throws IconException {
        DemandDraftProductInquiryDTO demandDraftProductInquiryDTO = new DemandDraftProductInquiryDTO();
        DemandDraftProduct demandDraftProduct = demandDraftProductRepository.findByProductCode(productCode).orElseThrow(() ->
                new EntityNotFoundException(String.format("Demand Draft Product %s Not found",productCode)));

        List<QueryDemandDraftProductChargesDTO> charges = demandDraftProductChargesService.findByProductCode(productCode);
        List<QueryDemandDraftProductInstrDTO> instruments = demandDraftProductInstrService.findByProductCode(productCode);
        List<QueryDemandDraftProductTranCodeLimitDTO> tranCodeLimits = demandDraftProductTranCodeLimitService.findByProductCode(productCode);

        demandDraftProductInquiryDTO.setDemandDraftProductCharges(charges);
        demandDraftProductInquiryDTO.setDemandDraftProductInstruments(instruments);
        demandDraftProductInquiryDTO.setDemandDraftProductTranCodeLimits(tranCodeLimits);
        demandDraftProductInquiryDTO.setDemandDraftProduct(modelMapper.map(demandDraftProduct,QueryDemandDraftProductDTO.class));
        return demandDraftProductInquiryDTO;
    }

    @Override
    public QueryDemandDraftProductDTO update(String productCode, UpdateDemandDraftProductDTO dto) throws IconException{
        demandDraftProductValidator.validateFields(dto);
        DemandDraftProduct demandDraftProduct = demandDraftProductRepository.findByProductCode(productCode).orElseThrow(() ->
                new EntityNotFoundException(String.format("Demand DraftProduct %s Not found",productCode)));
        demandDraftProduct = PatchMapper.of(() -> dto).map(demandDraftProduct).get();
        demandDraftProduct=demandDraftProductRepository.update(demandDraftProduct);

        BankProductMasterDTO bankProductMasterDTO = bankProductMasterService.updateBasicDetails(dto.getUpdateBankProductMasterDTO());

        QueryDemandDraftProductDTO demandDraftProductDTO = new QueryDemandDraftProductDTO();
        demandDraftProductDTO.setId(demandDraftProduct.getId());
        modelMapper.map(demandDraftProduct, demandDraftProductDTO);
        modelMapper.map(bankProductMasterDTO, demandDraftProductDTO.getBankProductMasterDTO());
        return demandDraftProductDTO;
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
}
