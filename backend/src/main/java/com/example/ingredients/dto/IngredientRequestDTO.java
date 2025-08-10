package com.example.ingredients.dto;

public class IngredientRequestDTO {
    // Declare fields
    private String name;
    private double quantity;
    private String unit;

    // Default constructor
    public IngredientRequestDTO() {
        // Default constructor required for some frameworks
    }

    // Parameterized constructor
    public IngredientRequestDTO(String name, double quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    // Getters and setters
    // These methods are used to access and modify the fields of the IngredientRequestDTO class
    public String getName() {
        return name;
    }   

    public void setName(String name) {
        this.name = name;
    }   

    public double getQuantity(){
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}