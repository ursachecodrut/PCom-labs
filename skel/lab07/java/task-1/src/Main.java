import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Queue;

public class Main {
    static class Task {
        public static final String INPUT_FILE = "in";
        public static final String OUTPUT_FILE = "out";

        // numarul maxim de noduri
        public static final int NMAX = (int) 1e5 + 5; // 10^5 + 5 = 100.005

        public static final int INF = (int) 1e9;

        // n = numar de noduri, m = numar de muchii/arce
        int n, m;

        // adj[node] = lista de adiacenta a nodului node
        // exemplu: daca adj[node] = {..., neigh, ...} => exista muchia (node, neigh)
        @SuppressWarnings("unchecked")
        ArrayList<Integer> adj[] = new ArrayList[NMAX];

        // nodul sursa in parcurgerea BFS
        int source;

        public void solve() {
            readInput();
            writeOutput(getResult());
        }

        private void readInput() {

            try {
                Scanner sc = new Scanner(new BufferedReader(new FileReader(
                        INPUT_FILE)));
                n = sc.nextInt();
                m = sc.nextInt();
                source = sc.nextInt();

                for (int node = 1; node <= n; node++) {
                    adj[node] = new ArrayList<>();
                }

                for (int i = 1, x, y; i <= m; i++) {
                    // muchie (x, y)
                    x = sc.nextInt();
                    y = sc.nextInt();
                    adj[x].add(y);
                    adj[y].add(x);
                }

                sc.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void writeOutput(int[] d) {
            try {
                PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
                        OUTPUT_FILE)));
                for (int node = 1; node <= n; node++) {
                    pw.printf("%d%c", d[node], node == n ? '\n' : ' ');
                }
                pw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        int[] BFS(int source, ArrayList<Integer> adj[]) {
            int d[] = new int[n + 1];
            int p[] = new int[n + 1];
            // fill d with INF values
            Arrays.fill(d, INF);
            Arrays.fill(p, -1);

            // init queue
            Queue<Integer> queue = new LinkedList<Integer>();

            d[source] = 0;
            p[source] = -1;
            queue.add(source);

            while (!queue.isEmpty()) {
                int node = queue.poll();
                for (Integer neigh : adj[node]) {
                    if (d[node] + 1 < d[neigh]) {
                        d[neigh] = d[node] + 1;
                        p[neigh] = node;
                        queue.add(neigh);
                    }
                }
            }

            for (int node = 1; node <= n; node++) {
                if (d[node] == INF) {
                    d[node] = -1;
                }
            }

            return d;
        }

        private int[] getResult() {
            // TODO: Faceti un BFS care sa construiasca in d valorile:
            // * d[node] = numarul minim de muchii de parcurs de la nodul `source` la nodul
            // `node`
            // * d[source] = 0
            // * d[node] = -1, daca nu exista drum de la source la node
            // *******
            // ATENTIE: nodurile sunt indexate de la 1 la n.
            // *******

            return BFS(source, adj);
        }
    }

    public static void main(String[] args) {
        new Task().solve();
    }
}
