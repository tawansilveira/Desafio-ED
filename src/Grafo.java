import java.util.*;

class Grafo {
    Map<String, List<Aresta>> adjList;
    private Set<String> estradasBloqueadas;

    public Grafo() {
        adjList = new HashMap<>();
        estradasBloqueadas = new HashSet<>();
    }

    // Adiciona uma cidade ao grafo
    public void adicionarCidade(String cidade) {
        adjList.putIfAbsent(cidade, new ArrayList<>());
    }

    // Adiciona uma estrada entre duas cidades
    public void adicionarEstrada(String cidade1, String cidade2, int distancia, int custo) {
        adjList.putIfAbsent(cidade1, new ArrayList<>());
        adjList.putIfAbsent(cidade2, new ArrayList<>());
        adjList.get(cidade1).add(new Aresta(cidade2, distancia, custo));
        adjList.get(cidade2).add(new Aresta(cidade1, distancia, custo)); // Grafo não direcionado
    }

    // Remove uma estrada entre duas cidades
    public void removerEstrada(String cidade1, String cidade2) {
        List<Aresta> lista1 = adjList.get(cidade1);
        List<Aresta> lista2 = adjList.get(cidade2);
        if (lista1 != null) {
            lista1.removeIf(aresta -> aresta.getDestino().equals(cidade2));
        }
        if (lista2 != null) {
            lista2.removeIf(aresta -> aresta.getDestino().equals(cidade1));
        }
    }

    // Bloqueia uma estrada entre duas cidades
    public void bloquearEstrada(String cidade1, String cidade2) {
        estradasBloqueadas.add(cidade1 + "-" + cidade2);
        estradasBloqueadas.add(cidade2 + "-" + cidade1); // Bloqueia também no sentido reverso
    }

    // Retira o bloqueio de uma estrada entre duas cidades
    public void retirarBloqueioEstrada(String cidade1, String cidade2) {
        estradasBloqueadas.remove(cidade1 + "-" + cidade2);
        estradasBloqueadas.remove(cidade2 + "-" + cidade1); // Retira bloqueio no sentido reverso
    }

    // Verifica se uma estrada está bloqueada
    public boolean estradaBloqueada(String cidade1, String cidade2) {
        return estradasBloqueadas.contains(cidade1 + "-" + cidade2) || estradasBloqueadas.contains(cidade2 + "-" + cidade1);
    }

    // Obtém as estradas conectadas a uma cidade
    public List<Aresta> getEstradas(String cidade) {
        return adjList.getOrDefault(cidade, new ArrayList<>());
    }

    // Verifica se uma cidade existe no grafo
    public boolean cidadeExiste(String cidade) {
        return adjList.containsKey(cidade);
    }
}