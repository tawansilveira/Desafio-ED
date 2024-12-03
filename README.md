# Grafo de Cidades e Buscas de Rota

Este projeto implementa um sistema de grafo para representar cidades e as estradas entre elas. Ele permite adicionar cidades, estradas, bloquear/desbloquear estradas e encontrar as melhores rotas entre as cidades. O foco principal deste código é encontrar rotas otimizadas considerando distância e custo, com a capacidade de bloquear estradas.

## Funcionalidades

- **Adição de Cidades**: Permite adicionar novas cidades ao grafo.
- **Adição de Estradas**: Adiciona estradas bidimensionais (não direcionadas) entre duas cidades com distância e custo especificados.
- **Remoção de Estradas**: Permite remover estradas entre duas cidades.
- **Bloqueio e Desbloqueio de Estradas**: Permite bloquear estradas para que elas não sejam consideradas na busca por rotas, e desbloquear quando necessário.
- **Busca pela Melhor Rota**: Encontrar a melhor rota entre duas cidades, considerando distância e custo, além de respeitar um limite de custo máximo.
- **Alternativa Sem Bloqueios**: Buscar o caminho mais curto entre duas cidades, ignorando as estradas bloqueadas.

## Classes Principais

### Classe `Aresta`
Representa uma estrada entre duas cidades.

- **Atributos**:
  - `destino`: Nome da cidade de destino.
  - `distancia`: Distância entre as duas cidades (em quilômetros).
  - `custo`: Custo de transporte entre as cidades (em R$).

- **Métodos**:
  - `getDestino()`: Retorna o nome da cidade de destino.
  - `getDistancia()`: Retorna a distância da estrada.
  - `getCusto()`: Retorna o custo da estrada.

### Classe `Grafo`
Representa o grafo que conecta as cidades com estradas.

- **Atributos**:
  - `adjList`: Estrutura de dados que mapeia cada cidade para as suas estradas conectadas.
  - `estradasBloqueadas`: Conjunto de estradas bloqueadas que não podem ser usadas nas buscas.

- **Métodos**:
  - `adicionarCidade(String cidade)`: Adiciona uma nova cidade ao grafo.
  - `adicionarEstrada(String cidade1, String cidade2, int distancia, int custo)`: Adiciona uma estrada entre duas cidades.
  - `removerEstrada(String cidade1, String cidade2)`: Remove uma estrada entre duas cidades.
  - `bloquearEstrada(String cidade1, String cidade2)`: Bloqueia uma estrada entre duas cidades.
  - `retirarBloqueioEstrada(String cidade1, String cidade2)`: Retira o bloqueio de uma estrada entre duas cidades.
  - `estradaBloqueada(String cidade1, String cidade2)`: Verifica se uma estrada entre duas cidades está bloqueada.
  - `getEstradas(String cidade)`: Retorna todas as estradas conectadas a uma cidade.
  - `cidadeExiste(String cidade)`: Verifica se uma cidade existe no grafo.

### Classe `Busca`

A classe **`Busca`** é responsável por implementar o algoritmo que encontra a **melhor rota** entre duas cidades, considerando:
1. **Distância total acumulada**.
2. **Custo total acumulado**, se houver um limite de custo especificado.
3. **Estradas bloqueadas**, que são ignoradas na busca.

#### Objetivo da Classe `Busca`
O principal objetivo dessa classe é encontrar o **caminho mais curto** (em termos de distância) entre duas cidades, considerando a possibilidade de:
- Respeitar um limite de custo máximo, se necessário.
- Ignorar estradas bloqueadas, caso existam.

A busca é realizada utilizando uma **versão modificada do algoritmo de Dijkstra**. O algoritmo utiliza uma **fila de prioridade** para sempre explorar a cidade mais próxima (em termos de distância) e, a partir dessa cidade, explora suas conexões para encontrar o caminho mais curto até o destino.

#### Principais Métodos

- **`encontrarMelhorRota(Grafo grafo, String partida, String destino, int custoMaximo)`**:
  - Este método encontra o **melhor caminho considerando o custo máximo** permitido.
  - Chama o método interno `executar` com o parâmetro `custoMaximo` para garantir que o custo total não ultrapasse o limite especificado.

- **`encontrarMelhorRotaSemBloqueios(Grafo grafo, String partida, String destino)`**:
  - Este método encontra o **caminho mais curto sem considerar restrições de custo**.
  - Utiliza o método `executar` com `INFINITO` como valor para o custo máximo, efetivamente ignorando o custo nas verificações.

- **`executar(Grafo grafo, String partida, String destino, int custoMaximo, boolean considerarCusto)`**:
  - Este é o **método principal** que realiza a busca. Ele explora as estradas do grafo e atualiza as distâncias e custos das cidades à medida que encontra rotas mais curtas.
  - **Passos**:
    1. Inicializa as distâncias e custos das cidades.
    2. Coloca a cidade de partida na fila de prioridade.
    3. Enquanto a fila não estiver vazia, explora a cidade com a menor distância.
    4. Para cada estrada conectada à cidade atual, calcula a **nova distância** e o **novo custo**, e atualiza os valores se uma rota mais curta for encontrada.
    5. Se o custo for respeitado (caso seja necessário), a cidade de destino é atualizada e adicionada à fila.
    6. Após o processamento, o caminho é reconstruído utilizando o mapa `anteriores` que contém a sequência de cidades percorridas.

    - **Reconstrução do Caminho**:
      - Ao final da busca, o algoritmo reconstruirá o caminho mais curto de volta ao ponto de partida utilizando o mapa `anteriores`, que guarda a cidade anterior em cada cidade no caminho.
      - O caminho é retornado na ordem correta após ser invertido.
     
## Funcionamento do Algoritmo

### 1. Inicialização

Antes de iniciar a busca, o algoritmo realiza as seguintes configurações:

- **Distâncias mínimas conhecidas**:
  - Todas as cidades começam com distância infinita, exceto a cidade de partida, que tem distância 0.
  - Exemplo: `distancias[A] = 0`, `distancias[B] = INFINITO`, `distancias[C] = INFINITO`.

- **Custos acumulados**:
  - Todas as cidades começam com custo acumulado infinito, exceto a cidade de partida, que tem custo 0.

- **Mapeamento do caminho (anteriores)**:
  - Um mapa vazio para rastrear de onde viemos ao chegar em uma cidade.

- **Fila de prioridade**:
  - A fila começa com a cidade de partida (A) e é ordenada pela menor distância acumulada.

### 2. Exploração

O algoritmo explora o grafo iterativamente, seguindo os seguintes passos:

1. **Pega a cidade com a menor distância acumulada na fila de prioridade**:
   - Exemplo: Começa com `A`, porque `distancias[A] = 0`.

2. **Para cada estrada conectada à cidade atual**:
   - Calcula a nova distância acumulada para a cidade de destino através da estrada atual:
     ```
     nova_distancia = distancias[atual] + distancia_da_estrada
     ```
   - Calcula o novo custo acumulado:
     ```
     novo_custo = custos[atual] + custo_da_estrada
     ```

3. **Verifica as condições de atualização**:
   - Atualiza a menor distância se:
     - A nova distância for menor que a registrada anteriormente:
       ```
       nova_distancia < distancias[destino]
       ```
     - O novo custo não exceder o custo máximo (se estiver sendo considerado):
       ```
       novo_custo ≤ custoMaximo
       ```

4. **Atualizações em caso positivo**:
   - Atualiza as tabelas:
     ```
     distancias[destino] = nova_distancia
     custos[destino] = novo_custo
     anteriores[destino] = atual  (para reconstruir o caminho)
     ```
   - Adiciona o destino atualizado à fila de prioridade.

5. **Repete o processo até**:
   - Todos os nós acessíveis terem sido processados.
   - A fila de prioridade estar vazia.

### 3. Reconstrução do Caminho

Após processar todas as cidades:

1. **Começa no destino e percorre o mapa `anteriores` para encontrar a origem**:
   - Exemplo: Se `anteriores[C] = B` e `anteriores[B] = A`, o caminho é `A -> B -> C`.
   
2. **O caminho é invertido para a ordem correta**.

3. **Se o destino não for alcançável**, retorna `null`.


#### Exemplo de Uso da Classe `Busca`

Para buscar a melhor rota entre duas cidades, considerando um custo máximo, você pode usar o método `encontrarMelhorRota` da seguinte forma:

```java
Grafo grafo = new Grafo();
grafo.adicionarCidade("A");
grafo.adicionarCidade("B");
grafo.adicionarCidade("C");
grafo.adicionarEstrada("A", "B", 10, 5);
grafo.adicionarEstrada("B", "C", 20, 15);
grafo.adicionarEstrada("A", "C", 35, 30);

List<String> rota = Busca.encontrarMelhorRota(grafo, "A", "C", 25);
System.out.println("Melhor Rota com Custo Máximo: " + rota);
