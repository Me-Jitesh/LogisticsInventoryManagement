package com.ishopee.logisticsinventorymanagement;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
class LogisticsInventoryManagementApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("TEST MUS # SAVE OPERATION")
    @Order(1)
    public void testMusSave() throws Exception {
        // 1 : Create One Http Request Using Mock
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/api/mus/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"musType\":\"PAKING\",\"musModel\":\"TEST\",\"musDesc\":\"SAMPLE DATA\"}");
        // 2 : Execute Request and get result using mockMvc(Envinonment)
        MvcResult result = mockMvc.perform(request).andReturn();
        // 3 : Read Response from result
        MockHttpServletResponse response = result.getResponse();
        // 4 : Validate/Assert response using Junit API
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        if (!response.getContentAsString().contains("Created")) {
            fail("MUS is Not Created");
        }
    }
}
