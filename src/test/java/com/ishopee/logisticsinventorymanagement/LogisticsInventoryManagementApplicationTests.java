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
    @DisplayName("TEST MUS #SAVE OPERATION : SUCCESS CASE")
    @Order(1)
    public void testMusSaveSucess() throws Exception {
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

    @Test
    @DisplayName("TEST MUS #DELETE OPERATION : SUCCESS CASE")
    @Order(2)
//    @Disabled
    public void testMusDeleteSuccess() throws Exception {
        // 1 : Create One Http Request Using Mock
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/api/mus/delete/{id}", 200202);
        // 2 : Execute Request and get result using mockMvc(Envinonment)
        MvcResult result = mockMvc.perform(request).andReturn();
        // 3 : Read Response from result
        MockHttpServletResponse response = result.getResponse();
        // 4 : Validate/Assert response using Junit API
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        if (!response.getContentAsString().contains("Deleted")) {
            fail("MUS is Not Deleted");
        }
    }

    @Test
    @DisplayName("TEST MUS #DELETE OPERATION : FAILED CASE")
    @Order(3)
    public void testMusDeleteFailed() throws Exception {
        // 1 : Create One Http Request Using Mock
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/api/mus/delete/{id}", 200152);
        // 2 : Execute Request and get result using mockMvc(Envinonment)
        MvcResult result = mockMvc.perform(request).andReturn();
        // 3 : Read Response from result
        MockHttpServletResponse response = result.getResponse();
        // 4 : Validate/Assert response using Junit API
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        if (response.getContentAsString().contains("Deleted")) {
            fail("MUS Deleted");
        }
    }

    @Test
    @DisplayName("TEST SHIPMENT TYPE  #SAVE OPERATION : SUCCESS CASE")
    public void testShipmentTypeSaveSucess() throws Exception {
        // 1 : Create One Http Request Using Mock
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/api/st/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"shipMode\":\"Truck\",\"shipCode\":\"TATA\",\"enableShip\": \"Yes\",\"shipGrad\": \"B\",\"shipDesc\": \"Heavey transition\"}");
        // 2 : Execute Request and get result using mockMvc(Envinonment)
        MvcResult result = mockMvc.perform(request).andReturn();
        // 3 : Read Response from result
        MockHttpServletResponse response = result.getResponse();
        // 4 : Validate/Assert response using Junit API
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        if (!response.getContentAsString().contains("Created")) {
            fail("Failed To Create ShipmentType");
        }
    }

    @Test
    @DisplayName("TEST  SHIPMENT TYPE  #DELETE OPERATION : SUCCESS CASE")
    public void testShipmentTypeDeleteSuccess() throws Exception {
        // 1 : Create One Http Request Using Mock
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/api/st/delete/{id}", 199952);
        // 2 : Execute Request and get result using mockMvc(Envinonment)
        MvcResult result = mockMvc.perform(request).andReturn();
        // 3 : Read Response from result
        MockHttpServletResponse response = result.getResponse();
        // 4 : Validate/Assert response using Junit API
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        if (!response.getContentAsString().contains("Deleted")) {
            fail("Failed To Delete ShipmentType");
        }
    }

    @Test
    @DisplayName("TEST  SHIPMENT TYPE  #DELETE OPERATION : FAILED CASE")
    public void testShipmentTypeDeleteFailed() throws Exception {
        // 1 : Create One Http Request Using Mock
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/api/st/delete/{id}", 199952);
        // 2 : Execute Request and get result using mockMvc(Envinonment)
        MvcResult result = mockMvc.perform(request).andReturn();
        // 3 : Read Response from result
        MockHttpServletResponse response = result.getResponse();
        // 4 : Validate/Assert response using Junit API
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
        if (response.getContentAsString().contains("Deleted")) {
            fail("ShipmentType Deleted");
        }
    }
}
