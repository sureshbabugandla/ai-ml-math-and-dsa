package com.ds.algos.practice.informed.search;

import java.util.*;

public class AStarSearch {
    // Graph: city -> neighbors with actual road distance
    private static final Map<String, Map<String, Integer>> map = new HashMap<>();

    // Heuristic: straight-line distance to Bucharest
    private static final Map<String, Integer> huristics = new HashMap<>();

    public static void main(String[] args) {
        buildMap();
        buildHeuristic();

        final String start = "Arad";
        final String goal = "Bucharest";

        final List<String> path = aStarSearch(start, goal);

        if (!path.isEmpty()) {
            System.out.println("Shortest path found: " + String.join(" -> ", path));
        } else {
            System.out.println("Goal not reachable");
        }
    }

    private static void buildMap() {
        map.put("Arad", Map.of("Sibiu", 140, "Timisoara", 118, "Zerind", 75));
        map.put("Sibiu", Map.of("Arad", 140, "Fagaras", 99, "Rimnicu Vilcea", 80));
        map.put("Timisoara", Map.of("Arad", 118, "Lugoj", 111));
        map.put("Zerind", Map.of("Arad", 75, "Oradea", 71));
        map.put("Oradea", Map.of("Zerind", 71, "Sibiu", 151));
        map.put("Fagaras", Map.of("Sibiu", 99, "Bucharest", 211));
        map.put("Rimnicu Vilcea", Map.of("Sibiu", 80, "Pitesti", 97, "Craiova", 146));
        map.put("Lugoj", Map.of("Timisoara", 111, "Mehadia", 70));
        map.put("Mehadia", Map.of("Lugoj", 70, "Dobreta", 75));
        map.put("Dobreta", Map.of("Mehadia", 75, "Craiova", 120));
        map.put("Craiova", Map.of("Dobreta", 120, "Rimnicu Vilcea", 146, "Pitesti", 138));
        map.put("Pitesti", Map.of("Rimnicu Vilcea", 97, "Craiova", 138, "Bucharest", 101));
        map.put("Bucharest", Map.of("Fagaras", 211, "Pitesti", 101, "Giurgiu", 90, "Urziceni", 85));
        map.put("Giurgiu", Map.of("Bucharest", 90));
        map.put("Urziceni", Map.of("Bucharest", 85, "Hirsova", 151, "Vaslui", 199));
        map.put("Hirsova", Map.of("Urziceni", 151, "Eforie", 161));
        map.put("Eforie", Map.of("Hirsova", 161));
        map.put("Vaslui", Map.of("Urziceni", 199, "Iasi", 226));
        map.put("Iasi", Map.of("Vaslui", 226, "Neamt", 234));
        map.put("Neamt", Map.of("Iasi", 234));
    }

    private static void buildHeuristic() {
        huristics.put("Arad", 366);
        huristics.put("Bucharest", 0);
        huristics.put("Craiova", 160);
        huristics.put("Dobreta", 242);
        huristics.put("Eforie", 161);
        huristics.put("Fagaras", 176);
        huristics.put("Giurgiu", 77);
        huristics.put("Hirsova", 151);
        huristics.put("Iasi", 226);
        huristics.put("Lugoj", 244);
        huristics.put("Mehadia", 241);
        huristics.put("Neamt", 234);
        huristics.put("Oradea", 380);
        huristics.put("Pitesti", 100);
        huristics.put("Rimnicu Vilcea", 193);
        huristics.put("Sibiu", 253);
        huristics.put("Timisoara", 329);
        huristics.put("Urziceni", 80);
        huristics.put("Vaslui", 199);
        huristics.put("Zerind", 374);
    }
    private static List<String> aStarSearch(String start, String goal) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
        Map<String, Integer> gScores = new HashMap<>();
        Set<String> closedSet = new HashSet<>();

        gScores.put(start, 0);
        openSet.add(new Node(start, 0 + huristics.get(start), List.of(start)));

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.city.equals(goal)) {
                return current.path;
            }

            closedSet.add(current.city);

            for (Map.Entry<String, Integer> neighborEntry : map.getOrDefault(current.city, Map.of()).entrySet()) {
                String neighbor = neighborEntry.getKey();
                int cost = neighborEntry.getValue();

                if (closedSet.contains(neighbor)){
                    continue;
                }

                int tentativeG = gScores.get(current.city) + cost;

                if (!gScores.containsKey(neighbor) || tentativeG < gScores.get(neighbor)) {
                    gScores.put(neighbor, tentativeG);
                    int fScore = tentativeG + huristics.get(neighbor);

                    List<String> newPath = new ArrayList<>(current.path);
                    newPath.add(neighbor);
                    openSet.add(new Node(neighbor, fScore, newPath));
                }
            }
        }

        return Collections.emptyList();
    }

    private static class Node {
        String city;
        int f; // f = g + h
        List<String> path;

        Node(String city, int f, List<String> path) {
            this.city = city;
            this.f = f;
            this.path = path;
        }
    }
}
