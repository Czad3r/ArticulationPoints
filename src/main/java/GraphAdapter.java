import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;


public class GraphAdapter {
    private Graph graphViz;
    private MutableGraph g;
    private LinkedList<Integer>[] adjacentList;
    private boolean[] isArticPoint;
    private String[] verticesNames;
    private int numberOfVertices;

    public static BufferedImage image;

    public GraphAdapter(Domain.Graph inputGraph) {
        initFromMyGraph(inputGraph);

        for (int i = 0; i < numberOfVertices; i++) {
            String name;
            if (verticesNames[i].equals(""))  //Checking if vertice have name
                name = Integer.toString(i);
            else
                name = verticesNames[i];

            MutableNode node = mutNode(name);
            if (isArticPoint[i]) {
                node.add(Color.RED);
            }
            for (int linkForNode : adjacentList[i]) {
                if (verticesNames[linkForNode].equals(""))
                    node.addLink(mutNode(Integer.toString(linkForNode)));
                else
                    node.addLink(mutNode(verticesNames[linkForNode]));
            }
            g.add(node);
        }
        generateImage(Format.PNG);
    }

    private void initFromMyGraph(Domain.Graph inputGraph) {
        g = mutGraph("").setDirected(false).setStrict(true);
        adjacentList = inputGraph.getAdj();
        isArticPoint = inputGraph.getAp();
        verticesNames = inputGraph.getVerticesNames();
        numberOfVertices = adjacentList.length;
    }

    public static Image getImage() {
        return image;
    }

    private void generateImage(Format format) {
        image = Graphviz.fromGraph(g).render(format).toImage();
    }
}
