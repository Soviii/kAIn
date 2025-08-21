package com.example.recipetags.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeTagDTO {
    private Long recipeId;
    private Long tagId;
}
