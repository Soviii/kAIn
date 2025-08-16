package com.example.ingredients.dto;
import com.example.ingredients.model.Ingredient;
public class IngredientResponseDTO {
    // Declare fields
    private Long id;
    private String name;
    private double quantity;
    private String unit;

    // Default constructor
    public IngredientResponseDTO() {
        // Default constructor required for some frameworks
    }

    // Parameterized constructor
    public IngredientResponseDTO(Long id, String name, double quantity, String unit) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    // used for converting Ingredient to IngredientResponseDTO
    public IngredientResponseDTO(Ingredient ingredient) {
        this.id = ingredient.getId();
        this.name = ingredient.getName();
        this.quantity = ingredient.getQuantity();
        this.unit = ingredient.getUnit();
    }

    // Getters and setters 
    // These methods are used to access and modify the fields of the IngredientResponseDTO class
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}