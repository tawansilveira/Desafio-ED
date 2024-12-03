import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Grafo grafo = new Grafo();

        // Adicionando cidades
        String[] cidades = {"A", "B", "C", "D", "E"};
        for (String cidade : cidades) {
            grafo.adicionarCidade(cidade);
        }

        // Adicionando estradas
        grafo.adicionarEstrada("A", "B", 10, 5);
        grafo.adicionarEstrada("B", "C", 20, 15);
        grafo.adicionarEstrada("C", "E", 25, 10);
        grafo.adicionarEstrada("B", "D", 30, 20);
        grafo.adicionarEstrada("A", "E", 50, 40);

        System.out.println("Cidades e estradas inicializadas no grafo.");


                // Menu de interação com o usuário
                while (true) {
                    System.out.println("\n--- Menu Principal ---");
                    System.out.println("1. Adicionar Cidade");
                    System.out.println("2. Adicionar Estrada");
                    System.out.println("3. Remover Estrada");
                    System.out.println("4. Buscar Melhor Rota (considerando custo máximo)");
                    System.out.println("5. Bloquear Estrada");
                    System.out.println("6. Retirar Bloqueio de Estrada");
                    System.out.println("7. Sair");
                    System.out.print("Escolha uma opção: ");
                    int opcao = scanner.nextInt();
                    scanner.nextLine(); // Consumir o newline após o número da opção

                    switch (opcao) {
                        case 1:
                            System.out.print("Informe o nome da cidade: ");
                            String cidade = scanner.nextLine();
                            grafo.adicionarCidade(cidade);
                            System.out.println("Cidade " + cidade + " adicionada.");
                            break;

                        case 2:
                            System.out.print("Informe a cidade 1: ");
                            String cidade1 = scanner.nextLine();
                            System.out.print("Informe a cidade 2: ");
                            String cidade2 = scanner.nextLine();
                            if (!grafo.cidadeExiste(cidade1) || !grafo.cidadeExiste(cidade2)) {
                                System.out.println("Uma ou ambas as cidades não existem. Adicione as cidades primeiro.");
                                break;
                            }
                            System.out.print("Informe a distância entre " + cidade1 + " e " + cidade2 + " (em km): ");
                            int distancia = scanner.nextInt();
                            System.out.print("Informe o custo de transporte entre " + cidade1 + " e " + cidade2 + " (em R$): ");
                            int custo = scanner.nextInt();
                            grafo.adicionarEstrada(cidade1, cidade2, distancia, custo);
                            System.out.println("Estrada entre " + cidade1 + " e " + cidade2 + " adicionada.");
                            break;

                        case 3:
                            System.out.print("Informe a cidade 1 para remover a estrada: ");
                            cidade1 = scanner.nextLine();
                            System.out.print("Informe a cidade 2 para remover a estrada: ");
                            cidade2 = scanner.nextLine();
                            grafo.removerEstrada(cidade1, cidade2);
                            System.out.println("Estrada entre " + cidade1 + " e " + cidade2 + " removida.");
                            break;

                        case 4:
                            System.out.print("Informe a cidade de partida: ");
                            String partida = scanner.nextLine();
                            System.out.print("Informe a cidade de destino: ");
                            String destino = scanner.nextLine();
                            System.out.print("Informe o custo máximo permitido: ");
                            int custoMaximo = scanner.nextInt();
                            scanner.nextLine(); // Consumir o newline após o número
                            List<String> rota = Busca.encontrarMelhorRota(grafo, partida, destino, custoMaximo);
                            if (rota != null) {
                                System.out.println("Rota ótima dentro do custo máximo: " + rota);
                            } else {
                                System.out.println("Não há rota viável dentro do custo máximo.");
                                rota = Busca.encontrarMelhorRotaSemBloqueios(grafo, partida, destino);
                                System.out.println("Rota alternativa mais curta: " + rota);
                            }
                            break;

                        case 5:
                            System.out.print("Informe a cidade 1 da estrada a ser bloqueada: ");
                            cidade1 = scanner.nextLine();
                            System.out.print("Informe a cidade 2 da estrada a ser bloqueada: ");
                            cidade2 = scanner.nextLine();
                            grafo.bloquearEstrada(cidade1, cidade2);
                            System.out.println("Estrada entre " + cidade1 + " e " + cidade2 + " bloqueada.");
                            break;

                        case 6:
                            System.out.print("Informe a cidade 1 da estrada a ser desbloqueada: ");
                            cidade1 = scanner.nextLine();
                            System.out.print("Informe a cidade 2 da estrada a ser desbloqueada: ");
                            cidade2 = scanner.nextLine();
                            grafo.retirarBloqueioEstrada(cidade1, cidade2);
                            System.out.println("Estrada entre " + cidade1 + " e " + cidade2 + " desbloqueada.");
                            break;

                        case 7:
                            System.out.println("Saindo...");
                            return;

                        default:
                            System.out.println("Opção inválida. Tente novamente.");
                            break;
                    }
                }
            }
        }
