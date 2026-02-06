package com.ds.algos.practice.uninformed.search.bfs;

import java.util.*;

public class BreadthFirstSearch {
    //Node represents the individual user profile
    static class Node {
       private final String state;
        private final Node parent;

        Node(String state, Node parent) {
            this.state = state;
            this.parent = parent;
        }
    }

    public static List<String> search(String start, String goal, Map<String, List<String>> adjList) {
        // Step 1: Initialize the frontier as a FIFO queue
        final Queue<Node> frontier = new LinkedList<>();
        frontier.add(new Node(start, null));

        // Step 2: Initialize the explored set to handle redundant paths
        final Set<String> explored = new HashSet<>();

        // Step 3: Loop while the frontier is not empty
        while (!frontier.isEmpty()) {
            // Pop the oldest (shallowest) node
            final Node node = frontier.poll();
            explored.add(node.state);

            // Step 4: Expand the node and check neighbors
            final List<String> neighbors = adjList.getOrDefault(node.state, Collections.emptyList());
            for (String neighborState : neighbors) {
                if (!explored.contains(neighborState) &&
                        frontier.stream().noneMatch(n -> n.state.equals(neighborState))) {

                    final Node child = new Node(neighborState, node);

                    // Goal test is applied when a node is generated
                    if (child.state.equals(goal)) {
                        return constructPath(child);
                    }
                    frontier.add(child);
                }
            }
        }
        return Collections.emptyList(); // Return failure if no path found
    }

    private static List<String> constructPath(Node node) {
        final List<String> path = new ArrayList<>();
        while (node != null) {
            path.add(0, node.state);
            node = node.parent; // Following parent pointers back to the root
        }
        return path;
    }

    public static void main(String[] args) {
        // Simple adjacency list representing LinkedIn connections
        final Map<String, List<String>> network = Map.of(
                "Alice", List.of("Bob", "Charlie"),
                "Bob", List.of("Alice", "David", "Eve"),
                "Charlie", List.of("Alice", "Eve"),
                "David", List.of("Bob"),
                "Eve", List.of("Bob", "Charlie", "Frank"),
                "Frank", List.of("Eve")

        );

        final List<String> result = search("Alice", "Frank", network);
        System.out.println("Path found: " + result);
    }
}
