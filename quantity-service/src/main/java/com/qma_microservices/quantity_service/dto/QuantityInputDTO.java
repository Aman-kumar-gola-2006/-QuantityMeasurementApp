package com.qma_microservices.quantity_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Schema(description = "Input DTO for quantity operations", 
        example = """
        {
            "thisQuantityDTO": { "value": 1.0, "unit": "FEET", "measurementType": "LengthUnit" },
            "thatQuantityDTO": { "value": 12.0, "unit": "INCHES", "measurementType": "LengthUnit" },
            "targetQuantityDTO": { "value": 0.0, "unit": "INCHES", "measurementType": "LengthUnit" }
        }
        """)
public class QuantityInputDTO {
    
    private static final Logger logger = LoggerFactory.getLogger(QuantityInputDTO.class);
    
    @Valid
    @NotNull(message = "First quantity cannot be null")
    @Schema(description = "First quantity for the operation", required = true)
    private QuantityDTO thisQuantityDTO;

    @Valid
    @NotNull(message = "Second quantity cannot be null")
    @Schema(description = "Second quantity for the operation", required = true)
    private QuantityDTO thatQuantityDTO;

    @Valid
    @Schema(description = "Target quantity/unit for conversion (optional)", 
            nullable = true, 
            example = "{\"value\": 0.0, \"unit\": \"INCHES\", \"measurementType\": \"LengthUnit\"}")
    private QuantityDTO targetQuantityDTO;

    // Constructors
    public QuantityInputDTO() {}
    
    public QuantityInputDTO(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
        this.thisQuantityDTO = thisQuantityDTO;
        this.thatQuantityDTO = thatQuantityDTO;
    }
    
    public QuantityInputDTO(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetQuantityDTO) {
        this.thisQuantityDTO = thisQuantityDTO;
        this.thatQuantityDTO = thatQuantityDTO;
        this.targetQuantityDTO = targetQuantityDTO;
    }

    // Getters
    public QuantityDTO getThisQuantityDTO() {
        return thisQuantityDTO;
    }

    // Setters
    public void setThisQuantityDTO(QuantityDTO thisQuantityDTO) {
        this.thisQuantityDTO = thisQuantityDTO;
    }

    public QuantityDTO getThatQuantityDTO() {
        return thatQuantityDTO;
    }

    public void setThatQuantityDTO(QuantityDTO thatQuantityDTO) {
        this.thatQuantityDTO = thatQuantityDTO;
    }

    public QuantityDTO getTargetQuantityDTO() {
        return targetQuantityDTO;
    }

    public void setTargetQuantityDTO(QuantityDTO targetQuantityDTO) {
        this.targetQuantityDTO = targetQuantityDTO;
    }
    
    // Helper methods for validation
    public boolean hasTargetQuantity() {
        return targetQuantityDTO != null;
    }
    
    public boolean hasValidTargetUnit() {
        if (targetQuantityDTO == null) {
            return false;
        }
        return targetQuantityDTO.isValidUnit();
    }
    
    // Validate that both quantities have the same measurement type
    public boolean isSameMeasurementType() {
        if (thisQuantityDTO == null || thatQuantityDTO == null) {
            logger.warn("Cannot check measurement type: one or both quantities are null");
            return false;
        }
        
        boolean isSame = thisQuantityDTO.getMeasurementType() != null && 
                        thisQuantityDTO.getMeasurementType().equals(thatQuantityDTO.getMeasurementType());
        
        if (!isSame) {
            logger.warn("Measurement type mismatch: {} vs {}", 
                       thisQuantityDTO.getMeasurementType(), 
                       thatQuantityDTO.getMeasurementType());
        }
        
        return isSame;
    }
    
    // Validate that target unit matches the measurement type of the quantities
    public boolean isTargetUnitCompatible() {
        if (targetQuantityDTO == null) {
            return true; // No target unit specified, so it's compatible
        }
        
        if (thisQuantityDTO == null) {
            return false;
        }
        
        boolean isCompatible = thisQuantityDTO.getMeasurementType() != null && 
                              thisQuantityDTO.getMeasurementType().equals(targetQuantityDTO.getMeasurementType());
        
        if (!isCompatible) {
            logger.warn("Target unit measurement type mismatch: {} vs {}", 
                       thisQuantityDTO.getMeasurementType(), 
                       targetQuantityDTO.getMeasurementType());
        }
        
        return isCompatible;
    }
    
    // Validate all quantities are valid
    public boolean isValid() {
        if (thisQuantityDTO == null || !thisQuantityDTO.isValidUnit()) {
            logger.warn("Invalid first quantity");
            return false;
        }
        
        if (thatQuantityDTO == null || !thatQuantityDTO.isValidUnit()) {
            logger.warn("Invalid second quantity");
            return false;
        }
        
        if (targetQuantityDTO != null && !targetQuantityDTO.isValidUnit()) {
            logger.warn("Invalid target quantity");
            return false;
        }
        
        if (!isSameMeasurementType()) {
            logger.warn("Quantities have different measurement types");
            return false;
        }
        
        if (!isTargetUnitCompatible()) {
            logger.warn("Target unit is not compatible with the quantities");
            return false;
        }
        
        return true;
    }
    
    // Get the measurement type (assuming both quantities have same type)
    public String getMeasurementType() {
        if (thisQuantityDTO != null) {
            return thisQuantityDTO.getMeasurementType();
        }
        return null;
    }
    
    @Override
    public String toString() {
        return String.format("QuantityInputDTO{thisQuantity=%s, thatQuantity=%s, targetQuantity=%s}", 
                            thisQuantityDTO, thatQuantityDTO, targetQuantityDTO);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        QuantityInputDTO that = (QuantityInputDTO) obj;
        
        if (thisQuantityDTO != null ? !thisQuantityDTO.equals(that.thisQuantityDTO) : that.thisQuantityDTO != null)
            return false;
        if (thatQuantityDTO != null ? !thatQuantityDTO.equals(that.thatQuantityDTO) : that.thatQuantityDTO != null)
            return false;
        return targetQuantityDTO != null ? targetQuantityDTO.equals(that.targetQuantityDTO) : that.targetQuantityDTO == null;
    }
    
    @Override
    public int hashCode() {
        int result = thisQuantityDTO != null ? thisQuantityDTO.hashCode() : 0;
        result = 31 * result + (thatQuantityDTO != null ? thatQuantityDTO.hashCode() : 0);
        result = 31 * result + (targetQuantityDTO != null ? targetQuantityDTO.hashCode() : 0);
        return result;
    }
}