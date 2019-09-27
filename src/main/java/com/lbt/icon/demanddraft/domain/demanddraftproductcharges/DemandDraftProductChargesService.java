
package com.lbt.icon.demanddraft.domain.demanddraftproductcharges;


import com.lbt.icon.core.exception.FieldValidationException;
import com.lbt.icon.demanddraft.domain.demanddraftproductcharges.dto.DemandDraftProductChargesDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproductcharges.dto.QueryDemandDraftProductChargesDTO;

import java.util.List;

/**
 * @author devbimpe
 * @since 14/03/2019
 */
public interface DemandDraftProductChargesService {


    QueryDemandDraftProductChargesDTO create(DemandDraftProductChargesDTO charge) throws FieldValidationException;

    List<QueryDemandDraftProductChargesDTO> findByProductCode(String productCode);
}

