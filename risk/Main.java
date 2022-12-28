package ataxx;

import java.util.*;

public class Main {
    public static final int TERRITORIES = 42;

    public static final Map<Integer, String> names = Map.ofEntries(
            new AbstractMap.SimpleEntry<Integer, String>(0, "Alaska"),
            new AbstractMap.SimpleEntry<Integer, String>(1, "Northwest Territory"),
            new AbstractMap.SimpleEntry<Integer, String>(2, "Greenland"),
            new AbstractMap.SimpleEntry<Integer, String>(3, "Iceland"),
            new AbstractMap.SimpleEntry<Integer, String>(4, "Scandinavia"),
            new AbstractMap.SimpleEntry<Integer, String>(5, "Yakutsk"),
            new AbstractMap.SimpleEntry<Integer, String>(6, "Kamchatka"),
            new AbstractMap.SimpleEntry<Integer, String>(7, "Alberta"),
            new AbstractMap.SimpleEntry<Integer, String>(8, "Ontario"),
            new AbstractMap.SimpleEntry<Integer, String>(9, "Quebec"),
            new AbstractMap.SimpleEntry<Integer, String>(10, "Great Britain"),
            new AbstractMap.SimpleEntry<Integer, String>(11, "Northern Europe"),
            new AbstractMap.SimpleEntry<Integer, String>(12, "Ukraine"),
            new AbstractMap.SimpleEntry<Integer, String>(13, "Ural"),
            new AbstractMap.SimpleEntry<Integer, String>(14, "Siberia"),
            new AbstractMap.SimpleEntry<Integer, String>(15, "Irkutsk"),
            new AbstractMap.SimpleEntry<Integer, String>(16, "Japan"),
            new AbstractMap.SimpleEntry<Integer, String>(17, "Western United States"),
            new AbstractMap.SimpleEntry<Integer, String>(18, "Eastern United States"),
            new AbstractMap.SimpleEntry<Integer, String>(19, "Western Europe"),
            new AbstractMap.SimpleEntry<Integer, String>(20, "Southern Europe"),
            new AbstractMap.SimpleEntry<Integer, String>(21, "Afghanistan"),
            new AbstractMap.SimpleEntry<Integer, String>(22, "China"),
            new AbstractMap.SimpleEntry<Integer, String>(23, "Mongolia"),
            new AbstractMap.SimpleEntry<Integer, String>(24, "Central America"),
            new AbstractMap.SimpleEntry<Integer, String>(25, "Middle East"),
            new AbstractMap.SimpleEntry<Integer, String>(26, "India"),
            new AbstractMap.SimpleEntry<Integer, String>(27, "Siam"),
            new AbstractMap.SimpleEntry<Integer, String>(28, "Venezuela"),
            new AbstractMap.SimpleEntry<Integer, String>(29, "Brazil"),
            new AbstractMap.SimpleEntry<Integer, String>(30, "North Africa"),
            new AbstractMap.SimpleEntry<Integer, String>(31, "Egypt"),
            new AbstractMap.SimpleEntry<Integer, String>(32, "Indonesia"),
            new AbstractMap.SimpleEntry<Integer, String>(33, "New Guinea"),
            new AbstractMap.SimpleEntry<Integer, String>(34, "Peru"),
            new AbstractMap.SimpleEntry<Integer, String>(35, "Argentina"),
            new AbstractMap.SimpleEntry<Integer, String>(36, "Congo"),
            new AbstractMap.SimpleEntry<Integer, String>(37, "East Africa"),
            new AbstractMap.SimpleEntry<Integer, String>(38, "Western Australia"),
            new AbstractMap.SimpleEntry<Integer, String>(39, "Eastern Australia"),
            new AbstractMap.SimpleEntry<Integer, String>(40, "South Africa"),
            new AbstractMap.SimpleEntry<Integer, String>(41, "Madagascar")
    );

    public static final Map<Integer, List<Integer>> edges = Map.ofEntries(
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(0, List.of(1, 7)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(1, List.of(0, 2, 7, 8)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(2, List.of(1, 3, 8, 9)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(3, List.of(2, 4, 10)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(4, List.of(3, 10, 11, 12)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(5, List.of(6, 14, 15)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(6, List.of(0, 5, 15, 16)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(7, List.of(0, 1, 8, 17)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(8, List.of(1, 2, 7, 9, 17, 18)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(9, List.of(2, 8, 18)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(10, List.of(3, 4, 11, 19)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(11, List.of(4, 10, 12, 19, 20)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(12, List.of(4, 11, 13, 20, 21, 25)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(13, List.of(12, 14, 21, 22)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(14, List.of(5, 13, 15, 22, 23)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(15, List.of(5, 6, 14, 23)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(16, List.of(6, 15, 23)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(17, List.of(7, 8, 18, 24)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(18, List.of(8, 9, 17, 24)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(19, List.of(10, 11, 20, 30)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(20, List.of(11, 12, 19, 25, 30, 31)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(21, List.of(12, 13, 22, 25)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(22, List.of(13, 14, 21, 23, 26, 27)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(23, List.of(14, 15, 16, 22)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(24, List.of(17, 18, 28)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(25, List.of(12, 20, 26, 31, 37)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(26, List.of(21, 22, 25, 27)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(27, List.of(22, 26, 32)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(28, List.of(24, 29, 34)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(29, List.of(28, 30, 34, 35)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(30, List.of(19, 20, 29, 31, 36, 37)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(31, List.of(20, 30, 25, 37)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(32, List.of(27, 33, 38)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(33, List.of(32, 38, 39)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(34, List.of(28, 29, 35)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(35, List.of(29, 34)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(36, List.of(30, 37, 40)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(37, List.of(25, 30, 31, 36, 40, 41)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(38, List.of(32, 33, 39)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(39, List.of(33, 38)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(40, List.of(36, 37, 41)),
            new AbstractMap.SimpleEntry<Integer, List<Integer>>(41, List.of(37, 40))
    );

    public static final int[] scores = new int[]{
            12, //Alaska
            8, //Northwest Territory
            10, //Greenland
            10, //Iceland
            15, //Scandinavia
            9, //Yakutsk
            12, //Kamchatka
            8, //Alberta
            7, //Ontario
            7, //Quebec
            7, //Great Britain
            15, //Northern Europe
            26, //Ukraine
            17, //Ural
            9, //Siberia
            9, //Irutsk
            9, //Japan
            6, //Western United States
            6, //Eastern United States
            11, //Western Europe
            18, //Southern Europe
            17, //Afghanistan
            9, //China
            7, //Mongolia
            7, //Central America
            23, //Middle East
            15, //India
            9, //Siam
            7, //Venezuela
            10, //Brazil
            15, //North Africa
            17, //Egypt
            9, //Indonesia
            5, //New Guinea
            4, //Peru
            3, //Argentina
            10, //Congo
            11, //East Africa
            5, //Western Australia
            2, //Eastern Australia
            6, //South Africa
            6 //Madagascar
    };

    public static int[] players = new int[]{
            2, //Alaska
            2, //Northwest Territory
            2, //Greenland
            2, //Iceland
            2, //Scandinavia
            2, //Yakutsk
            2, //Kamchatka
            2, //Alberta
            2, //Ontario
            2, //Quebec
            1, //Great Britain
            2, //Northern Europe
            2, //Ukraine
            2, //Ural
            2, //Siberia
            2, //Irutsk
            2, //Japan
            2, //Western United States
            2, //Eastern United States
            2, //Western Europe
            2, //Southern Europe
            2, //Afghanistan
            2, //China
            2, //Mongolia
            2, //Central America
            2, //Middle East
            2, //India
            2, //Siam
            2, //Venezuela
            2, //Brazil
            2, //North Africa
            2, //Egypt
            2, //Indonesia
            2, //New Guinea
            2, //Peru
            2, //Argentina
            2, //Congo
            2, //East Africa
            2, //Western Australia
            2, //Eastern Australia
            2, //South Africa
            2 //Madagascar
    };
    public static int[] troops = new int[]{
            5, //Alaska
            8, //Northwest Territory
            15, //Greenland
            3, //Iceland
            5, //Scandinavia
            3, //Yakutsk
            10, //Kamchatka
            3, //Alberta
            3, //Ontario
            8, //Quebec
            155, //Great Britain
            3, //Northern Europe
            10, //Ukraine
            7, //Ural
            5, //Siberia
            3, //Irutsk
            3, //Japan
            8, //Western United States
            3, //Eastern United States
            5, //Western Europe
            8, //Southern Europe
            8, //Afghanistan
            10, //China
            5, //Mongolia
            10, //Central America
            15, //Middle East
            3, //India
            14, //Siam
            11, //Venezuela
            15, //Brazil
            10, //North Africa
            8, //Egypt
            9, //Indonesia
            5, //New Guinea
            5, //Peru
            8, //Argentina
            5, //Congo
            5, //East Africa
            3, //Western Australia
            8, //Eastern Australia
            8, //South Africa
            3 //Madagascar
    };
    public static int playerCount = 2;
    public static int player = 1;
    public static int troopsAvailable = 0;

    public static void main(String[] args) {
        AI ai = new AI();

        System.out.println(ai.bestMove(names, edges, players, troops, playerCount, player, troopsAvailable));
    }
}
