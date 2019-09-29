
package com.lbt.icon.demanddraft.domain.demanddraftproductinstr;


import com.lbt.icon.core.exception.IconException;
import com.lbt.icon.demanddraft.domain.demanddraftproductinstr.dto.DemandDraftProductInstrDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproductinstr.dto.QueryDemandDraftProductInstrDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproductinstr.dto.UpdateDemandDraftProductInstrDTO;

import java.util.List;

/**
 * @author devbimpe
 * @since 14/03/2019
 */
public interface DemandDraftProductInstrService {

    QueryDemandDraftProductInstrDTO create(DemandDraftProductInstrDTO instr);

    List<QueryDemandDraftProductInstrDTO> findByProductCode(String productCode);

    UpdateDemandDraftProductInstrDTO update(UpdateDemandDraftProductInstrDTO dto, String productCode) throws IconException;
    QueryDemandDraftProductInstrDTO updateOne(QueryDemandDraftProductInstrDTO dto) throws IconException;

    QueryDemandDraftProductInstrDTO findById(Long id) throws IconException;
}

