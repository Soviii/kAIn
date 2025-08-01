package com.example.steps.model;

import com.example.recipes.model.Recipe;
import jakarta.persistence.*;

@Entity
@Table(name = "steps")
public class Step {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Maps to SERIAL in PostgreSQL (auto-increment)
    private Long id;

    private int step_number;
    private String instruction;

    // making a reference to recipe obj and creating a foreign key relationship with the Recipe entity
    // this will allow us to associate steps with a specific recipe_id
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    // Constructors, getters, and setters
    public Step() {}

    public Step(int step_number, String instruction, Recipe recipe) {
        this.step_number = step_number;
        this.instruction = instruction;
        this.recipe = recipe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStepNumber() {
        return step_number;
    }

    public void setStepNumber(int step_number) {
        this.step_number = step_number;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
