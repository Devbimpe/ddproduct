
package com.lbt.icon.demanddraft.domain.demanddraftproductinstr;


import com.lbt.icon.demanddraft.domain.demanddraftproductinstr.dto.DemandDraftProductInstrDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproductinstr.dto.QueryDemandDraftProductInstrDTO;

import java.util.List;

/**
 * @author devbimpe
 * @since 14/03/2019
 */
public interface DemandDraftProductInstrService {

    QueryDemandDraftProductInstrDTO create(DemandDraftProductInstrDTO instr);

    List<QueryDemandDraftProductInstrDTO> findByProductCode(String productCode);
}

