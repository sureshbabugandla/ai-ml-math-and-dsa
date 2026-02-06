package com.ds.algos.practice.uninformed.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DepthLimitSearch {
    // Define the possible outcomes of the search based on the sources
    enum ResultStatus { SUCCESS, FAILURE, CUTOFF }

    record SearchResult(ResultStatus status, List<String> path) {}

    public static SearchResult depthLimitedSearch(String start, String target,
                                                  Map<String, List<String>> network, int limit) {
        return recursiveDLS(start, target, network, limit, new ArrayList<>(List.of(start)));
    }

    private static SearchResult recursiveDLS(String node, String target,
                                             Map<String, List<String>> network,
                                             int limit, List<String> currentPath) {
        // Goal Test applied to the node [2]
        if (node.equals(target)) {
            return new SearchResult(ResultStatus.SUCCESS, currentPath);
        } else if (limit == 0) {
            return new SearchResult(ResultStatus.CUTOFF, null); // [2]
        } else {
           boolean cutoffOccurred = false;
            final List<String> neighbors = network.getOrDefault(node, Collections.emptyList());

            for (String neighbor : neighbors) {
                // To prevent infinite loops in a graph, we check if neighbor is in current path
                if (currentPath.contains(neighbor)) {
                    continue;
                }

                final List<String> nextPath = new ArrayList<>(currentPath);
                nextPath.add(neighbor);

                SearchResult result = recursiveDLS(neighbor, target, network, limit - 1, nextPath);

                if (result.status() == ResultStatus.SUCCESS) return result;
                if (result.status() == ResultStatus.CUTOFF) cutoffOccurred = true;
            }

            if (cutoffOccurred) return new SearchResult(ResultStatus.CUTOFF, null);
            else return new SearchResult(ResultStatus.FAILURE, null);
        }
    }

    public static void main(String[] args) {
        // Adjacency list representing LinkedIn connections
        Map<String, List<String>> network = Map.of(
                "You", List.of("Alice", "Bob"),
                "Alice", List.of("Charlie", "David"),
                "Charlie", List.of("Eve"),
                "Eve", List.of("TargetUser"),
                "David", List.of("TargetUser")
        );

        final int depthLimit = 2;
        final SearchResult result = depthLimitedSearch("You", "TargetUser", network, depthLimit);

        switch (result.status()) {
            case SUCCESS -> System.out.println("Profile found within " + depthLimit +
                    " degrees! Path: " + result.path());
            case CUTOFF -> System.out.println("Target not found within " + depthLimit +
                    " degrees (Search cut off).");
            case FAILURE -> System.out.println("No connection exists between users.");
        }
    }
}
