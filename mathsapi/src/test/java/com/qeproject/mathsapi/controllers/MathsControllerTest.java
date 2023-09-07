package com.qeproject.mathsapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qeproject.mathsapi.models.NumbersObject;
import com.qeproject.mathsapi.models.OperatorEnum;
import com.qeproject.mathsapi.services.MathsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

@WebMvcTest(MathsController.class)
class MathsControllerTest
{
    @Autowired
    MockMvc mockMvc;
    @MockBean
    MathsService mathsService;

    @Autowired
    ObjectMapper mapper;

    @Test
    void postIntegers_isCreated() throws Exception
    {
        NumbersObject numberRepo = new NumbersObject(3, 5);
        String json = mapper.writeValueAsString(numberRepo);

        mockMvc.perform(MockMvcRequestBuilders.post("/mathservice/integers")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(json)
                                              .accept(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isCreated())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void postIntegers_isBadRequest() throws Exception
    {
        // Expect test to fail because number2 is not a number
        String badJson = """
            { "number1": "1", "number2" : "two" } 
            """;
        mockMvc.perform(MockMvcRequestBuilders.post("/mathservice/integers")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(badJson)
                                              .accept(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isBadRequest())
               .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void postOperator_isCreated_withResult() throws Exception
    {
        final OperatorEnum activeOperator = OperatorEnum.ADD;

        String json = mapper.writeValueAsString(activeOperator);

        //Stubbed service method which returns 2.00 when hit by the controller
        Mockito.when(mathsService.storeOperator(activeOperator))
               .thenReturn(2.00);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/mathservice/operator")
                                                                 .contentType(MediaType.APPLICATION_JSON)
                                                                 .content(json)
                                                                 .accept(MediaType.APPLICATION_JSON))
                                  .andExpect(MockMvcResultMatchers.status().isCreated())
                                  .andDo(MockMvcResultHandlers.print())
                                  .andReturn();
        assertEquals(2.0, Double.parseDouble(result.getResponse().getContentAsString()));
    }

    @Test
    void postOperator_isBadRequest() throws Exception
    {
        final OperatorEnum activeOperator = OperatorEnum.ADD;

        String incorrectJson = "\"ADD_ME\"";

        mockMvc.perform(MockMvcRequestBuilders.post("/mathservice/operator")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(incorrectJson)
                                              .accept(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isBadRequest())
               .andDo(MockMvcResultHandlers.print());
    }
}