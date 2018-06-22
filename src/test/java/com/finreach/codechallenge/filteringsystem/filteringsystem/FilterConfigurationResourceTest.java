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
    private static final String LOOKUP_IP = "192.9.200.2";
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
    public void getBlackList() throws Exception {
        restUserMockMvc.perform((get("/api/filterConfiguration"))
        ).andExpect(status().isOk());
    }

    @Test
    public void isIPInBlackList() throws Exception {
        restUserMockMvc.perform((get("/api/filterConfiguration/" + LOOKUP_IP)
        ))
                .andExpect(status().isOk())
                .andExpect(content().string("true"))
        ;
    }

    @Test
    public void removeFromBlackList() throws Exception {
        FilterConfigurationDTO filterConfigurationDTO=new FilterConfigurationDTO("192.9.200.1","192.9.200.15");
        restUserMockMvc.perform((delete("/api/filterConfiguration").contentType(MediaType.APPLICATION_JSON) .content(TestUtil.convertObjectToJsonBytes(filterConfigurationDTO))
        )).andExpect(status().isOk());
    }
}
