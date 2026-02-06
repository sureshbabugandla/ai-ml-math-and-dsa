package com.ds.algos.practice.uninformed.search;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IterativeDeepeningSearch {
    public static void main(String[] args) {

        final Map<String, List<String>> network = Map.of(
                "You", List.of("Alice", "Bob"),
                "Alice", List.of("Charlie", "David"),
                "Charlie", List.of("Eve"),
                "Eve", List.of("TargetUser"),
                "David", List.of("TargetUser"),
                "Bob", List.of()
        );

        final String start = "You";
        final String target = "TargetUser";
        final boolean found = iterativeDeepeningSearch(network, start, target, 5);

        System.out.println(found ? "Target found!" : "Target not found.");
    }
    private static boolean iterativeDeepeningSearch(
            Map<String, List<String>> graph,
            String start,
            String target,
            int maxDepth
    ) {
        for (int depth = 0; depth <= maxDepth; depth++) {
            System.out.println("Searching with depth limit: " + depth);
            Set<String> visited = new HashSet<>();
            if (depthLimitedDFS(graph, start, target, depth, visited)) {
                return true;
            }
        }
        return false;
    }

    private static boolean depthLimitedDFS(
            Map<String, List<String>> graph,
            String current,
            String target,
            int depth,
            Set<String> visited
    ) {
        if (current.equals(target)) {
            return true;
        }
        if (depth == 0) {
            return false;
        }
        visited.add(current);
        for (String neighbor : graph.getOrDefault(current, List.of())) {
            if (!visited.contains(neighbor)) {
                if (depthLimitedDFS(graph, neighbor, target, depth - 1, visited)) {
                    return true;
                }
            }
        }
        return false;
    }
}
