package com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit;

import com.lbt.icon.core.domain.repo.GenericRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author devbimpe
 * @since 14/03/2019
 */
@Repository
public interface DemandDraftProductTranCodeLimitRepository extends GenericRepository<DemandDraftProductTranCodeLimit, Long> {

    List<DemandDraftProductTranCodeLimit> findByProductCode(String productCode);
}

