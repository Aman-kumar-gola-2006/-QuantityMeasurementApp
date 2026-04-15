package com.qma_microservices.quantity_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Schema(description = "A quantity with a value and unit")
public class QuantityDTO {
    private static final Logger logger = LoggerFactory.getLogger(QuantityDTO.class);

    // Interface representing measurable unit
    public interface IMeasurableUnit {
        String getUnitName();
        String getMeasurementType();
        double getConversionToBase();
        String getBaseUnit();
    }

    // Length Units
    public enum LengthUnit implements IMeasurableUnit {
        FEET("FEET", 30.48, "CENTIMETERS"),
        INCHES("INCHES", 2.54, "CENTIMETERS"),
        YARDS("YARDS", 91.44, "CENTIMETERS"),
        CENTIMETERS("CENTIMETERS", 1.0, "CENTIMETERS");

        private final String unit;
        private final double conversionToBase;
        private final String baseUnit;

        LengthUnit(String unit, double conversionToBase, String baseUnit) {
            this.unit = unit;
            this.conversionToBase = conversionToBase;
            this.baseUnit = baseUnit;
        }

        @Override
        public String getUnitName() {
            return unit;
        }

        @Override
        public String getMeasurementType() {
            return "LengthUnit";
        }

        @Override
        public double getConversionToBase() {
            return conversionToBase;
        }

        @Override
        public String getBaseUnit() {
            return baseUnit;
        }
    }

    // Volume Units
    public enum VolumeUnit implements IMeasurableUnit {
        LITRE("LITRE", 1000.0, "MILLILITER"),
        MILLILITER("MILLILITER", 1.0, "MILLILITER"),
        GALLON("GALLON", 3785.41, "MILLILITER");

        private final String unit;
        private final double conversionToBase;
        private final String baseUnit;

        VolumeUnit(String unit, double conversionToBase, String baseUnit) {
            this.unit = unit;
            this.conversionToBase = conversionToBase;
            this.baseUnit = baseUnit;
        }

        @Override
        public String getUnitName() {
            return unit;
        }

        @Override
        public String getMeasurementType() {
            return "VolumeUnit";
        }

        @Override
        public double getConversionToBase() {
            return conversionToBase;
        }

        @Override
        public String getBaseUnit() {
            return baseUnit;
        }
    }

    // Weight Units
    public enum WeightUnit implements IMeasurableUnit {
        MILLIGRAM("MILLIGRAM", 0.001, "GRAM"),
        GRAM("GRAM", 1.0, "GRAM"),
        KILOGRAM("KILOGRAM", 1000.0, "GRAM"),
        POUND("POUND", 453.592, "GRAM"),
        TONNE("TONNE", 1000000.0, "GRAM");

        private final String unit;
        private final double conversionToBase;
        private final String baseUnit;

        WeightUnit(String unit, double conversionToBase, String baseUnit) {
            this.unit = unit;
            this.conversionToBase = conversionToBase;
            this.baseUnit = baseUnit;
        }

        @Override
        public String getUnitName() {
            return unit;
        }

        @Override
        public String getMeasurementType() {
            return "WeightUnit";
        }

        @Override
        public double getConversionToBase() {
            return conversionToBase;
        }

        @Override
        public String getBaseUnit() {
            return baseUnit;
        }
    }

    // Temperature Units
    public enum TemperatureUnit implements IMeasurableUnit {
        CELSIUS("CELSIUS", 0, 1.0, "CELSIUS"),
        FAHRENHEIT("FAHRENHEIT", -32, 5.0/9.0, "CELSIUS");

        private final String unit;
        private final double offset;
        private final double multiplier;
        private final String baseUnit;

        TemperatureUnit(String unit, double offset, double multiplier, String baseUnit) {
            this.unit = unit;
            this.offset = offset;
            this.multiplier = multiplier;
            this.baseUnit = baseUnit;
        }

        @Override
        public String getUnitName() {
            return unit;
        }

        @Override
        public String getMeasurementType() {
            return "TemperatureUnit";
        }

        @Override
        public double getConversionToBase() {
            return multiplier;
        }
        
        public double getOffset() {
            return offset;
        }

        @Override
        public String getBaseUnit() {
            return baseUnit;
        }
    }

    // Value of the quantity
    @NotNull(message = "Value cannot be null")
    @Schema(example = "1.0")
    private double value;

    // Unit of the quantity
    @NotNull(message = "Unit cannot be null")
    @Schema(example = "FEET", allowableValues = {
            "FEET", "INCHES", "YARDS", "CENTIMETERS",
            "LITRE", "MILLILITER", "GALLON",
            "MILLIGRAM", "GRAM", "KILOGRAM", "POUND", "TONNE",
            "CELSIUS", "FAHRENHEIT"
    })
    private String unit;

    // Measurement type
    @NotNull(message = "Measurement type cannot be null")
    @Pattern(regexp = "LengthUnit|VolumeUnit|WeightUnit|TemperatureUnit", 
             message = "Measurement type must be one of: LengthUnit, VolumeUnit, WeightUnit, TemperatureUnit")
    @Schema(example = "LengthUnit", allowableValues = {
            "LengthUnit", "VolumeUnit", "WeightUnit", "TemperatureUnit"
    })
    private String measurementType;

    // Constructors
    public QuantityDTO() {}

    public QuantityDTO(double value, String unit, String measurementType) {
        this.value = value;
        this.unit = unit;
        this.measurementType = measurementType;
    }

    // Constructor using enum
    public QuantityDTO(double value, IMeasurableUnit unit) {
        this.value = value;
        this.unit = unit.getUnitName();
        this.measurementType = unit.getMeasurementType();
    }

    // Getters and Setters
    public double getValue() { 
        return value; 
    }
    
    public void setValue(double value) { 
        this.value = value; 
    }

    public String getUnit() { 
        return unit; 
    }
    
    public void setUnit(String unit) { 
        this.unit = unit; 
    }

    public String getMeasurementType() { 
        return measurementType; 
    }
    
    public void setMeasurementType(String measurementType) { 
        this.measurementType = measurementType; 
    }

    // Helper method to get the enum unit
    public IMeasurableUnit getUnitEnum() {
        logger.debug("Getting unit enum for: {} - {}", unit, measurementType);
        
        try {
            switch (measurementType) {
                case "LengthUnit":
                    return LengthUnit.valueOf(unit);
                case "VolumeUnit":
                    return VolumeUnit.valueOf(unit);
                case "WeightUnit":
                    return WeightUnit.valueOf(unit);
                case "TemperatureUnit":
                    return TemperatureUnit.valueOf(unit);
                default:
                    return null;
            }
        } catch (IllegalArgumentException e) {
            logger.error("Invalid unit: {} for type: {}", unit, measurementType, e);
            return null;
        }
    }

    // Convert to base unit value
    public double convertToBaseUnit() {
        IMeasurableUnit unitEnum = getUnitEnum();
        if (unitEnum == null) {
            throw new IllegalArgumentException("Invalid unit or measurement type");
        }

        if (measurementType.equals("TemperatureUnit")) {
            TemperatureUnit tempUnit = (TemperatureUnit) unitEnum;
            return (value + tempUnit.getOffset()) * tempUnit.getConversionToBase();
        } else {
            return value * unitEnum.getConversionToBase();
        }
    }

    // Convert from base unit to this unit
    public double convertFromBaseUnit(double baseValue) {
        IMeasurableUnit unitEnum = getUnitEnum();
        if (unitEnum == null) {
            throw new IllegalArgumentException("Invalid unit or measurement type");
        }

        if (measurementType.equals("TemperatureUnit")) {
            TemperatureUnit tempUnit = (TemperatureUnit) unitEnum;
            return (baseValue / tempUnit.getConversionToBase()) - tempUnit.getOffset();
        } else {
            return baseValue / unitEnum.getConversionToBase();
        }
    }

    // Custom validation to check if unit matches measurement type
    @AssertTrue(message = "Unit must be valid for the specified measurement type")
    public boolean isValidUnit() {
        logger.info("Validating unit: {} for measurement type: {}", unit, measurementType);
        return getUnitEnum() != null;
    }

    // Validate that value is appropriate (not negative for most types)
    @AssertTrue(message = "Value must be non-negative for non-temperature measurements")
    public boolean isValidValue() {
        if (measurementType == null) {
            return false;
        }
        
        // Temperature can be negative, other measurements cannot
        if (measurementType.equals("TemperatureUnit")) {
            return true;
        }
        
        return value >= 0;
    }

    // Convert to another unit of same measurement type
    public QuantityDTO convertTo(String targetUnit) {
        if (!isValidUnit()) {
            throw new IllegalArgumentException("Invalid source unit");
        }
        
        // Create a temporary DTO for the target unit
        QuantityDTO targetDto = new QuantityDTO();
        targetDto.setUnit(targetUnit);
        targetDto.setMeasurementType(this.measurementType);
        
        if (!targetDto.isValidUnit()) {
            throw new IllegalArgumentException("Invalid target unit: " + targetUnit);
        }
        
        // Convert to base unit first, then to target unit
        double baseValue = this.convertToBaseUnit();
        double convertedValue = targetDto.convertFromBaseUnit(baseValue);
        
        return new QuantityDTO(convertedValue, targetUnit, this.measurementType);
    }

    @Override
    public String toString() {
        return String.format("QuantityDTO{value=%s, unit='%s', measurementType='%s'}", 
                            value, unit, measurementType);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        QuantityDTO that = (QuantityDTO) obj;
        
        // Compare by converting both to base units
        try {
            double thisBaseValue = this.convertToBaseUnit();
            double thatBaseValue = that.convertToBaseUnit();
            
            // Use a small delta for floating point comparison
            return Math.abs(thisBaseValue - thatBaseValue) < 0.0001 &&
                   this.measurementType.equals(that.measurementType);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        try {
            long bits = Double.doubleToLongBits(convertToBaseUnit());
            return (int) (bits ^ (bits >>> 32)) + measurementType.hashCode();
        } catch (Exception e) {
            return super.hashCode();
        }
    }
}