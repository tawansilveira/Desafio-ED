import java.util.*;

public class Busca {
    private static final int INFINITO = Integer.MAX_VALUE;

    public static List<String> encontrarMelhorRota(Grafo grafo, String partida, String destino, int custoMaximo) {
        return executar(grafo, partida, destino, custoMaximo, true);
    }

    public static List<String> encontrarMelhorRotaSemBloqueios(Grafo grafo, String partida, String destino) {
        return executar(grafo, partida, destino, INFINITO, false);
    }

    private static List<String> executar(Grafo grafo, String partida, String destino, int custoMaximo, boolean considerarCusto) {
        Map<String, Integer> distancias = new HashMap<>();
        Map<String, Integer> custos = new HashMap<>();
        Map<String, String> anteriores = new HashMap<>();
        PriorityQueue<String> fila = new PriorityQueue<>(Comparator.comparingInt(distancias::get));

        for (String cidade : grafo.adjList.keySet()) {
            distancias.put(cidade, INFINITO);
            custos.put(cidade, INFINITO);
        }
        distancias.put(partida, 0);
        custos.put(partida, 0);
        fila.add(partida);

        while (!fila.isEmpty()) {
            String atual = fila.poll();

            for (Aresta aresta : grafo.getEstradas(atual)) {
                if (grafo.estradaBloqueada(atual, aresta.getDestino())) continue;

                int novaDistancia = distancias.get(atual) + aresta.getDistancia();
                int novoCusto = custos.get(atual) + aresta.getCusto();
                if (novaDistancia < distancias.get(aresta.getDestino()) && (considerarCusto ? novoCusto <= custoMaximo : true)) {
                    distancias.put(aresta.getDestino(), novaDistancia);
                    custos.put(aresta.getDestino(), novoCusto);
                    anteriores.put(aresta.getDestino(), atual);
                    fila.add(aresta.getDestino());
                }
            }
        }

        // Reconstrução do caminho
        List<String> caminho = new ArrayList<>();
        for (String at = destino; at != null; at = anteriores.get(at)) {
            caminho.add(at);
        }
        Collections.reverse(caminho);
        return caminho.size() == 1 ? null : caminho;
    }
}
