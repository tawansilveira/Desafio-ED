class Aresta {
    private String destino;
    private int distancia;
    private int custo;

    public Aresta(String destino, int distancia, int custo) {
        this.destino = destino;
        this.distancia = distancia;
        this.custo = custo;
    }

    public String getDestino() {
        return destino;
    }

    public int getDistancia() {
        return distancia;
    }

    public int getCusto() {
        return custo;
    }
}