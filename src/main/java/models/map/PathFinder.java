package models.map;

import models.game.Player;
import java.util.*;

public class PathFinder {
    private final Player player;
    private final Map map;

    public PathFinder(Player player) {
        this.player = player;
        this.map = player.getMap();
    }

    public List<PathStep> findPathTo(Coord target) {
        Coord start = player.getCoord();
        List<PathStep> path = new ArrayList<>();

        // Check if target is walkable
        Tile targetTile = map.getTile(target);
        if (targetTile == null || !targetTile.getTileType().isWalkable()) {
            return path; // Empty path if target is not walkable
        }

        // A* algorithm implementation
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        java.util.Map<Coord, Node> allNodes = new HashMap<>();

        Node startNode = new Node(start, null, 0, heuristic(start, target));
        openSet.add(startNode);
        allNodes.put(start, startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            // Check if we've reached the target
            if (current.coord.equals(target)) {
                // Reconstruct path
                return reconstructPath(current, player.getEnergy());
            }

            // Explore neighbors
            for (Direction direction : Direction.values()) {
                Coord neighborCoord = current.coord.addCoord(direction.getCoord());
                Tile neighborTile = map.getTile(neighborCoord);

                // Skip if not walkable or doesn't exist
                if (neighborTile == null || !neighborTile.getTileType().isWalkable()) {
                    continue;
                }

                // Calculate tentative gScore (energy cost)
                int turnCost = current.direction == null || current.direction == direction ? 0 : 3;
                int tentativeGScore = current.gScore + 1 + turnCost; // 1 for move, 3 for turn

                Node neighborNode = allNodes.getOrDefault(neighborCoord,
                        new Node(neighborCoord, null, Integer.MAX_VALUE, 0));

                if (tentativeGScore < neighborNode.gScore) {
                    neighborNode.cameFrom = current;
                    neighborNode.direction = direction;
                    neighborNode.gScore = tentativeGScore;
                    neighborNode.fScore = tentativeGScore + heuristic(neighborCoord, target);

                    // Remove and re-add to update position in priority queue
                    openSet.remove(neighborNode);
                    openSet.add(neighborNode);

                    allNodes.put(neighborCoord, neighborNode);
                }
            }
        }

        return null; // No path found
    }

    private List<PathStep> reconstructPath(Node endNode, int maxEnergy) {
        List<PathStep> path = new ArrayList<>();
        Node current = endNode;
        int totalEnergy = 0;

        // First build path in reverse order
        List<Node> reversePath = new ArrayList<>();
        while (current.cameFrom != null) {
            reversePath.add(current);
            current = current.cameFrom;
        }
        Collections.reverse(reversePath);

        // Convert to PathSteps with energy costs
        Direction prevDirection = null;
        for (Node node : reversePath) {
            if (totalEnergy >= maxEnergy) break;

            int turnCost = prevDirection == null || prevDirection == node.direction ? 0 : 3;
            int stepCost = 1 + turnCost;

            if (totalEnergy + stepCost > maxEnergy) break;

            path.add(new PathStep(node.coord, node.direction, stepCost));
            totalEnergy += stepCost;
            prevDirection = node.direction;
        }

        return path;
    }

    private int heuristic(Coord a, Coord b) {
        // Manhattan distance for heuristic
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    private static class Node implements Comparable<Node> {
        Coord coord;
        Node cameFrom;
        Direction direction;
        int gScore; // Energy cost from start
        int fScore; // gScore + heuristic

        public Node(Coord coord, Node cameFrom, int gScore, int fScore) {
            this.coord = coord;
            this.cameFrom = cameFrom;
            this.gScore = gScore;
            this.fScore = fScore;
            this.direction = null;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.fScore, other.fScore);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return coord.equals(node.coord);
        }

        @Override
        public int hashCode() {
            return coord.hashCode();
        }
    }

    public record PathStep(Coord coord, Direction direction, int energyCost) {

        @Override
            public String toString() {
                return String.format("Move %s to %s (Energy: -%d)", direction, coord, energyCost);
            }
        }
}