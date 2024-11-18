package GraphTraversalComponent.src.com.example.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * A generic class to represent a graph and perform common traversal algorithms.
 *
 * @param <T> The type of nodes in the graph.
 */
public class GraphTraversal<T> implements GraphTraversalSecondary<T> {

    // The adjacency list to store graph edges.
    private final Map<T, List<T>> adjacencyList = new HashMap<>();

    /**
     * Adds a node to the graph.
     *
     * @param node The node to be added.
     */
    @Override
    public void addNode(T node) {
        // Ensure the node exists in the graph, initializing an empty adjacency list if necessary.
        adjacencyList.putIfAbsent(node, new ArrayList<>());
    }

    /**
     * Adds a directed edge between two nodes in the graph.
     *
     * @param from The source node of the edge.
     * @param to   The destination node of the edge.
     */
    @Override
    public void addEdge(T from, T to) {
        // Ensure both nodes exist before adding the edge.
        adjacencyList.putIfAbsent(from, new ArrayList<>());
        adjacencyList.putIfAbsent(to, new ArrayList<>());
        adjacencyList.get(from).add(to);
    }

    /**
     * Retrieves a list of all nodes in the graph.
     *
     * @return A list of all nodes in the graph.
     */
    @Override
    public List<T> getNodes() {
        return new ArrayList<>(adjacencyList.keySet());
    }

    /**
     * Retrieves a list of edges (neighbors) for a specific node.
     *
     * @param node The node whose edges are to be retrieved.
     * @return A list of neighboring nodes.
     */
    @Override
    public List<T> getEdges(T node) {
        return adjacencyList.getOrDefault(node, new ArrayList<>());
    }

    /**
     * Performs a depth-first traversal (DFS) starting from the given node.
     *
     * @param start The starting node for the DFS traversal.
     * @return A list of nodes visited in DFS order.
     */
    @Override
    public List<T> traverseDFS(T start) {
        List<T> visited = new ArrayList<>();
        Set<T> seen = new HashSet<>();
        dfsHelper(start, seen, visited); // Helper method for recursion.
        return visited;
    }

    /**
     * Helper method to recursively perform DFS.
     *
     * @param current The current node being visited.
     * @param seen    A set of nodes that have already been visited.
     * @param visited The list of nodes visited in order.
     */
    private void dfsHelper(T current, Set<T> seen, List<T> visited) {
        if (!seen.contains(current)) {
            seen.add(current); // Mark the current node as seen.
            visited.add(current); // Add the current node to the visited list.
            // Recursively visit all neighbors of the current node.
            for (T neighbor : adjacencyList.getOrDefault(current, new ArrayList<>())) {
                dfsHelper(neighbor, seen, visited);
            }
        }
    }

    /**
     * Performs a breadth-first traversal (BFS) starting from the given node.
     *
     * @param start The starting node for the BFS traversal.
     * @return A list of nodes visited in BFS order.
     */
    @Override
    public List<T> traverseBFS(T start) {
        List<T> visited = new ArrayList<>();
        Queue<T> q = new LinkedList<>();
        Set<T> seen = new HashSet<>();

        q.add(start); // Add the starting node to the queue.
        seen.add(start); // Mark the starting node as seen.

        while (!q.isEmpty()) {
            T current = q.poll(); // Remove the next node from the queue.
            visited.add(current); // Add the current node to the visited list.

            // Add all unvisited neighbors to the queue.
            for (T neighbor : adjacencyList.getOrDefault(current, new ArrayList<>())) {
                if (!seen.contains(neighbor)) {
                    seen.add(neighbor); // Mark the neighbor as seen.
                    q.add(neighbor); // Add the neighbor to the queue.
                }
            }
        }
        return visited;
    }

    /**
     * Finds a path between two nodes (placeholder implementation).
     *
     * @param start The starting node of the path.
     * @param end   The destination node of the path.
     * @return A list representing the path (empty in this placeholder implementation).
     */
    @Override
public List<T> findPath(T start, T end) {
    // Check if start or end node does not exist in the graph
    if (!adjacencyList.containsKey(start) || !adjacencyList.containsKey(end)) {
        return new ArrayList<>();
    }

    // Check if start and end are the same
    if (start.equals(end)) {
        List<T> path = new ArrayList<>();
        path.add(start);
        return path;
    }

    // Queue for BFS
    Queue<T> queue = new LinkedList<>();
    // Map to track the parent of each node for path reconstruction
    Map<T, T> parentMap = new HashMap<>();
    // Set to track visited nodes
    Set<T> visited = new HashSet<>();

    // Initialize BFS
    queue.add(start);
    visited.add(start);

    while (!queue.isEmpty()) {
        T current = queue.poll();

        // Explore neighbors
        for (T neighbor : adjacencyList.getOrDefault(current, new ArrayList<>())) {
            if (!visited.contains(neighbor)) {
                visited.add(neighbor);
                parentMap.put(neighbor, current); // Track the parent
                queue.add(neighbor);

                // If we reach the destination, reconstruct the path
                if (neighbor.equals(end)) {
                    return reconstructPath(parentMap, start, end);
                }
            }
        }
    }

    // If no path exists, return an empty list
    return new ArrayList<>();
}

/**
 * Helper method to reconstruct the path from start to end using the parent map.
 *
 * @param parentMap A map containing each node's parent.
 * @param start     The starting node of the path.
 * @param end       The destination node of the path.
 * @return A list representing the path from start to end.
 */
private List<T> reconstructPath(Map<T, T> parentMap, T start, T end) {
    List<T> path = new LinkedList<>();
    T current = end;

    // Backtrack from end to start using the parent map
    while (current != null) {
        path.add(0, current); // Add to the front of the list
        current = parentMap.get(current);
    }

    return path;
}

    /**
     * Resets the graph by removing all nodes and edges.
     */
    @Override
    public void resetGraph() {
        adjacencyList.clear(); // Clear the adjacency list to reset the graph.
    }
}
