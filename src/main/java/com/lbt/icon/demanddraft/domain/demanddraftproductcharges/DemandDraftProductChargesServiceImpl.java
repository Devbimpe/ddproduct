package com.lbt.icon.demanddraft.domain.demanddraftproductcharges;

import com.lbt.icon.core.exception.EntityNotFoundException;
import com.lbt.icon.core.exception.FieldValidationException;
import com.lbt.icon.core.exception.IconException;
import com.lbt.icon.demanddraft.domain.demanddraftproductcharges.dto.DemandDraftProductChargesDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproductcharges.dto.QueryDemandDraftProductChargesDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproductcharges.dto.UpdateDemandDraftProductChargesDTO;
import com.lbt.icon.functional.mapper.PatchMapper;
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

    @Override
    public UpdateDemandDraftProductChargesDTO update(UpdateDemandDraftProductChargesDTO dto, String productCode) throws IconException {
        UpdateDemandDraftProductChargesDTO response = new UpdateDemandDraftProductChargesDTO();
        response.setDemandDraftProductCharges(updateChargeBatch(productCode, dto.getDemandDraftProductCharges()));
        return response;
    }

    @Override
    public List<QueryDemandDraftProductChargesDTO> updateChargeBatch(String productCode, List<QueryDemandDraftProductChargesDTO> chargeDTOS) throws IconException {
        List<QueryDemandDraftProductChargesDTO> dtos = new ArrayList<>();
        for (QueryDemandDraftProductChargesDTO q: chargeDTOS){
            if (q.getId() == null) {
                q.setProductCode(productCode);
                dtos.add(modelMapper.map(demanddraftProductChargesRepository.create(modelMapper.map(q, DemandDraftProductCharges.class)),QueryDemandDraftProductChargesDTO.class));
            }
            else {
                QueryDemandDraftProductChargesDTO byId = findById(q.getId());
                byId = PatchMapper.of(() -> q).map(byId).get();
                dtos.add(updateOne(byId));
            }
        }
        return dtos;
    }

    @Override
    public QueryDemandDraftProductChargesDTO updateOne(QueryDemandDraftProductChargesDTO dto) throws IconException {
        DemandDraftProductCharges found = demanddraftProductChargesRepository.findById(dto.getId()).orElseThrow(() ->
                new EntityNotFoundException(String.format("Entity with id, %d  NOT FOUND", dto.getId())));
        // validator.validateUpdate(id, dto);
        modelMapper.map(dto, found);
        return modelMapper.map(demanddraftProductChargesRepository.update(found),QueryDemandDraftProductChargesDTO.class);

    }

    @Override
    public QueryDemandDraftProductChargesDTO findById(Long id) throws IconException {
        DemandDraftProductCharges entity = demanddraftProductChargesRepository.findById(id).orElseThrow(() ->
                new IconException(String.format("Entity with id, %d  NOT FOUND", id)));
        return (modelMapper.map(entity, QueryDemandDraftProductChargesDTO.class));
    }
}
