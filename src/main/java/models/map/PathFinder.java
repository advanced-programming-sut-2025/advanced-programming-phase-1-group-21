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
        int maxEnergy = player.getEnergy();

        // Quick check: target must exist and be walkable
        Tile targetTile = map.getTile(target);
        if (targetTile == null || !targetTile.getTileType().isWalkable()) {
            return Collections.emptyList();
        }

        // A* state = (coord, facingDirection)
        record State(Coord coord, Direction facing) {}

        // Priority queue ordered by fScore = gScore + heuristic
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        java.util.Map<State, Node> allNodes = new HashMap<>();
        Set<State> closed = new HashSet<>();

        // Initialize start node (no facing yet)
        Node startNode = new Node(start, null, null, 0, heuristic(start, target));
        State startState = new State(start, null);
        openSet.add(startNode);
        allNodes.put(startState, startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            State currState = new State(current.coord, current.direction);

            // Skip if we've already finalized this state
            if (!closed.add(currState)) {
                continue;
            }

            // Goal check
            if (current.coord.equals(target)) {
                return reconstructPath(current);
            }

            // Explore neighbors
            for (Direction dir : Direction.values()) {
                Coord nextCoord = current.coord.addCoord(dir.getCoord());
                Tile nextTile = map.getTile(nextCoord);
                if (nextTile == null || !nextTile.getTileType().isWalkable()) {
                    continue;
                }

                // Compute turn cost (3) if changing facing after the first move
                int turnCost = (current.direction == null || current.direction == dir) ? 0 : 3;
                int tentativeG = current.gScore + 1 + turnCost;
                if (tentativeG > maxEnergy) {
                    continue;  // prune paths that exceed available energy
                }

                State nextState = new State(nextCoord, dir);
                Node neighbor = allNodes.getOrDefault(
                        nextState,
                        new Node(nextCoord, dir, null, Integer.MAX_VALUE, 0)
                );

                if (tentativeG < neighbor.gScore) {
                    // Better path found
                    neighbor.gScore   = tentativeG;
                    neighbor.fScore   = tentativeG + heuristic(nextCoord, target);
                    neighbor.cameFrom = current;
                    neighbor.direction = dir;

                    // Refresh in openSet
                    openSet.remove(neighbor);
                    openSet.add(neighbor);
                    allNodes.put(nextState, neighbor);
                }
            }
        }

        // No path found
        return Collections.emptyList();
    }

    private List<PathStep> reconstructPath(Node endNode) {
        Deque<Node> stack = new ArrayDeque<>();
        Node curr = endNode;
        while (curr.cameFrom != null) {
            stack.push(curr);
            curr = curr.cameFrom;
        }

        List<PathStep> steps = new ArrayList<>();
        Direction prevDir = null;
        while (!stack.isEmpty()) {
            Node n = stack.pop();
            int turnCost = (prevDir == null || prevDir == n.direction) ? 0 : 3;
            int stepCost = 1 + turnCost;
            steps.add(new PathStep(n.coord, n.direction, stepCost));
            prevDir = n.direction;
        }
        return steps;
    }

    private int heuristic(Coord a, Coord b) {
        // Manhattan distance
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    private static class Node implements Comparable<Node> {
        final Coord coord;
        Direction direction;    // facing when arriving here
        Node cameFrom;          // link to predecessor
        int gScore;             // cost from start
        int fScore;             // gScore + heuristic

        Node(Coord coord, Direction direction, Node cameFrom, int gScore, int fScore) {
            this.coord = coord;
            this.direction = direction;
            this.cameFrom = cameFrom;
            this.gScore = gScore;
            this.fScore = fScore;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.fScore, other.fScore);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Node)) return false;
            Node n = (Node) o;
            return coord.equals(n.coord) &&
                    Objects.equals(direction, n.direction);
        }

        @Override
        public int hashCode() {
            return Objects.hash(coord, direction);
        }
    }

    public record PathStep(Coord coord, Direction direction, int energyCost) {
        @Override
        public String toString() {
            return String.format("Move %s to %s (Energy: -%d)",
                    direction, coord, energyCost);
        }
    }
}