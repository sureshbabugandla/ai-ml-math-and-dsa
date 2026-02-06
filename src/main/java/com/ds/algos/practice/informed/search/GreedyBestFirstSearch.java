package com.ds.algos.practice.informed.search;

import java.util.*;

public class GreedyBestFirstSearch {
    // Graph: city -> list of neighbors
    private static final Map<String, List<String>> map = new HashMap<>();

    // Heuristic: straight-line distance to Bucharest
    private static final Map<String, Integer> huristics = new HashMap<>();

    public static void main(String[] args) {
        buildMap();
        buildHeuristic();

        final String start = "Arad";
        final String goal = "Bucharest";

        final List<String> path = greedyBestFirstSearch(start, goal);

        if (!path.isEmpty()) {
            System.out.println("Path found: " + String.join(" -> ", path));
        } else {
            System.out.println("Goal not reachable");
        }
    }
    private static List<String> greedyBestFirstSearch(String start, String goal) {
        final PriorityQueue<Node> frontier = new PriorityQueue<>(Comparator.comparingInt(n -> n.heuristic));
        final Set<String> visited = new HashSet<>();

        frontier.add(new Node(start, huristics.get(start), List.of(start)));

        while (!frontier.isEmpty()) {
            final Node current = frontier.poll();
            final String city = current.city;

            if (city.equals(goal)) {
                return current.path;
            }

            visited.add(city);

            for (String neighbor : map.getOrDefault(city, List.of())) {
                if (!visited.contains(neighbor)) {
                    List<String> newPath = new ArrayList<>(current.path);
                    newPath.add(neighbor);
                    frontier.add(new Node(neighbor, huristics.get(neighbor), newPath));
                }
            }
        }

        return Collections.emptyList();
    }

    // Node class for priority queue
    private static class Node {
       private String city;
        private int heuristic;
        private List<String> path;

        Node(String city, int heuristic, List<String> path) {
            this.city = city;
            this.heuristic = heuristic;
            this.path = path;
        }
    }

    private static void buildMap() {
        map.put("Arad", List.of("Sibiu", "Timisoara", "Zerind"));
        map.put("Sibiu", List.of("Arad", "Fagaras", "Rimnicu Vilcea"));
        map.put("Timisoara", List.of("Arad", "Lugoj"));
        map.put("Zerind", List.of("Arad", "Oradea"));
        map.put("Oradea", List.of("Zerind", "Sibiu"));
        map.put("Fagaras", List.of("Sibiu", "Bucharest"));
        map.put("Rimnicu Vilcea", List.of("Sibiu", "Pitesti", "Craiova"));
        map.put("Lugoj", List.of("Timisoara", "Mehadia"));
        map.put("Mehadia", List.of("Lugoj", "Dobreta"));
        map.put("Dobreta", List.of("Mehadia", "Craiova"));
        map.put("Craiova", List.of("Dobreta", "Rimnicu Vilcea", "Pitesti"));
        map.put("Pitesti", List.of("Rimnicu Vilcea", "Craiova", "Bucharest"));
        map.put("Bucharest", List.of("Fagaras", "Pitesti", "Giurgiu", "Urziceni"));
        map.put("Giurgiu", List.of("Bucharest"));
        map.put("Urziceni", List.of("Bucharest", "Hirsova", "Vaslui"));
        map.put("Hirsova", List.of("Urziceni", "Eforie"));
        map.put("Eforie", List.of("Hirsova"));
        map.put("Vaslui", List.of("Urziceni", "Iasi"));
        map.put("Iasi", List.of("Vaslui", "Neamt"));
        map.put("Neamt", List.of("Iasi"));
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
}
