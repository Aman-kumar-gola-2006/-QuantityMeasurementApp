package com.qma_microservices.quantity_service.controller;

import com.qma_microservices.quantity_service.dto.QuantityInputDTO;
import com.qma_microservices.quantity_service.dto.QuantityMeasurementDTO;
import com.qma_microservices.quantity_service.service.IQuantityMeasurementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quantities")
@Tag(name = "Quantity Measurements", description = "REST API for quantity measurement operations")
public class QuantityMeasurementController {
    
    private static final Logger log = LoggerFactory.getLogger(QuantityMeasurementController.class);

    private final IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        this.service = service;
    }

    @PostMapping("/compare")
    @Operation(summary = "Compare two quantities")
    public ResponseEntity<QuantityMeasurementDTO> performComparison(@Valid @RequestBody QuantityInputDTO input) {
        log.info("compare called with input: {}", input);
        
        if (input.getThisQuantityDTO() == null || input.getThatQuantityDTO() == null) {
            log.error("Invalid input: null quantities provided");
            return ResponseEntity.badRequest().build();
        }
        
        QuantityMeasurementDTO compareDto = service.compare(input.getThisQuantityDTO(), input.getThatQuantityDTO());
        log.info("Comparison result: {}", compareDto);
        return ResponseEntity.ok(compareDto);
    }

    @PostMapping("/convert")

    @Operation(summary = "Convert quantity to target unit")
    public ResponseEntity<QuantityMeasurementDTO> performConversion(@Valid @RequestBody QuantityInputDTO input) {
        log.info("convert called with input: {}", input);
        
        if (input.getThisQuantityDTO() == null || input.getThatQuantityDTO() == null) {
            log.error("Invalid input: null quantities provided");
            return ResponseEntity.badRequest().build();
        }
        
        QuantityMeasurementDTO result = service.convert(input.getThisQuantityDTO(), input.getThatQuantityDTO());
        log.info("Conversion result: {}", result);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    @Operation(summary = "Add two quantities")
    public ResponseEntity<QuantityMeasurementDTO> performAddition(@Valid @RequestBody QuantityInputDTO input) {
        log.info("add called with input: {}", input);
        
        if (input.getThisQuantityDTO() == null || input.getThatQuantityDTO() == null) {
            log.error("Invalid input: null quantities provided");
            return ResponseEntity.badRequest().build();
        }
        
        QuantityMeasurementDTO result = service.add(input.getThisQuantityDTO(), input.getThatQuantityDTO());
        log.info("Addition result: {}", result);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add-with-target-unit")
    @Operation(summary = "Add two quantities with target unit")
    public ResponseEntity<QuantityMeasurementDTO> performAdditionWithTargetUnit(
            @Valid @RequestBody QuantityInputDTO input) {
        log.info("add-with-target-unit called with input: {}", input);
        
        if (input.getThisQuantityDTO() == null || input.getThatQuantityDTO() == null || input.getTargetQuantityDTO() == null) {
            log.error("Invalid input: null quantities provided");
            return ResponseEntity.badRequest().build();
        }
        
        QuantityMeasurementDTO result = service.add(input.getThisQuantityDTO(), input.getThatQuantityDTO(), input.getTargetQuantityDTO());
        log.info("Addition with target unit result: {}", result);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/subtract")
    @Operation(summary = "Subtract two quantities")
    public ResponseEntity<QuantityMeasurementDTO> performSubtraction(@Valid @RequestBody QuantityInputDTO input) {
        log.info("subtract called with input: {}", input);
        
        if (input.getThisQuantityDTO() == null || input.getThatQuantityDTO() == null) {
            log.error("Invalid input: null quantities provided");
            return ResponseEntity.badRequest().build();
        }
        
        QuantityMeasurementDTO result = service.subtract(input.getThisQuantityDTO(), input.getThatQuantityDTO());
        log.info("Subtraction result: {}", result);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/subtract-with-target-unit")
    @Operation(summary = "Subtract two quantities with target unit")
    public ResponseEntity<QuantityMeasurementDTO> performSubtractionWithTargetUnit(
            @Valid @RequestBody QuantityInputDTO input) {
        log.info("subtract-with-target-unit called with input: {}", input);
        
        if (input.getThisQuantityDTO() == null || input.getThatQuantityDTO() == null || input.getTargetQuantityDTO() == null) {
            log.error("Invalid input: null quantities provided");
            return ResponseEntity.badRequest().build();
        }
        
        QuantityMeasurementDTO result = service.subtract(input.getThisQuantityDTO(), input.getThatQuantityDTO(), input.getTargetQuantityDTO());
        log.info("Subtraction with target unit result: {}", result);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/divide")
    @Operation(summary = "Divide two quantities")
    public ResponseEntity<QuantityMeasurementDTO> performDivision(@Valid @RequestBody QuantityInputDTO input) {
        log.info("divide called with input: {}", input);
        
        if (input.getThisQuantityDTO() == null || input.getThatQuantityDTO() == null) {
            log.error("Invalid input: null quantities provided");
            return ResponseEntity.badRequest().build();
        }
        
        // Check for division by zero if applicable
        if (input.getThatQuantityDTO().getValue() == 0) {
            log.error("Division by zero attempted");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        
        QuantityMeasurementDTO result = service.divide(input.getThisQuantityDTO(), input.getThatQuantityDTO());
        log.info("Division result: {}", result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/history/operation/{operation}")
    @Operation(summary = "Get operation history")
    public ResponseEntity<List<QuantityMeasurementDTO>> getOperationHistory(@PathVariable String operation) {
        log.info("getOperationHistory called for operation: {}", operation);
        
        if (operation == null || operation.trim().isEmpty()) {
            log.error("Invalid operation: null or empty");
            return ResponseEntity.badRequest().build();
        }
        
        List<QuantityMeasurementDTO> history = service.getOperationHistory(operation);
        log.info("Found {} history entries for operation: {}", history.size(), operation);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/history/type/{type}")
    @Operation(summary = "Get history by measurement type")
    public ResponseEntity<List<QuantityMeasurementDTO>> getOperationHistoryByType(@PathVariable String type) {
        log.info("getOperationHistoryByType called for type: {}", type);
        
        if (type == null || type.trim().isEmpty()) {
            log.error("Invalid type: null or empty");
            return ResponseEntity.badRequest().build();
        }
        
        List<QuantityMeasurementDTO> history = service.getMeasurementsByType(type);
        log.info("Found {} history entries for type: {}", history.size(), type);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/count/{operation}")
    @Operation(summary = "Get operation count")
    public ResponseEntity<Long> getOperationCount(@PathVariable String operation) {
        log.info("getOperationCount called for operation: {}", operation);
        
        if (operation == null || operation.trim().isEmpty()) {
            log.error("Invalid operation: null or empty");
            return ResponseEntity.badRequest().build();
        }
        
        Long count = service.getOperationCount(operation);
        log.info("Count for operation {}: {}", operation, count);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/history/errored")
    @Operation(summary = "Get errored operations history")
    public ResponseEntity<List<QuantityMeasurementDTO>> getErroredOperations() {
        log.info("getErroredOperations called");
        List<QuantityMeasurementDTO> errors = service.getErrorHistory();
        log.info("Found {} error entries", errors.size());
        return ResponseEntity.ok(errors);
    }
    
    // Global exception handler for this controller (optional - can also use @ControllerAdvice)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        log.error("Unexpected error occurred", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred: " + e.getMessage());
    }
}