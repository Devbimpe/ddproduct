
package com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit;


import com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit.dto.DemandDraftProductTranCodeLimitDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit.dto.QueryDemandDraftProductTranCodeLimitDTO;

import java.util.List;

/**
 * @author devbimpe
 * @since 14/03/2019
 */
public interface DemandDraftProductTranCodeLimitService {

    QueryDemandDraftProductTranCodeLimitDTO create(DemandDraftProductTranCodeLimitDTO tranCodeLim);

    List<QueryDemandDraftProductTranCodeLimitDTO> findByProductCode(String productCode);
}

