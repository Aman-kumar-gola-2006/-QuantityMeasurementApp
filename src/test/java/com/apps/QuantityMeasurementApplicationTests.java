package com.apps;

import com.apps.dto.QuantityDTO;
import com.apps.dto.QuantityInputDTO;
import com.apps.dto.QuantityMeasurementDTO;
import com.apps.model.QuantityMeasurementEntity;
import com.apps.repository.QuantityMeasurementRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class QuantityMeasurementApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private QuantityMeasurementRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    private String baseUrl;
    private HttpHeaders headers;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/api/v1/quantities";
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        repository.deleteAll();
    }

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

    // ===== BASIC TEST =====

    @Test
    public void testSpringBootApplicationStarts() {
        assertNotNull(restTemplate);
        assertNotNull(repository);
    }

    // ===== COMPARE =====

    @Test
    public void testCompare() {

        QuantityInputDTO input = buildInput(1.0, "FEET", "LengthUnit",
                                           12.0, "INCHES", "LengthUnit");

        ResponseEntity<QuantityMeasurementDTO> response =
                restTemplate.postForEntity(baseUrl + "/compare", input, QuantityMeasurementDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("compare", response.getBody().getOperation());
    }

    // ===== ADD =====

    @Test
    public void testAdd() {

        QuantityInputDTO input = buildInput(1.0, "FEET", "LengthUnit",
                                           12.0, "INCHES", "LengthUnit");

        ResponseEntity<QuantityMeasurementDTO> response =
                restTemplate.postForEntity(baseUrl + "/add", input, QuantityMeasurementDTO.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("add", response.getBody().getOperation());
    }

    // ===== HISTORY =====

    @Test
    public void testHistory() {

        QuantityInputDTO input = buildInput(1.0, "FEET", "LengthUnit",
                                           12.0, "INCHES", "LengthUnit");

        restTemplate.postForEntity(baseUrl + "/compare", input, QuantityMeasurementDTO.class);

        ResponseEntity<List> response =
                restTemplate.getForEntity(baseUrl + "/history/operation/compare", List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // ===== DATABASE =====

    @Test
    public void testDatabase() {

        QuantityInputDTO input = buildInput(1.0, "FEET", "LengthUnit",
                                           12.0, "INCHES", "LengthUnit");

        restTemplate.postForEntity(baseUrl + "/add", input, QuantityMeasurementDTO.class);

        List<QuantityMeasurementEntity> list = repository.findAll();

        assertFalse(list.isEmpty());
    }
}