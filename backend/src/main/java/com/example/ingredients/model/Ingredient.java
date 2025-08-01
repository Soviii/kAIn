package com.example.ingredients.model;

import com.example.recipes.model.Recipe;
import jakarta.persistence.*;


@Entity
@Table(name = "ingredients")
public class Ingredient {
    // Declaring columns for the recipe table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Maps to SERIAL in PostgreSQL (auto-increment)
    private Long id;

    private String name;
    private double quantity;
    private String unit;

    // making a reference to recipe obj and creating a foreign key relationship with the Recipe entity
    // this will allow us to associate ingredients with a specific recipe_id
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    // Constructors
    public Ingredient() {}

    public Ingredient(String name, double quantity, String unit, Recipe recipe) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.recipe = recipe;
    }

    // Getters and Setters
    public Long getId() {       // used to get the ingredient id
        return id;
    }

    public void setId(Long id) {    // used to set the ingredient id
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

    public Recipe getRecipe() {       // used to get the recipe associated with the ingredient
        return recipe;
    }

    public void setRecipe(Recipe recipe) {    // used to set the recipe associated with the ingredient
        this.recipe= recipe;
    }
}