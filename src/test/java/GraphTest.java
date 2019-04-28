import Domain.Graph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class GraphTest {

    @Test
    void punkty_artykulacji_0() throws Exception {
        Graph g = new Graph(5);
        g.addEdge(1, 0);
        g.addEdge(0, 2);
        g.addEdge(2, 1);
        g.addEdge(0, 3);
        g.addEdge(3, 4);

        assertEquals(0, g.countAP());

    }

    @Test
    void punkty_artykulacji_1() throws Exception {
        Graph g = new Graph(5);
        g.addEdge(1, 0);
        g.addEdge(0, 2);
        g.addEdge(2, 1);
        g.addEdge(0, 3);
        g.addEdge(3, 4);
        g.findAP();

        assertEquals(2, g.countAP());

    }

    @Test
    void punkty_artykulacji_2() throws Exception {
        Graph g = new Graph(4);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.findAP();

        assertEquals(2, g.countAP());
    }

    @Test
    void punkty_artykulacji_3() throws Exception {
        Graph g = new Graph(7);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 0);
        g.addEdge(1, 3);
        g.addEdge(1, 4);
        g.addEdge(1, 6);
        g.addEdge(3, 5);
        g.addEdge(4, 5);
        g.findAP();

        assertEquals(1, g.countAP());
        assertEquals("1 ",g.printAP());
    }

    @Test
    void nadawanie_nazw_wierzchołkom_1() {
        Graph g = new Graph(4);
        try {
            g.addNameForVertice(0, "Test1");
            g.addNameForVertice(1, "Test2");
            g.addNameForVertice(2, "Test3");
            g.addNameForVertice(3, "Test4");
        } catch (Exception e) {
            e.printStackTrace();
        }

        g.addEdge("Test1", "Test2");
        g.addEdge("Test2", "Test3");
        g.addEdge("Test3", "Test4");
        g.findAP();
        String points = "Test2 Test3 ";

        assertEquals(points, g.printAP());
        assertEquals(2, g.countAP());
    }

    @Test
    void brak_nazw_wierzchołki_1() throws Exception {
        Graph g = new Graph(4);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 3);

        Arrays.stream(g.getVerticesNames()).forEach(Assertions::assertNull);
    }

    @Test
    void brak_nazw_wierzchołki_2() throws Exception {
        Graph g = new Graph(4);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.addNameForVertice(2, "Test");

        assertNotNull(g.getVerticesNames()[2]);
        assertNull(g.getVerticesNames()[1]);

    }
}