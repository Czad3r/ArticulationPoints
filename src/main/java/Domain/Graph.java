package Domain;

import java.util.LinkedList;


public class Graph {
    private int V; // Liczba wierzchołków
    private boolean ap[]; //Punkty artykulacji
    private String[] verticesNames;

    // Tablica list dla reprezentacji list sąsiedztwa
    private LinkedList<Integer> adj[];
    private int time = 0;
    static final int NIL = -1;

    public void sąsiedzi(int wierzch){
        adj[wierzch].forEach(System.out::println);
    }

    public Graph(int v) {
        V = v;
        adj = new LinkedList[v];
        verticesNames = new String[V];
        ap=new boolean[V];
        for (int i = 0; i < v; ++i)
            adj[i] = new LinkedList();
    }

    public void addEdge(int v, int w) throws Exception {
        if (!(w >= 0 && w < V))throw new Exception("Dla wierzchołka : "+v+" podano nieistniejącego sąsiada ="+w);

        if(!adj[v].contains(w))adj[v].add(w); // Dodaj w do listy v
        if(!adj[w].contains(v))adj[w].add(v); //Dodaj v do listy w
    }

    public void addEdge(String first, String second) {
        int v = -1, w = -1;
        for (int i = 0; i < V; i++) {
            if (verticesNames[i].equals(first)) v = i;
            if (verticesNames[i].equals(second)) w = i;
        }
        if (v != -1 && w != -1) {
            adj[v].add(w); // Dodaj w do listy v
            adj[w].add(v); //Dodaj v do listy w
        }
    }

    //Wierzchołki numerowane od zera
    public void addNameForVertice(int v, String name) throws Exception {
        if (name != null && v >= 0 && v < V)
            verticesNames[v] = name;
        else throw new Exception("Brak nazwy lub wierzchołek spoza zakresu! v=" + v);
    }

    // A recursive function that find articulation points using findAP
    // u --> The vertex to be visited next
    // visited[] --> keeps tract of visited vertices
    // disc[] --> Stores discovery times of visited vertices
    // parent[] --> Stores parent vertices in findAP tree
    // ap[] --> Store articulation points
    void APUtil(int u, boolean visited[], int disc[],
                int low[], int parent[], boolean ap[]) {

        //Licznik dzieci w drzewie findAP
        int children = 0;

        //Uznaczanie aktualnego wierchułka jako odwiedzony
        visited[u] = true;

        //inicjalizacja przebytej drogi oraz funkcji low
        disc[u] = low[u] = ++time;

        //Przejście przez wszystkie wierzchołki sąsiednie do aktualnego
        for (int v : adj[u]) {
            //Jeśli v nie jest odwiedzone, to staje się dzieckiem u, wykonuje rekursywnie
            if (!visited[v]) {
                children++;
                parent[v] = u;
                APUtil(v, visited, disc, low, parent, ap);

                //Sprawdź czy poddrzewo zakorzenione w v ma połączenie do jednego z przodków u
                low[u] = Math.min(low[u], low[v]);

                //u jest punktem artykulacji w poniższych przypadkach:

                // 1. u jest korzeniem drzewa findAP i ma dwoje lub więcej dzieci
                if (parent[u] == NIL && children > 1)
                    ap[u] = true;

                // 2. Jeśli u nie jest korzeniem i funkcja low jednego z jej dzieci jest większa od przebytej odległości u(disc[])
                if (parent[u] != NIL && low[v] >= disc[u])
                    ap[u] = true;
            }

            //Aktualizuj wartość low dla u
            else if (v != parent[u])
                low[u] = Math.min(low[u], disc[v]);
        }


    }

    //Funkcja findAP używa rekursywnie APUtil
    public boolean[] findAP() {
        //Oznacz wszystkie wierzchołki jako nieodwiedzone
        boolean visited[] = new boolean[V];
        int disc[] = new int[V];
        int low[] = new int[V];
        int parent[] = new int[V];

        //Inicjalizacja tablic rodziców,odwiedzin oraz punktów artykulacji
        for (int i = 0; i < V; i++) {
            parent[i] = NIL;
            visited[i] = false;
            ap[i] = false;
        }

        //Wywoływanie rekursywnej metody do znalezienia punktów artykulacji w drzewie o korzeniu i
        for (int i = 0; i < V; i++)
            if (!visited[i])
                APUtil(i, visited, disc, low, parent, ap);

        return ap;
    }

    public int countAP() {
        int count = 0;
        for (boolean point : ap) {
            if (point) count++;
        }
        return count;
    }

    public String printAP() {
        String points = "";
        for (int i = 0; i < V; i++)
            if (ap[i]) {
                if (verticesNames[i]==null||verticesNames[i].equals("")) {
                    points += i + " ";
                    System.out.print(i + " ");
                } else {
                    points += verticesNames[i] + " ";
                    System.out.print(verticesNames[i] + " ");
                }
            }
        return points;
    }

    public boolean[] getAp() {
        return ap;
    }

    public String[] getVerticesNames() {
        return verticesNames;
    }

    public LinkedList<Integer>[] getAdj() {
        return adj;
    }
}
