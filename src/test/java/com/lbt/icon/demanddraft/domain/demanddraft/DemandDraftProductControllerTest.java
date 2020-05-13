package com.lbt.icon.demanddraft.domain.demanddraft;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lbt.icon.bankcommons.domain.common.currencyrate.CurrencyRateService;
import com.lbt.icon.bankcommons.domain.company.bankbranch.BankBranchRepo;
import com.lbt.icon.bankcommons.domain.company.financialinstitution.FinancialInstitutionService;
import com.lbt.icon.bankcommons.domain.globalparams.globalcode.GlobalCodeService;
import com.lbt.icon.bankcommons.domain.nextnumbergenerator.NextNumberGeneratorService;
import com.lbt.icon.bankproduct.domain.branch.BankProductBranchRepo;
import com.lbt.icon.bankproduct.domain.master.BankProductMaster;
import com.lbt.icon.bankproduct.domain.master.BankProductMasterRepo;
import com.lbt.icon.bankproduct.domain.master.BankProductMasterService;
import com.lbt.icon.bankproduct.domain.master.BankProductMasterValidator;
import com.lbt.icon.bankproduct.domain.master.dto.BankProductMasterDTO;
import com.lbt.icon.bankproduct.domain.master.search.BankProductContextSearch;
import com.lbt.icon.bankproduct.domain.subgl.BankProductGLRepo;
import com.lbt.icon.core.util.DatasourceUtil;
import com.lbt.icon.demanddraft.domain.demanddraftproductcharges.DemandDraftProductCharges;
import com.lbt.icon.demanddraft.domain.demanddraftproductcharges.DemandDraftProductChargesRepository;
import com.lbt.icon.demanddraft.domain.demanddraftproductcharges.DemandDraftProductChargesService;
import com.lbt.icon.demanddraft.domain.demanddraftproductcharges.DemandDraftProductChargesValidator;
import com.lbt.icon.demanddraft.domain.demanddraftproductinstr.DemandDraftProductInstr;
import com.lbt.icon.demanddraft.domain.demanddraftproductinstr.DemandDraftProductInstrRepository;
import com.lbt.icon.demanddraft.domain.demanddraftproductinstr.DemandDraftProductInstrServiceImpl;
import com.lbt.icon.demanddraft.domain.demanddraftproductinstr.DemandDraftProductInstrValidator;
import com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit.DemandDraftProductTranCodeLimit;
import com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit.DemandDraftProductTranCodeLimitRepository;
import com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit.DemandDraftProductTranCodeLimitServiceImpl;
import com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit.DemandDraftProductTranCodeLimitValidator;
import com.lbt.icon.demanddraft.domain.util.EntityManagerUtil;
import com.lbt.icon.demanddraft.type.InstrumentTransactionType;
import com.lbt.icon.excd.domain.exceptiondefinition.ExceptionDefinitionService;

import com.lbt.icon.ledger.setup.glcodes.subcategory.GlSubCategoryService;
import com.lbt.icon.sec.security.domain.activesession.ActiveSessionService;
import com.lbt.icon.sec.security.util.SecurityUtil;
import lombok.val;
import org.apache.commons.io.IOUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.InputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = DemandDraftProductController.class)
@ExtendWith(SpringExtension.class)
public class DemandDraftProductControllerTest {


    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private DemandDraftProductRepository demandDraftProductRepository;

    @SpyBean
    DemandDraftProductServiceImpl demandDraftProductService;

    @MockBean
    SecurityUtil securityUtil;

    @MockBean
    TokenStore tokenStore;

    @MockBean
    ActiveSessionService activeSessionService;

    @SpyBean
    private EntityManagerUtil entityManagerUtil;

    @MockBean
    private DatasourceUtil datasourceUtil;

    @MockBean
    private  BankBranchRepo bankBranchRepo;
    @MockBean
    private  BankProductBranchRepo bankProductBranchRepo;
    @MockBean
    private  BankProductGLRepo bankProductGLRepo;
    @MockBean
    private  BankProductMasterRepo bankProductMasterRepo;
    @MockBean
    private  BankProductMasterService bankProductMasterService;
    @MockBean
    private  DemandDraftProductChargesService demandDraftProductChargesService;
    @SpyBean
    private DemandDraftProductInstrServiceImpl demandDraftProductInstrService;

    @SpyBean
    private DemandDraftProductTranCodeLimitServiceImpl demandDraftProductTranCodeLimitService;

    @SpyBean
    private  DemandDraftProductValidator demandDraftProductValidator;
    @MockBean
    private GlSubCategoryService gLSubCategoryService;
    @MockBean
    private  GlobalCodeService globalCodeService;
    @SpyBean
    private  ModelMapper modelMapper;
    @MockBean
    private ExceptionDefinitionService exceptionDefinitionService;

    @MockBean
    private BankProductContextSearch bankProductContextSearch;

    @MockBean
    private   CurrencyRateService currencyRateService;

    @MockBean
    private  FinancialInstitutionService financialInstitutionService;

    @MockBean
    private  BankProductMasterValidator bankProductMasterValidator;

    @MockBean
    private  NextNumberGeneratorService nextNumberGeneratorService;

    @MockBean
    private DemandDraftProductInstrRepository demandDraftProductInstrRepository;

    @MockBean
    private DemandDraftProductChargesRepository demandDraftProductChargesRepository;

    @MockBean
    private DemandDraftProductTranCodeLimitRepository demandDraftProductTranCodeLimitRepository;

    @SpyBean
    private DemandDraftProductInstrValidator demandDraftProductInstrValidator;

    @SpyBean
    private DemandDraftProductChargesValidator demandDraftProductChargesValidator;

    @SpyBean
    private DemandDraftProductTranCodeLimitValidator demandDraftProductTranCodeLimitValidator;


    private static DemandDraftProduct demandDraftProduct;


    @BeforeEach
    void setup() throws Exception {

        demandDraftProduct = DemandDraftProduct.builder().productCode("DD001").build();

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    }

    @Test
    @WithMockUser
    public void test_findByProductCode() throws Exception {

        val product = BankProductMasterDTO.builder().productCode("DD001").build();
        Mockito.when(bankProductMasterService.findByDemandDraftProductCode(Mockito.any())).thenReturn(product);
        Mockito.when(demandDraftProductRepository.findByProductCode(Mockito.any())).thenReturn(java.util.Optional.ofNullable(demandDraftProduct));

        mockMvc.perform(get("/v1/demanddrafts/DD001"))
                .andDo(print())
                .andExpect(jsonPath("$.response").exists())
                .andExpect(jsonPath("$.response.productCode", Matchers.is("DD001")))
                .andExpect(status().isOk());

        Mockito.verify(bankProductMasterService,Mockito.atLeastOnce()).findByDemandDraftProductCode(Mockito.any());
        Mockito.verify(demandDraftProductRepository,Mockito.times(1)).findByProductCode(Mockito.any());
    }


    @Test
    @WithMockUser
    public void test_createDemandDraftProduct() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        InputStream inJson2 = DemandDraftProductControllerTest.class.getResourceAsStream("/test_response.json");
        DemandDraftProduct demandDraftProduct1 = objectMapper.readValue(inJson2, DemandDraftProduct.class);

        InputStream inJson3 = DemandDraftProductControllerTest.class.getResourceAsStream("/test_bp_response.json");
        BankProductMasterDTO bankProductMaster1 = objectMapper.readValue(inJson3, BankProductMasterDTO.class);



        InputStream inJson = DemandDraftProductControllerTest.class.getResourceAsStream("/test.json");
        byte[] payload = IOUtils.toByteArray(inJson);
       // val bankProductMaster = Mockito.mock(BankProductMasterDTO.class);

        val demandDraftProductInstr = DemandDraftProductInstr.builder().instrCode("001").productCode("DD001").instrTranType(InstrumentTransactionType.CREDIT).build();
        val demandDraftProductCharges= DemandDraftProductCharges.builder().productCode("DD001").chargeCode("DD001").build();
        val draftProductTranCodeLimit= DemandDraftProductTranCodeLimit.builder().productCode("DD001").build();
        //val demandDraftProduct= DemandDraftProduct.builder().productCode("DD001").build();


       // Mockito.when(bankProductMaster.getProductCode()).thenReturn("DD001");
        Mockito.when(bankProductMasterService.create(Mockito.any())).thenReturn(bankProductMaster1);
        Mockito.when(demandDraftProductInstrRepository.create(Mockito.any(DemandDraftProductInstr.class))).thenReturn(demandDraftProductInstr);
        Mockito.when(demandDraftProductChargesRepository.create(Mockito.any(DemandDraftProductCharges.class))).thenReturn(demandDraftProductCharges);
        Mockito.when(demandDraftProductTranCodeLimitRepository.create(Mockito.any(DemandDraftProductTranCodeLimit.class))).thenReturn(draftProductTranCodeLimit);
        Mockito.when(demandDraftProductRepository.create(Mockito.any(DemandDraftProduct.class))).thenReturn(demandDraftProduct1);
        mockMvc.perform(post("/v1/demanddrafts").contentType(MediaType.APPLICATION_JSON_UTF8).content(payload))
                .andDo(print())
                .andExpect(status().isCreated());

    }



}
