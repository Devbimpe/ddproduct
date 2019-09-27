package com.lbt.icon.demanddraft.domain.demanddraftproductinstr;

import com.lbt.icon.demanddraft.domain.demanddraftproductinstr.dto.DemandDraftProductInstrDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproductinstr.dto.QueryDemandDraftProductInstrDTO;
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
}
