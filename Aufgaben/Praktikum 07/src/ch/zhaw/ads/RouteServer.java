package ch.zhaw.ads;

import java.util.*;

public class RouteServer implements CommandExecutor {
    /**
    build the graph given a text file with the topology
    */
    public Graph<DijkstraNode, Edge> createGraph(String topo) throws Exception {
        // TODO implement
        Graph<DijkstraNode, Edge> graph = new AdjListGraph<DijkstraNode, Edge>(DijkstraNode.class, Edge.class);

        String[] routes = topo.split("\n");
        for (String rout: routes){
            String[] routSplit = rout.split(" ");
            try {
                graph.addEdge(routSplit[0], routSplit[1], Double.parseDouble(routSplit[2]));
                graph.addEdge(routSplit[1], routSplit[0], Double.parseDouble(routSplit[2]));
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
        return graph;
    }


    /**
    apply the dijkstra algorithm
    */
    public void dijkstraRoute(Graph<DijkstraNode, Edge> graph, String from, String to) {
        // TODO implement

        for (DijkstraNode dijkstraNode: graph.getNodes()){
            dijkstraNode.setDist(Double.MAX_VALUE);
            dijkstraNode.setMark(false);
            dijkstraNode.setPrev(null);
        }

        PriorityQueue<DijkstraNode> redNodes = new PriorityQueue<>();
        DijkstraNode startNode = graph.findNode(from);
        DijkstraNode goal = graph.findNode(to);
        startNode.setDist(0);
        startNode.setMark(true);
        redNodes.add(startNode);



        while (!redNodes.isEmpty()){
            DijkstraNode current = redNodes.remove();
            current.setMark(true);
            if(current == goal) return;
            for (Edge edge: current.getEdges()){
                DijkstraNode neighbour = (DijkstraNode) edge.getDest();
                if (!neighbour.getMark()){
                    double dist = current.getDist() + edge.getWeight();
                    if (dist < neighbour.getDist()){
                        neighbour.setDist(dist);
                        neighbour.setPrev(current);
                        redNodes.remove(neighbour);
                        redNodes.add(neighbour);
                    }
                }
            }
        }
    }

    /**
    find the route in the graph after applied dijkstra
    the route should be returned with the start town first
    */
    public List<DijkstraNode> getRoute(Graph<DijkstraNode, Edge> graph, String to) {
        List<DijkstraNode> route = new LinkedList<>();
        DijkstraNode town = graph.findNode(to);
        do {
            route.add(0, town);
            town = town.getPrev();
        } while (town != null);
        return route;
    }

    public String execute(String topo) throws Exception {
        Graph<DijkstraNode, Edge> graph = createGraph(topo);
        dijkstraRoute(graph, "Winterthur", "Lugano");
        List<DijkstraNode> route = getRoute(graph, "Lugano");
        // generate result string
        StringBuilder builder = new StringBuilder();
        for (DijkstraNode rt : route) builder.append(rt).append("\n");
        return builder.toString();
    }

    public static void main(String[] args)throws Exception {
        String swiss = "Winterthur Zürich 25\n" +
                    "Zürich Bern 126\n" +
                    "Zürich Genf 277\n" +
                    "Zürich Luzern 54\n" +
                    "Zürich Chur 121\n" +
                    "Zürich Berikon 16\n" +
                    "Bern Genf 155\n" +
                    "Genf Lugano 363\n" +
                    "Lugano Luzern 206\n" +
                    "Lugano Chur 152\n" +
                    "Chur Luzern 146\n" +
                    "Luzern Bern 97\n" +
                    "Bern Berikon 102\n" +
                    "Luzern Berikon 41\n";
        RouteServer server = new RouteServer();
        System.out.println(server.execute(swiss));
    }
}
