package ataxx;

import java.util.*;

public class AI {
    public static final int ITERATIONS = 1;

    public static String bestMove = "";

    public String bestMove(Map<Integer, String> names, Map<Integer, List<Integer>> edges,
                           int[] players, int[] troops, int playerCount, int player, int troopsAvailable) {
        minMax(names, edges, players, troops, playerCount, player, troopsAvailable, player, playerCount, 0, Integer.MAX_VALUE);
        return bestMove;
    }

    public double minMax(Map<Integer, String> names, Map<Integer, List<Integer>> edges,
                      int[] players, int[] troops, int playerCount, int player, int troopsAvailable,
                      int currentPlayer, int turn, double alpha, double beta) {
        if (turn == 0) {
            int netScore = 0;
            for (int i = 1; i <= playerCount; i += 1) {
                if (i == player) {
                    netScore += Utils.score(players, troops, i);
                } else {
                    netScore -= Utils.score(players, troops, i);
                }
            }
            return (double) netScore / Utils.countBorderTerritories(players, player).size();
        }

        for (int iteration = 0; iteration < ITERATIONS; iteration += 1) {
            double currentScore = 0;
            String currentMove = "";

            int[] currentPlayers = Arrays.copyOf(players, players.length);
            int[] currentTroops = Arrays.copyOf(troops, troops.length);

            //Draft phase
            ArrayList<Integer> currentBorderTerritories = Utils.countBorderTerritories(currentPlayers, currentPlayer);
            int currentTroopsAvailable = troopsAvailable;

            if (turn == playerCount) {
                currentMove += "Draft: \n";
            }

            //Draft map stores total troops moved per territory
            Map<Integer, Integer> draft = new HashMap<Integer, Integer>();
            for (int i = 0; i < Main.TERRITORIES; i += 1) {
                draft.put(i, 0);
            }

            //Select the worst defended border territories to deploy available troops to
            while (currentTroopsAvailable > 0 && currentBorderTerritories.size() > 0) {
                int territory = Utils.bestDraft(currentBorderTerritories, currentPlayers, currentTroops, currentPlayer);
                if (currentPlayers[territory] == currentPlayer) {
                    currentTroops[territory] += 1;
                    currentTroopsAvailable -= 1;

                    draft.put(territory, draft.get(territory) + 1);
                }
            }

            for (int i = 0; i < Main.TERRITORIES; i += 1) {
                if (turn == playerCount && draft.get(i) != 0) {
                    currentMove += (names.get(i) + " drafts " + draft.get(i) + "\n");
                }
            }

            //Attack phase
            ArrayList<int[]> currentWinnableAttacks = Utils.countWinnableAttacks(currentPlayers, currentTroops, currentPlayer);

            if (turn == playerCount) {
                currentMove += "Attack: \n";
            }

            while (currentWinnableAttacks.size() > 0) {
                //Select the best winnable attack to execute
                int[] attack = Utils.bestAttack(currentWinnableAttacks, currentPlayers, currentTroops, currentPlayer);
                currentTroops[attack[0]] -= currentTroops[attack[1]];
                currentTroops[attack[1]] = 0;
                currentPlayers[attack[1]] = currentPlayer;

                //Occupation variable stores total troops moved
                int occupation = Math.min(3, currentTroops[attack[0]] - 1);

                //Select size of occupation force until the destination is as defended as the source
                if (currentTroops[attack[0]] > 3
                        && Utils.countBorderTerritories(currentPlayers, currentPlayer).contains(attack[0])) {
                    currentTroops[attack[0]] -= occupation;
                    currentTroops[attack[1]] += occupation;
                    while (currentTroops[attack[0]] > 1
                            && Utils.defenseScore(attack[0], currentPlayers, currentTroops, currentPlayer)
                            > Utils.defenseScore(attack[1], currentPlayers, currentTroops, currentPlayer)) {
                        occupation += 1;
                        currentTroops[attack[0]] -= 1;
                        currentTroops[attack[1]] += 1;
                    }
                } else {
                    occupation = currentTroops[attack[0]] - 1;
                    currentTroops[attack[0]] -= occupation;
                    currentTroops[attack[1]] += occupation;
                }

                if (turn == playerCount) {
                    currentMove += (names.get(attack[0]) + " attacks " + names.get(attack[1]) + ", moves " + occupation + "\n");
                }

                currentWinnableAttacks = Utils.countWinnableAttacks(currentPlayers, currentTroops, currentPlayer);
            }

            //Fortify phase
            currentBorderTerritories = Utils.countBorderTerritories(currentPlayers, currentPlayer);

            if (turn == playerCount) {
                currentMove += "Fortify: \n";
            }

            //Select the best defended source and worst defended destination that are connected for fortification
            Union union = new Union(Main.TERRITORIES, edges, currentPlayers, currentPlayer);
            int source, destination;
            if (currentBorderTerritories.size() > 0) {
                destination = Utils.bestDraft(currentBorderTerritories, currentPlayers, currentTroops, currentPlayer);
                source = Utils.bestSource(union.connectedComponent(destination), currentPlayers, currentTroops, currentPlayer);

                //Fortification variable stores total troops moved
                int fortification = 0;

                //Select size of fortification force until the destination is as defended as the source
                while (source >= 0 && currentTroops[source] > 1
                        && Utils.defenseScore(source, currentPlayers, currentTroops, currentPlayer)
                        > Utils.defenseScore(destination, currentPlayers, currentTroops, currentPlayer)) {
                    fortification += 1;
                    currentTroops[source] -= 1;
                    currentTroops[destination] += 1;
                }

                if (turn == playerCount) {
                    if (fortification > 0) {
                        currentMove += (names.get(source) + " fortifies " + names.get(destination) + ", moves " + fortification + "\n");
                    } else {
                        currentMove += "Do not fortify \n";
                    }
                }
            }

            currentScore = minMax(names, edges, currentPlayers, currentTroops,
                    playerCount, player, Utils.estimateTroopsAvailable(currentPlayers, currentTroops, currentPlayer),
                    currentPlayer % playerCount + 1, turn - 1, alpha, beta);

            if (currentPlayer == player && currentScore > alpha) {
                alpha = currentScore;
                bestMove = currentMove;

            } else if (currentScore < beta) {
                beta = currentScore;
                bestMove = currentMove;
            }
            if (alpha >= beta) {
                break;
            }
        }
        if (currentPlayer == player) {
            return alpha;
        } else {
            return beta;
        }
    }
}
