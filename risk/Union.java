package ataxx;

import java.util.*;

public class Union {
    private int[] parents;
    private int N;

    public Union(int n, Map<Integer, List<Integer>> edges, int[] players, int player) {
        parents = new int[n];
        N = n;
        for (int i = 0; i < n; i += 1) {
            parents[i] = i;
        }

        for (int i = 0; i < n; i += 1) {
            for (int j : edges.get(i)) {
                if (players[i] == player && players[j] == player) {
                    union(i, j);
                }
            }
        }
    }

    public boolean connected(int u, int v) {
        return find(u) == find(v);
    }

    public int find(int x) {
        if (parents[x] != x) {
            parents[x] = find(parents[x]);
        }
        return parents[x];
    }

    public void union(int u, int v) {
        if (!connected(u, v)) {
            parents[find(v)] = find(u);
        }
    }

    public ArrayList<Integer> connectedComponent(int x) {
        ArrayList<Integer> connectedComponent = new ArrayList<Integer>();
        for (int i = 0; i < N; i += 1) {
            if (x != i && connected(x, i)) {
                connectedComponent.add(i);
            }
        }
        return connectedComponent;
    }
}
