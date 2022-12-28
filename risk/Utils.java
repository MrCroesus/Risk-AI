package ataxx;

import java.util.*;

public class Utils {
    public static ArrayList<Integer> countTerritories(int[] players, int[] troops, int player, int minimumTroopCount) {
        ArrayList<Integer> playerTerritories = new ArrayList<Integer>();
        for (int i = 0; i < Main.TERRITORIES; i += 1) {
            if (players[i] == player && troops[i] >= minimumTroopCount) {
                playerTerritories.add(i);
            }
        }
        return playerTerritories;
    }

    public static ArrayList<Integer> countBorderTerritories(int[] players, int player) {
        ArrayList<Integer> borderTerritories = new ArrayList<Integer>();
        for (int i = 0; i < Main.TERRITORIES; i += 1) {
            for (int j : Main.edges.get(i)) {
                if (players[i] == player && players[j] != player) {
                    borderTerritories.add(i);
                }
            }
        }
        return borderTerritories;
    }

    public static int bestDraft(ArrayList<Integer> currentTerritories, int[] players, int[] troops, int player) {
        double worstScore = Integer.MAX_VALUE;
        int bestDraft = -1;
        for (int territory : currentTerritories) {
            if (defenseScore(territory, players, troops, player) < worstScore) {
                worstScore = defenseScore(territory, players, troops, player);
                bestDraft = territory;
            }
        }
        return bestDraft;
    }

    public static double defenseScore(int territory, int[] players, int[] troops, int player) {
        int totalDefense = troops[territory];
        for (int i : Main.edges.get(territory)) {
            if (players[i] != player) {
                totalDefense -= troops[i];
            }
        }
        return (double) totalDefense / Main.edges.get(territory).size();
    }

    public static ArrayList<int[]> countWinnableAttacks(int[] players, int[] troops, int player) {
        ArrayList<int[]> winnableAttacks = new ArrayList<int[]>();
        for (int i = 0; i < Main.TERRITORIES; i += 1) {
            for (int j : Main.edges.get(i)) {
                if (players[i] == player && players[j] != player && troops[i] > troops[j] + 1) {
                    winnableAttacks.add(new int[]{i, j});
                }
            }
        }
        return winnableAttacks;
    }

    public static int[] bestAttack(ArrayList<int[]> currentWinnableAttacks, int[] players, int[] troops, int player) {
        int bestScore = Integer.MIN_VALUE;
        int[] bestAttack = new int[2];
        for (int[] attack : currentWinnableAttacks) {
            int initialPlayer = players[attack[1]];
            players[attack[1]] = player;
            if (score(players, troops, player) > bestScore) {
                bestScore = score(players, troops, player);
                bestAttack = attack;
            }
            players[attack[1]] = initialPlayer;
        }
        return bestAttack;
    }

    public static int estimateTroopsAvailable(int[] players, int[] troops, int player) {
        int estimatedTroopsAvailable = 0;

        //Add the North America continent bonus
        if (player == players[0] && player == players[1] && player == players[2] && player == players[7]
                && player == players[8] && player == players[9] && player == players[17]
                && player == players[18] && player == players[24]) {
            estimatedTroopsAvailable += 5;
        }

        //Add the Europe continent bonus
        if (player == players[3] && player == players[4] && player == players[10] && player == players[11]
                && player == players[12] && player == players[19] && player == players[20]) {
            estimatedTroopsAvailable += 5;
        }

        //Add the Asia continent bonus
        if (player == players[5] && player == players[6] && player == players[13] && player == players[14]
                && player == players[15] && player == players[16] && player == players[21]
                && player == players[22] && player == players[23] && player == players[25]
                && player == players[26] && player == players[27]) {
            estimatedTroopsAvailable += 7;
        }

        //Add the South America continent bonus
        if (player == players[28] && player == players[29] && player == players[34] && player == players[35]) {
            estimatedTroopsAvailable += 2;
        }

        //Add the Africa continent bonus
        if (player == players[30] && player == players[31] && player == players[36] && player == players[37]
                && player == players[40] && player == players[41]) {
            estimatedTroopsAvailable += 3;
        }

        //Add the Australia continent bonus
        if (player == players[32] && player == players[33] && player == players[38] && player == players[39]) {
            estimatedTroopsAvailable += 2;
        }

        estimatedTroopsAvailable += Math.max(3, countTerritories(players, troops, player, 0).size() / 3);
        return estimatedTroopsAvailable;
    }

    public static int bestSource(ArrayList<Integer> currentTerritories, int[] players, int[] troops, int player) {
        double bestScore = Integer.MIN_VALUE;
        int bestSource = -1;
        for (int territory : currentTerritories) {
            if (defenseScore(territory, players, troops, player) > bestScore && troops[territory] > 1) {
                bestScore = defenseScore(territory, players, troops, player);
                bestSource = territory;
            }
        }
        return bestSource;
    }

    public static int score(int[] players, int[] troops, int player) {
        int score = 0;

        int[][] territoriesByContinent = new int[][]{
                new int[]{0, 1, 2, 7, 8, 9, 17, 18, 24},
                new int[]{3, 4, 10, 11, 12, 19, 20},
                new int[]{5, 6, 13, 14, 15, 16, 21, 22, 23, 25, 26, 27},
                new int[]{28, 29, 34, 35},
                new int[]{30, 31, 36, 37, 40, 41},
                new int[]{32, 33, 38, 39}
        };

        for (int[] continent : territoriesByContinent) {
            for (int territory : continent) {
                if (players[territory] == player) {
                    //score += Main.scores[territory];
                    score += defenseScore(territory, players, troops, player) + bonus(players, player, continent, territory);
                }
            }
        }
        return score;
    }

    public static int bonus(int[] players, int player, int[] continentTerritories, int territory) {
        if (players[territory] == player) {
            return 0;
        }

        int countPlayerContinentTerritories = 0;

        for (int i : continentTerritories) {
            if (players[i] == player) {
                countPlayerContinentTerritories += 1;
            }
        }

        return countPlayerContinentTerritories * 5;
    }
}