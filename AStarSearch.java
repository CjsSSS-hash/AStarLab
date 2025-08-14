

 import java.util.*;

class Node implements Comparable<Node> {
    public int x, y;
    public double gCost, hCost;
    public Node parent;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double fCost() {
        return gCost + hCost;
    }

    @Override
    public int compareTo(Node other) {
        return Double.compare(this.fCost(), other.fCost());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Node)) return false;
        Node other = (Node) obj;
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

public class AStarSearch {
    static int[][] grid = {
        {0, 0, 0, 0, 0},
        {0, 0, 1, 0, 0},
        {0, 0, 1, 0, 0},
        {0, 1, 0, 0, 0},
        {0, 0, 0, 0, 0}
    };

    static int ROWS = 5, COLS = 5;

    static List<Node> findPath(Node start, Node goal) {
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Set<Node> closedSet = new HashSet<>();
        openSet.add(start);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.equals(goal)) {
                List<Node> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = current.parent;
                }
                Collections.reverse(path);
                return path;
            }

            closedSet.add(current);
            for (int[] dir : new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}}) {
                int nx = current.x + dir[0], ny = current.y + dir[1];
                if (nx >= 0 && ny >= 0 && nx < ROWS && ny < COLS && grid[nx][ny] == 0) {
                    Node neighbor = new Node(nx, ny);
                    if (closedSet.contains(neighbor)) continue;

                    double tentativeG = current.gCost + 1;
                    boolean better = false;

                    if (!openSet.contains(neighbor)) {
                        neighbor.hCost = Math.abs(nx - goal.x) + Math.abs(ny - goal.y);
                        better = true;
                    } else if (tentativeG < neighbor.gCost) {
                        better = true;
                    }

                    if (better) {
                        neighbor.gCost = tentativeG;
                        neighbor.parent = current;
                        openSet.add(neighbor);
                    }
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Node start = new Node(0, 0);
        Node goal = new Node(4, 4);
        List<Node> path = findPath(start, goal);

        if (path != null) {
            System.out.println("Path found:");
            for (Node n : path) {
                System.out.println("(" + n.x + ", " + n.y + ")");
            }
        } else {
            System.out.println("No path found.");
        }
    }
}


