package com.example.jogodavelha.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VictoryCombinations {
    public static final List<List<Integer>> WINNING_COMBINATIONS = Arrays.asList(
            Arrays.asList(0, 1, 2), // Linha superior
            Arrays.asList(3, 4, 5), // Linha do meio
            Arrays.asList(6, 7, 8), // Linha inferior
            Arrays.asList(0, 3, 6), // Coluna esquerda
            Arrays.asList(1, 4, 7), // Coluna do meio
            Arrays.asList(2, 5, 8), // Coluna direita
            Arrays.asList(0, 4, 8), // Diagonal principal
            Arrays.asList(2, 4, 6)  // Diagonal secundária
    );

    public static List<Integer> getWinningCombination(List<Integer> playerCombination) {
        for (List<Integer> winningCombination : WINNING_COMBINATIONS) {
            boolean hasCombination = true;

            for (Integer winningCombinationIndex : winningCombination) {
                if (!playerCombination.contains(winningCombinationIndex)) {
                    hasCombination = false;
                    break;
                }
            }

            if (hasCombination) return winningCombination;
        }

        return new ArrayList<Integer>();
    }
}


