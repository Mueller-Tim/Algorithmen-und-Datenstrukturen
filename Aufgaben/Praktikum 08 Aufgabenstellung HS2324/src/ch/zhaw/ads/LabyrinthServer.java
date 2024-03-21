package ch.zhaw.ads;

import java.awt.*;

public class LabyrinthServer implements CommandExecutor {
    ServerGraphics g = new ServerGraphics();

    public Graph<DijkstraNode, Edge> createGraph(String s) {
        // TODO implement 8.2
        Graph<DijkstraNode, Edge> graph = new AdjListGraph<>(DijkstraNode.class, Edge.class);

        s.lines().forEach(line->{
            String[] nodes = line.split(" ");
            String firstNode = nodes[0];
            String secondNode = nodes[1];
            try {
                graph.addEdge(firstNode, secondNode, 1);
                graph.addEdge(secondNode, firstNode, 1);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }

        });
        return graph;
    }

    public void drawLabyrinth(Graph<DijkstraNode, Edge> graph) {
        // TODO implement 8.3
        graph.getNodes().forEach(node -> {
            node.getEdges().forEach(edge -> {
               g.drawPath(node.toString(), edge.getDest().toString(), false);
            });
        });
    }

    private boolean search(DijkstraNode current, DijkstraNode goal) {
        // TODO implement 8.4
        current.setMark(true);
		if(current == goal) return true;

        for (Edge adjacentEdge : current.getEdges()){
            DijkstraNode adjacentNode = (DijkstraNode) adjacentEdge.getDest();
            if (!adjacentNode.getMark() && search(adjacentNode, goal)){
                adjacentNode.setPrev(current);
                return true;
            }
        }
        return false;
	}

    // search and draw result
    public void drawRoute(Graph<DijkstraNode, Edge> graph, String start, String goal) {
        // TODO implement 8.4
        DijkstraNode startNode = graph.findNode(start);
        DijkstraNode goalNode = graph.findNode(goal);
        search(startNode, goalNode);
        DijkstraNode currentNode = goalNode;
        while(currentNode != startNode){
            g.drawPath(currentNode.toString(), currentNode.getPrev().toString(), true);
            currentNode = currentNode.getPrev();
        }
    }

    public String execute(String s) {
        g.setColor(Color.red);
        Graph<DijkstraNode, Edge> graph;
        graph = createGraph(s);
        drawLabyrinth(graph);
        drawRoute(graph, "0-6", "3-0");
        return g.getTrace();
    }
}
