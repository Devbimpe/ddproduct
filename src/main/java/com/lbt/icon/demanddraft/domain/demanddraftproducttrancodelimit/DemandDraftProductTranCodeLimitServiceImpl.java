package com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit;

import com.lbt.icon.core.exception.EntityNotFoundException;
import com.lbt.icon.core.exception.IconException;
import com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit.dto.DemandDraftProductTranCodeLimitDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit.dto.QueryDemandDraftProductTranCodeLimitDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit.dto.UpdateDemandDraftProductTranCodeLimitDTO;
import com.lbt.icon.functional.mapper.PatchMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author devbimpe
 * @since 14/03/2019
 */
@RequiredArgsConstructor
@Service
public class DemandDraftProductTranCodeLimitServiceImpl implements DemandDraftProductTranCodeLimitService {

    private final DemandDraftProductTranCodeLimitRepository demandDraftProductTranCodeLimitRepository;
    private final DemandDraftProductTranCodeLimitValidator demandDraftProductTranCodeLimitValidator;
    private final ModelMapper modelMapper;

    @Override
    public QueryDemandDraftProductTranCodeLimitDTO create(DemandDraftProductTranCodeLimitDTO tranCodeLim) {
        DemandDraftProductTranCodeLimit tranCodeLimit = modelMapper.map(tranCodeLim,DemandDraftProductTranCodeLimit.class);
        return modelMapper.map(demandDraftProductTranCodeLimitRepository.create(tranCodeLimit),QueryDemandDraftProductTranCodeLimitDTO.class);
    }

    @Override
    public List<QueryDemandDraftProductTranCodeLimitDTO> findByProductCode(String productCode) {
        List<QueryDemandDraftProductTranCodeLimitDTO> instruments = new ArrayList<>();
        demandDraftProductTranCodeLimitRepository.findByProductCode(productCode).forEach(demandDraftProductTranCodeLimit ->
                instruments.add(modelMapper.map(demandDraftProductTranCodeLimit,QueryDemandDraftProductTranCodeLimitDTO.class)));
        return instruments;
    }

    @Override
    public UpdateDemandDraftProductTranCodeLimitDTO update(UpdateDemandDraftProductTranCodeLimitDTO dto, String productCode) throws IconException, EntityNotFoundException{
        List<QueryDemandDraftProductTranCodeLimitDTO> tranCodes = new ArrayList<>();
        for (QueryDemandDraftProductTranCodeLimitDTO q: dto.getDemandDraftProductTranCodeLimits()) {
            if (q.getId() == null) {
                q.setProductCode(productCode);
                tranCodes.add(modelMapper.map(demandDraftProductTranCodeLimitRepository.create(modelMapper.map(q,DemandDraftProductTranCodeLimit.class)),QueryDemandDraftProductTranCodeLimitDTO.class));
            }
            else {
                QueryDemandDraftProductTranCodeLimitDTO byId = findById(q.getId());
                byId = PatchMapper.of(() -> q).map(byId).get();
                tranCodes.add(updateOne(byId));
            }
        }
            UpdateDemandDraftProductTranCodeLimitDTO response = new UpdateDemandDraftProductTranCodeLimitDTO();
            response.setDemandDraftProductTranCodeLimits(tranCodes);
        return response;
    }


    @Override
    public QueryDemandDraftProductTranCodeLimitDTO updateOne(QueryDemandDraftProductTranCodeLimitDTO tranCodeLimit) throws EntityNotFoundException{
        DemandDraftProductTranCodeLimit found = demandDraftProductTranCodeLimitRepository.findById(tranCodeLimit.getId()).orElseThrow(() ->
                new EntityNotFoundException(String.format("Entity with id, %d  NOT FOUND", tranCodeLimit.getId())));
       // validator.validateUpdate(id, dto);
        modelMapper.map(tranCodeLimit, found);
        return modelMapper.map(demandDraftProductTranCodeLimitRepository.update(found),QueryDemandDraftProductTranCodeLimitDTO.class);

    }

    @Override
    public QueryDemandDraftProductTranCodeLimitDTO findById(Long id) throws IconException {
        DemandDraftProductTranCodeLimit entity = demandDraftProductTranCodeLimitRepository.findById(id).orElseThrow(() ->
                new IconException(String.format("Entity with id, %d  NOT FOUND", id)));
        return (modelMapper.map(entity, QueryDemandDraftProductTranCodeLimitDTO.class));

    }
}
