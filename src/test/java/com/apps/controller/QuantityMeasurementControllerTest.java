package com.apps.controller;

import com.apps.dto.QuantityDTO;
import com.apps.dto.QuantityInputDTO;
import com.apps.dto.QuantityMeasurementDTO;
import com.apps.service.IQuantityMeasurementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuantityMeasurementController.class)
public class QuantityMeasurementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IQuantityMeasurementService service;

    // ===== Helper =====

    private QuantityInputDTO buildInput(double v1, String u1, String t1,
                                        double v2, String u2, String t2) {

        QuantityDTO q1 = new QuantityDTO();
        q1.setValue(v1);
        q1.setUnit(u1);
        q1.setMeasurementType(t1);

        QuantityDTO q2 = new QuantityDTO();
        q2.setValue(v2);
        q2.setUnit(u2);
        q2.setMeasurementType(t2);

        QuantityInputDTO input = new QuantityInputDTO();
        input.setThisQuantityDTO(q1);
        input.setThatQuantityDTO(q2);

        return input;
    }

    private QuantityMeasurementDTO buildResult(String operation, double value,
                                               String unit, String type) {

        QuantityMeasurementDTO dto = new QuantityMeasurementDTO();
        dto.setOperation(operation);
        dto.setResultValue(value);
        dto.setResultUnit(unit);
        dto.setResultMeasurementType(type);
        dto.setError(false);
        return dto;
    }

    // ===== COMPARE =====

    @Test
    @WithMockUser
    public void testCompare() throws Exception {

        QuantityInputDTO input = buildInput(1, "FEET", "LengthUnit",
                                           12, "INCHES", "LengthUnit");

        QuantityMeasurementDTO result = buildResult("compare", 0, null, null);
        result.setResultString("true");

        Mockito.when(service.compare(Mockito.any())).thenReturn(result);

        mockMvc.perform(post("/api/v1/quantities/compare")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk());
    }

    // ===== ADD =====

    @Test
    @WithMockUser
    public void testAdd() throws Exception {

        QuantityInputDTO input = buildInput(1, "FEET", "LengthUnit",
                                           12, "INCHES", "LengthUnit");

        QuantityMeasurementDTO result = buildResult("add", 2.0, "FEET", "LengthUnit");

        Mockito.when(service.add(Mockito.any())).thenReturn(result);

        mockMvc.perform(post("/api/v1/quantities/add")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk());
    }

    // ===== HISTORY =====

    @Test
    @WithMockUser
    public void testHistory() throws Exception {

        QuantityMeasurementDTO dto = buildResult("compare", 0, null, null);
        dto.setResultString("true");

        List<QuantityMeasurementDTO> list = Arrays.asList(dto);

        Mockito.when(service.getHistoryByOperation(Mockito.any()))
                .thenReturn(list);

        mockMvc.perform(get("/api/v1/quantities/history/operation/compare")
                .with(csrf()))
                .andExpect(status().isOk());
    }

    // ===== ERROR =====

    @Test
    @WithMockUser
    public void testErrorHistory() throws Exception {

        QuantityMeasurementDTO dto = new QuantityMeasurementDTO();
        dto.setError(true);
        dto.setErrorMessage("error");

        Mockito.when(service.getErrorHistory())
                .thenReturn(Arrays.asList(dto));

        mockMvc.perform(get("/api/v1/quantities/history/errored")
                .with(csrf()))
                .andExpect(status().isOk());
    }
}