package com.example.ingredients.model;

import com.example.recipes.model.Recipe;
import jakarta.persistence.*;


@Entity
@Table(name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Maps to SERIAL in PostgreSQL (auto-increment)
    private Long id;

    private String name;
    private double quantity;
    private String unit;

    // making a reference to recipe obj and creating a foreign key relationship with the Recipe entity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_uuid", nullable = false)
    private Recipe recipe;

    // Constructors, getters, and setters
    public Ingredient() {}

    public Ingredient(String name, double quantity, String unit, Recipe recipe) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.recipe = recipe;
    }

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

    public double getQuantity(){
        return quantity;
    }

    public void setQuantity(double quantity){
        this.quantity = quantity;
    }

    public String getUnit(){
        return unit;
    }

    public void setUnit(String unit){
        this.unit = unit;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe= recipe;
    }
}