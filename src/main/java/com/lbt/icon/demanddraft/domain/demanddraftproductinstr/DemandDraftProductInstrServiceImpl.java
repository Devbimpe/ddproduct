package com.lbt.icon.demanddraft.domain.demanddraftproductinstr;

import com.lbt.icon.core.exception.EntityNotFoundException;
import com.lbt.icon.core.exception.IconException;
import com.lbt.icon.demanddraft.domain.demanddraftproductinstr.dto.DemandDraftProductInstrDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproductinstr.dto.QueryDemandDraftProductInstrDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproductinstr.dto.UpdateDemandDraftProductInstrDTO;
import com.lbt.icon.functional.mapper.PatchMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @author devbimpe
 * @since 14/03/2019
 */
@RequiredArgsConstructor
@Service
public class DemandDraftProductInstrServiceImpl implements DemandDraftProductInstrService {

    private final DemandDraftProductInstrRepository demanddraftproductinstrRepository;
    private final DemandDraftProductInstrValidator demanddraftproductinstrValidator;
    private final ModelMapper modelMapper;

    @Override
    public QueryDemandDraftProductInstrDTO create(DemandDraftProductInstrDTO instr) {
        DemandDraftProductInstr productInstr = modelMapper.map(instr,DemandDraftProductInstr.class);
        return modelMapper.map(demanddraftproductinstrRepository.create(productInstr),QueryDemandDraftProductInstrDTO.class);
    }

    @Override
    public List<QueryDemandDraftProductInstrDTO> findByProductCode(String productCode) {
        List<QueryDemandDraftProductInstrDTO> instruments = new ArrayList<>();
        demanddraftproductinstrRepository.findByProductCode(productCode).forEach(demandDraftProductInstr ->
                instruments.add(modelMapper.map(demandDraftProductInstr,QueryDemandDraftProductInstrDTO.class)));
        return instruments;
    }

    @Override
    public UpdateDemandDraftProductInstrDTO update(UpdateDemandDraftProductInstrDTO dto,String productCode) throws IconException{
        UpdateDemandDraftProductInstrDTO response = new UpdateDemandDraftProductInstrDTO();
        response.setDemandDraftProductInstruments(updateInstrBatch(productCode,dto.getDemandDraftProductInstruments()));
        return response;
    }

    @Override
    public QueryDemandDraftProductInstrDTO findById(Long id) throws IconException{
        DemandDraftProductInstr entity = demanddraftproductinstrRepository.findById(id).orElseThrow(() ->
                new IconException(String.format("Entity with id, %d  NOT FOUND", id)));
        return (modelMapper.map(entity, QueryDemandDraftProductInstrDTO.class));
    }

    @Override
    public QueryDemandDraftProductInstrDTO updateOne(QueryDemandDraftProductInstrDTO dto) throws IconException{
        DemandDraftProductInstr found = demanddraftproductinstrRepository.findById(dto.getId()).orElseThrow(() ->
                new EntityNotFoundException(String.format("Entity with id, %d  NOT FOUND", dto.getId())));
        // validator.validateUpdate(id, dto);
        modelMapper.map(dto, found);
        return modelMapper.map(demanddraftproductinstrRepository.update(found),QueryDemandDraftProductInstrDTO.class);

    }

    @Override
    public List<QueryDemandDraftProductInstrDTO> updateInstrBatch(String productCode, List<QueryDemandDraftProductInstrDTO> demandDraftProductInstruments) throws IconException {
        List<QueryDemandDraftProductInstrDTO> instrDTOS = new ArrayList<>();
        for (QueryDemandDraftProductInstrDTO q: demandDraftProductInstruments) {
            if (q.getId() == null) {
                q.setProductCode(productCode);
                instrDTOS.add(modelMapper.map(demanddraftproductinstrRepository.create(modelMapper.map(q,DemandDraftProductInstr.class)),QueryDemandDraftProductInstrDTO.class));
            }
            else {
                QueryDemandDraftProductInstrDTO byId = findById(q.getId());
                byId = PatchMapper.of(() -> q).map(byId).get();
                instrDTOS.add(updateOne(byId));
            }
        }
        return instrDTOS;
    }
}
