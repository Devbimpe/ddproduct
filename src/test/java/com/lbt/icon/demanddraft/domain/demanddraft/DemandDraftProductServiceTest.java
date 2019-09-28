package com.lbt.icon.demanddraft.domain.demanddraft;

import com.lbt.icon.core.exception.IconException;
import com.lbt.icon.demanddraft.domain.demanddraft.dto.CreateDemandDraftProductDTO;
import com.lbt.icon.demanddraft.domain.demanddraft.dto.DemandDraftProductDTO;
import com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit.DemandDraftProductTranCodeLimit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


/**
 * @author devbimpe
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DemandDraftProductServiceTest {





    @Mock
    private DemandDraftProductService demandDraftProductService;

    @Mock
    private DemandDraftProductRepository demandDraftProductRepository;


    @BeforeEach
    public void setUp(){
        ModelMapper modelMapper = new ModelMapper();
        DemandDraftProduct demandDraftProduct =new DemandDraftProduct();
        demandDraftProduct.toString();
        DemandDraftProductDTO demandDraftProductDTO = new DemandDraftProductDTO();
        demandDraftProductDTO.toString();
        CreateDemandDraftProductDTO c = new CreateDemandDraftProductDTO();
        c.toString();
        DemandDraftProductTranCodeLimit limit = new DemandDraftProductTranCodeLimit();
        limit.toString();
        when(demandDraftProductRepository.create(any())).thenReturn(demandDraftProduct);
        when(demandDraftProductRepository.update(any())).thenReturn(demandDraftProduct);
        when(demandDraftProductRepository.findByProductCode(any())).thenReturn(Optional.of(demandDraftProduct));
        when(demandDraftProductRepository.findByIdOrThrow(any())).thenReturn(demandDraftProduct);


    }


    @Test
    @Disabled
    public void testFindById() throws IconException {
        Assertions.assertNotNull(demandDraftProductService.inquireByProductCode("TEST"));
    }


}
