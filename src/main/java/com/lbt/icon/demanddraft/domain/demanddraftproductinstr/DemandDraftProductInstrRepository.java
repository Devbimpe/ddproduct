package com.lbt.icon.demanddraft.domain.demanddraftproductinstr;

import com.lbt.icon.core.domain.repo.GenericRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author devbimpe
 * @since 14/03/2019
 */
@Repository
public interface DemandDraftProductInstrRepository extends GenericRepository<DemandDraftProductInstr, Long> {

    List<DemandDraftProductInstr> findByProductCode(String productCode);
}

