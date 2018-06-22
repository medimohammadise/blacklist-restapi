package com.finreach.codechallenge.filteringsystem.filteringsystem;

import com.finreach.codechallenge.filteringsystem.filteringsystem.dto.FilterConfigurationDTO;
import com.finreach.codechallenge.filteringsystem.filteringsystem.rest.FilterConfigurationResource;
import com.finreach.codechallenge.filteringsystem.filteringsystem.service.FilterConfigurationService;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FilteringsystemApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FilterConfigurationResourceTest {
    private static final String EXTSING_LOOKUP_IP_INBLACKLIST = "192.9.200.2";
    private static final String NOT_EXTSING_LOOKUP_IP_INBLACKLIST = "215.4.4.1";
    private static final String INVALID_LOOKUP_IP_INBLACKLIST = "215.4333.2.3";
    private MockMvc restUserMockMvc;

    @Autowired
    FilterConfigurationService filterConfigurationService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FilterConfigurationResource filterConfigurationResource = new FilterConfigurationResource(filterConfigurationService);
        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(filterConfigurationResource)
                .build();
    }


    @Test
    public void createFilterConfiguration() throws Exception {
        FilterConfigurationDTO filterConfigurationDTO=new FilterConfigurationDTO("192.9.200.1","192.9.200.15");
        restUserMockMvc.perform((put("/api/filterConfiguration").contentType(MediaType.APPLICATION_JSON) .content(TestUtil.convertObjectToJsonBytes(filterConfigurationDTO))
        ))

                .andExpect(status().isOk())

        ;
    }

    @Test
    public void createFilterConfigurationWithInvalidIP() throws Exception {
        FilterConfigurationDTO filterConfigurationDTO=new FilterConfigurationDTO("1","192.9.200.15");
        restUserMockMvc.perform((put("/api/filterConfiguration").contentType(MediaType.APPLICATION_JSON) .content(TestUtil.convertObjectToJsonBytes(filterConfigurationDTO))
        )).andExpect(status().isBadRequest());
    }
    @Test
    public void getBlackList() throws Exception {
        restUserMockMvc.perform((get("/api/filterConfiguration"))
        ).andExpect(status().isOk());
    }

    @Test
    public void isIPInBlackListMatch() throws Exception {
        restUserMockMvc.perform((get("/api/filterConfiguration/" + EXTSING_LOOKUP_IP_INBLACKLIST)
        ))
                .andExpect(status().isOk())
                .andExpect(content().string("true"))
        ;
    }

    @Test
    public void isIPInBlackListNotMacth() throws Exception {
        restUserMockMvc.perform((get("/api/filterConfiguration/" + NOT_EXTSING_LOOKUP_IP_INBLACKLIST)
        ))
                .andExpect(status().isOk())
                .andExpect(content().string("false"))
        ;
    }

    @Test
    public void isIPInBlackListInvalidIP() throws Exception {
        restUserMockMvc.perform((get("/api/filterConfiguration/" + INVALID_LOOKUP_IP_INBLACKLIST)
        )).andExpect(status().isInternalServerError());
    }

    @Test
    public void removeFromBlackList() throws Exception {
        FilterConfigurationDTO filterConfigurationDTO=new FilterConfigurationDTO("192.9.200.1","192.9.200.15");
        restUserMockMvc.perform((delete("/api/filterConfiguration").contentType(MediaType.APPLICATION_JSON) .content(TestUtil.convertObjectToJsonBytes(filterConfigurationDTO))
        )).andExpect(status().isOk());
    }
}
