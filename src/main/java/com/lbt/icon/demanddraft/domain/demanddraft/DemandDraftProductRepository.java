package com.lbt.icon.demanddraft.domain.demanddraft;

import com.lbt.icon.core.domain.repo.GenericRepository;
import com.lbt.icon.demanddraft.domain.demanddraft.dto.DemandDraftProductCurrencyDto;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author devbimpe
 * @since 14/03/2019
 */
@Repository
public interface DemandDraftProductRepository extends GenericRepository<DemandDraftProduct, Long> {
    Boolean existsByProductCode(String productCode);


    Optional<DemandDraftProduct> findByProductCode(String productCode);

    List<DemandDraftProduct> findByProductCodeContaining(String productCode);

//    DemandDraftProduct findByProductCode(String productCode);
    
    @Query("select distinct new com.lbt.icon.demanddraft.domain.demanddraft.dto.DemandDraftProductCurrencyDto(b.currencyCode,b.currencyCode) from BankProductCurrency b join BankProductMaster m on b.productCode = m.productCode where lower(b.productCode)=lower(:productCode)")
    List<DemandDraftProductCurrencyDto> findCurrencyDtoForProduct(@Param("productCode")String productCode);
}

