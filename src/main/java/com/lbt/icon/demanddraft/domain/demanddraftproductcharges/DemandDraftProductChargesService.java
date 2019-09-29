
package com.lbt.icon.demanddraft.domain.demanddraftproductcharges;


import com.lbt.icon.core.exception.FieldValidationException;
import com.lbt.icon.core.exception.IconException;
import com.lbt.icon.demanddraft.domain.demanddraftproductcharges.dto.DemandDraftProductChargesDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproductcharges.dto.QueryDemandDraftProductChargesDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproductcharges.dto.UpdateDemandDraftProductChargesDTO;

import java.util.List;

/**
 * @author devbimpe
 * @since 14/03/2019
 */
public interface DemandDraftProductChargesService {


    QueryDemandDraftProductChargesDTO create(DemandDraftProductChargesDTO charge) throws FieldValidationException;

    List<QueryDemandDraftProductChargesDTO> findByProductCode(String productCode);

    UpdateDemandDraftProductChargesDTO update(UpdateDemandDraftProductChargesDTO dto, String productCode) throws IconException;
    QueryDemandDraftProductChargesDTO updateOne(QueryDemandDraftProductChargesDTO dto) throws IconException;

    QueryDemandDraftProductChargesDTO findById(Long id) throws IconException;
}

