Grafo de Cidades e Buscas de Rota

Este projeto implementa um sistema de grafo para representar cidades e as estradas entre elas. Ele permite adicionar cidades, estradas, bloquear/desbloquear estradas e encontrar as melhores rotas entre as cidades. O foco principal deste código é encontrar rotas otimizadas considerando distância e custo, com a capacidade de bloquear estradas.
Funcionalidades

    Adição de Cidades: Permite adicionar novas cidades ao grafo.
    Adição de Estradas: Adiciona estradas bidimensionais (não direcionadas) entre duas cidades com distância e custo especificados.
    Remoção de Estradas: Permite remover estradas entre duas cidades.
    Bloqueio e Desbloqueio de Estradas: Permite bloquear estradas para que elas não sejam consideradas na busca por rotas, e desbloquear quando necessário.
    Busca pela Melhor Rota: Encontrar a melhor rota entre duas cidades, considerando distância e custo, além de respeitar um limite de custo máximo.
    Alternativa Sem Bloqueios: Buscar o caminho mais curto entre duas cidades, ignorando as estradas bloqueadas.

Classes Principais
Classe Aresta

Representa uma estrada entre duas cidades.

    Atributos:
        destino: Nome da cidade de destino.
        distancia: Distância entre as duas cidades (em quilômetros).
        custo: Custo de transporte entre as cidades (em R$).

    Métodos:
        getDestino(): Retorna o nome da cidade de destino.
        getDistancia(): Retorna a distância da estrada.
        getCusto(): Retorna o custo da estrada.

Classe Grafo

Representa o grafo que conecta as cidades com estradas.

    Atributos:
        adjList: Estrutura de dados que mapeia cada cidade para as suas estradas conectadas.
        estradasBloqueadas: Conjunto de estradas bloqueadas que não podem ser usadas nas buscas.

    Métodos:
        adicionarCidade(String cidade): Adiciona uma nova cidade ao grafo.
        adicionarEstrada(String cidade1, String cidade2, int distancia, int custo): Adiciona uma estrada entre duas cidades.
        removerEstrada(String cidade1, String cidade2): Remove uma estrada entre duas cidades.
        bloquearEstrada(String cidade1, String cidade2): Bloqueia uma estrada entre duas cidades.
        retirarBloqueioEstrada(String cidade1, String cidade2): Retira o bloqueio de uma estrada entre duas cidades.
        estradaBloqueada(String cidade1, String cidade2): Verifica se uma estrada entre duas cidades está bloqueada.
        getEstradas(String cidade): Retorna todas as estradas conectadas a uma cidade.
        cidadeExiste(String cidade): Verifica se uma cidade existe no grafo.

Classe Busca

A classe Busca é responsável por implementar o algoritmo que encontra a melhor rota entre duas cidades, considerando:

    Distância total acumulada.
    Custo total acumulado, se houver um limite de custo especificado.
    Estradas bloqueadas, que são ignoradas na busca.

Objetivo da Classe Busca

O principal objetivo dessa classe é encontrar o caminho mais curto (em termos de distância) entre duas cidades, considerando a possibilidade de:

    Respeitar um limite de custo máximo, se necessário.
    Ignorar estradas bloqueadas, caso existam.

A busca é realizada utilizando uma versão modificada do algoritmo de Dijkstra. O algoritmo utiliza uma fila de prioridade para sempre explorar a cidade mais próxima (em termos de distância) e, a partir dessa cidade, explora suas conexões para encontrar o caminho mais curto até o destino.
Principais Métodos

    encontrarMelhorRota(Grafo grafo, String partida, String destino, int custoMaximo):
        Este método encontra o melhor caminho considerando o custo máximo permitido.
        Chama o método interno executar com o parâmetro custoMaximo para garantir que o custo total não ultrapasse o limite especificado.

    encontrarMelhorRotaSemBloqueios(Grafo grafo, String partida, String destino):
        Este método encontra o caminho mais curto sem considerar restrições de custo.
        Utiliza o método executar com INFINITO como valor para o custo máximo, efetivamente ignorando o custo nas verificações.

    executar(Grafo grafo, String partida, String destino, int custoMaximo, boolean considerarCusto):
        Este é o método principal que realiza a busca. Ele explora as estradas do grafo e atualiza as distâncias e custos das cidades à medida que encontra rotas mais curtas.
        Passos:

            Inicializa as distâncias e custos das cidades.

            Coloca a cidade de partida na fila de prioridade.

            Enquanto a fila não estiver vazia, explora a cidade com a menor distância.

            Para cada estrada conectada à cidade atual, calcula a nova distância e o novo custo, e atualiza os valores se uma rota mais curta for encontrada.

            Se o custo for respeitado (caso seja necessário), a cidade de destino é atualizada e adicionada à fila.

            Após o processamento, o caminho é reconstruído utilizando o mapa anteriores que contém a sequência de cidades percorridas.
            Reconstrução do Caminho:
                Ao final da busca, o algoritmo reconstruirá o caminho mais curto de volta ao ponto de partida utilizando o mapa anteriores, que guarda a cidade anterior em cada cidade no caminho.
                O caminho é retornado na ordem correta após ser invertido.

Exemplo de Uso da Classe Busca

Para buscar a melhor rota entre duas cidades, considerando um custo máximo, você pode usar o método encontrarMelhorRota da seguinte forma:

Grafo grafo = new Grafo();
grafo.adicionarCidade("A");
grafo.adicionarCidade("B");
grafo.adicionarCidade("C");
grafo.adicionarEstrada("A", "B", 10, 5);
grafo.adicionarEstrada("B", "C", 20, 15);
grafo.adicionarEstrada("A", "C", 35, 30);

List<String> rota = Busca.encontrarMelhorRota(grafo, "A", "C", 25);
System.out.println("Melhor Rota com Custo Máximo: " + rota);

Se o custo máximo for atingido, o algoritmo pode retornar o melhor caminho possível dentro do custo ou sugerir a melhor rota sem considerar o custo, utilizando o método encontrarMelhorRotaSemBloqueios.
Como Rodar o Projeto
Requisitos

    Java 8 ou superior.

Passos para execução:

    Clone o repositório ou faça o download do código-fonte.
    Compile e execute o arquivo Main.java, que contém a interface de interação com o usuário.
    No menu interativo, você pode adicionar cidades, estradas, bloquear estradas e realizar buscas de rotas.
