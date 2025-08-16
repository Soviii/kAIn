package com.example.steps.model;

import com.example.recipes.model.Recipe;
import jakarta.persistence.*;

@Entity
@Table(name = "steps")
public class Step {
    // Declaring columns for the recipe table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Maps to SERIAL in PostgreSQL (auto-increment)
    private Long id;

    private int step_number;

    @Column(columnDefinition = "TEXT") // allows unlimited length in Postgres (TODO: maybe fix????)
    private String instruction;

    // making a reference to recipe obj and creating a foreign key relationship with the Recipe entity
    // this will allow us to associate steps with a specific recipe_id
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    // Constructors
    public Step() {}

    public Step(int step_number, String instruction, Recipe recipe) {
        this.step_number = step_number;
        this.instruction = instruction;
        this.recipe = recipe;
    }

    // Getters and Setters
    public Long getId() {       // used to get the step id
        return id;
    }

    public void setId(Long id) {    // used to set the step id
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

    public Recipe getRecipe() {     // used to get the recipe associated with the step
        return recipe;
    }

    public void setRecipe(Recipe recipe) {    // used to set the recipe associated with the step
        this.recipe = recipe;
    }
}
