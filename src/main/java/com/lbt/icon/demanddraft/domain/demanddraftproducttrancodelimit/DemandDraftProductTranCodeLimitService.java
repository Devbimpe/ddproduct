
package com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit;


import com.lbt.icon.core.exception.EntityNotFoundException;
import com.lbt.icon.core.exception.IconException;
import com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit.dto.DemandDraftProductTranCodeLimitDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit.dto.QueryDemandDraftProductTranCodeLimitDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit.dto.UpdateDemandDraftProductTranCodeLimitDTO;

import java.util.List;

/**
 * @author devbimpe
 * @since 14/03/2019
 */
public interface DemandDraftProductTranCodeLimitService {

    QueryDemandDraftProductTranCodeLimitDTO create(DemandDraftProductTranCodeLimitDTO tranCodeLim);

    List<QueryDemandDraftProductTranCodeLimitDTO> findByProductCode(String productCode);

    UpdateDemandDraftProductTranCodeLimitDTO update(UpdateDemandDraftProductTranCodeLimitDTO dto, String productCode) throws IconException, EntityNotFoundException;

    QueryDemandDraftProductTranCodeLimitDTO updateOne(QueryDemandDraftProductTranCodeLimitDTO tranCodeLimit) throws EntityNotFoundException;

    QueryDemandDraftProductTranCodeLimitDTO findById(Long id) throws IconException;
}

