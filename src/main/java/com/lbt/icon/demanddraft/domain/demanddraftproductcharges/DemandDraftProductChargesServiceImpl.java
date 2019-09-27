package com.lbt.icon.demanddraft.domain.demanddraftproductcharges;

import com.lbt.icon.core.exception.FieldValidationException;
import com.lbt.icon.demanddraft.domain.demanddraftproductcharges.dto.DemandDraftProductChargesDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproductcharges.dto.QueryDemandDraftProductChargesDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


/**
 * @author devbimpe
 * @since 14/03/2019
 */
@RequiredArgsConstructor
@Service
public class DemandDraftProductChargesServiceImpl implements DemandDraftProductChargesService {

    private final DemandDraftProductChargesRepository demanddraftProductChargesRepository;
    private final DemandDraftProductChargesValidator demanddraftProductChargesValidator;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public QueryDemandDraftProductChargesDTO create(DemandDraftProductChargesDTO charge) throws FieldValidationException {
        DemandDraftProductCharges productCharges=modelMapper.map(charge,DemandDraftProductCharges.class);
        return modelMapper.map(demanddraftProductChargesRepository.create(productCharges),QueryDemandDraftProductChargesDTO.class);
    }

    @Override
    public List<QueryDemandDraftProductChargesDTO> findByProductCode(String productCode) {
        List<DemandDraftProductCharges> charges = demanddraftProductChargesRepository.findByProductCode(productCode);
        List<QueryDemandDraftProductChargesDTO> productCharges = new ArrayList<>();
        charges.forEach(c -> productCharges.add(modelMapper.map(c,QueryDemandDraftProductChargesDTO.class)));
        return productCharges;
    }
}
