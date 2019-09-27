package com.lbt.icon.demanddraft.domain.demanddraftproductcharges;

import com.lbt.icon.core.domain.repo.GenericRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author devbimpe
 * @since 14/03/2019
 */
@Repository
public interface DemandDraftProductChargesRepository extends GenericRepository<DemandDraftProductCharges, Long> {

    List<DemandDraftProductCharges> findByProductCode(String productCode);
}

