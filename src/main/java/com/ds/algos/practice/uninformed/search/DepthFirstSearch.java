package com.ds.algos.practice.uninformed.search;

import java.util.*;

public class DepthFirstSearch {
    // A Node represents a state in the search tree
    static class Node {
        private final String person;
        private final Node parent;

        Node(String person, Node parent) {
            this.person = person;
            this.parent = parent;
        }
    }

    public static List<String> search(String start, String target, Map<String, List<String>> network) {
        // Step 1: Initialize the frontier as a LIFO queue (Stack)
       final Deque<Node> frontier = new ArrayDeque<>();
        frontier.push(new Node(start, null));

        // Step 2: Initialize the explored set to handle repeated states
        final Set<String> explored = new HashSet<>();

        while (!frontier.isEmpty()) {
            // Step 3: Pop the deepest node from the frontier
            final Node currentNode = frontier.pop();
           final String currentState = currentNode.person;

            // Goal test: Check if we reached the target person
            if (currentState.equals(target)) {
                return constructPath(currentNode);
            }

            // Step 4: Add the node to the explored set
            explored.add(currentState);

            // Step 5: Expand the chosen node
            final List<String> neighbors = network.getOrDefault(currentState, new ArrayList<>());
            for (String neighbor : neighbors) {
                // Only add to frontier if not already explored or in frontier
                if (!explored.contains(neighbor) &&
                        frontier.stream().noneMatch(n -> n.person.equals(neighbor))) {
                    frontier.push(new Node(neighbor, currentNode));
                }
            }
        }
        return Collections.emptyList(); // Return empty if no connection exists
    }

    private static List<String> constructPath(Node node) {
        final List<String> path = new ArrayList<>();
        while (node != null) {
            path.add(0, node.person); // Backtrack through parents to find the path
            node = node.parent;
        }
        return path;
    }

    public static void main(String[] args) {
        // Adjacency list representing connections
       final Map<String, List<String>> network = new HashMap<>();
        network.put("Alice", Arrays.asList("Bob", "Charlie"));
        network.put("Bob", Arrays.asList("David", "Eve"));
        network.put("Charlie", List.of("Eve"));
        network.put("David", List.of("Frank"));
        network.put("Eve", List.of("Frank"));
        network.put("Frank", new ArrayList<>());

        final List<String> result = search("Alice", "David", network);

        if (result.isEmpty()) {
            System.out.println("No connection found.");
        } else {
            System.out.println("Path found via DFS: " + result);
        }
    }
}
