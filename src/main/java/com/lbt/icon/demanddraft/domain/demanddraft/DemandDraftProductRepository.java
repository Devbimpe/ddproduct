package com.lbt.icon.demanddraft.domain.demanddraft;

import com.lbt.icon.core.domain.repo.GenericRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author devbimpe
 * @since 14/03/2019
 */
@Repository
public interface DemandDraftProductRepository extends GenericRepository<DemandDraftProduct, Long> {
    Boolean existsByProductCode(String productCode);

    Optional<DemandDraftProduct> findByProductCode(String productCode);

//    DemandDraftProduct findByProductCode(String productCode);
}

