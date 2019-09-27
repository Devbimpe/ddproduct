package com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit;

import com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit.dto.DemandDraftProductTranCodeLimitDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit.dto.QueryDemandDraftProductTranCodeLimitDTO;
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
}
