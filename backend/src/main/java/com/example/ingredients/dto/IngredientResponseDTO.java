package com.example.ingredients.dto;

public class IngredientResponseDTO {
    // Declare fields
    private Long id;
    private String name;
    private double quantity;

    // Default constructor
    public IngredientResponseDTO() {
        // Default constructor required for some frameworks
    }

    // Parameterized constructor
    public IngredientResponseDTO(Long id, String name, double quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
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
}