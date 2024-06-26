package com.example.culinaryblogapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDto {
    private long productId;
    private long unitId;
    private double quantity;
    private int ordinalNr;
}
