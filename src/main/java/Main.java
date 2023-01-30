import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import br.com.davesmartins.api.Graph;

public class Main {

	public static void main(String[] args) throws IOException {

		Scanner leitura = new Scanner(System.in);
		Grafo grafo = new Grafo(false);

		System.out.println("###### Menu Principal");
		System.out.println("## 1 - Criar grafo");
		System.out.println("## 2 - Abrir grafo");
		System.out.println("## 3 - Sair");
		System.out.println();

		int op = leitura.nextInt();

		switch (op) {

		case 1:
			menu_1();
			int menu1 = leitura.nextInt();
			switch (menu1) {
			case 1:
				System.out.println("###### Escolha uma opção:\n## 1 - Orientado \n## 2 - Não Orientado");
				int orientado = leitura.nextInt();
				if (orientado == 1) {
					grafo.orientaGrafo();
				}

				while (true) {
					menu_2(leitura);
					int menu2 = leitura.nextInt();

					criar_grafo_valorado(leitura, grafo, menu2);

				}

			case 2:
				System.out.println("###### Escolha uma opção:\n## 1 - Orientado \n## 2 - Não Orientado");
				int orientado1 = leitura.nextInt();
				if (orientado1 == 1) {
					grafo.orientaGrafo();

				}
				while (true) {
					menu_2(leitura);
					int menu2 = leitura.nextInt();

					criar_grafo_naoValorado(leitura, grafo, menu2);

				}

			case 3:
				break;

			}

		case 2:

			grafo = case_2_menu_principal(leitura, grafo);
			boolean valorado = false;

			for (int i = 0; i < grafo.getArestas().size(); i++) {
				if ((grafo.getArestas().get(i)).getPeso() != 1) {
					valorado = true;
				}
			}

			if (valorado) {

				while (true) {
					menu_2(leitura);
					int menu2 = leitura.nextInt();

					criar_grafo_valorado(leitura, grafo, menu2);

					if (menu2 == 8) {
						break;
					}
				}

			} else {
				while (true) {
					menu_2(leitura);
					int menu2 = leitura.nextInt();

					criar_grafo_naoValorado(leitura, grafo, menu2);

					if (menu2 == 8) {
						break;
					}
				}

			}

		case 3:
			break;
		}

	}

	private static Grafo case_2_menu_principal(Scanner leitura, Grafo g) {
		System.out.println("\nInforme o nome do arquivo:");
		String ler = leitura.next();

		File grafo1 = new File("DOTS/" + ler + ".dot");

		try {
			if (!grafo1.exists()) {
				System.out.println("Arquivo solicitado não existe!");
			} else {

				FileReader fr = new FileReader(grafo1);
				BufferedReader br = new BufferedReader(fr);

				while (br.ready()) {
					String linha = br.readLine();

					String[] textoSeparado = linha.split(" ");

					if (linha.contains(";") && (!linha.contains("--") || !linha.contains("->"))) {
						g.addVertice(textoSeparado[0].trim().replaceAll(";", ""));
					}

					if (linha.contains("graph")) {
						g.setOrientacao(false);
					} else if (linha.contains("digraph")) {
						g.setOrientacao(true);
					}

					if (linha.contains("--")) {
						g.setOrientacao(false);

						g.addVertice(textoSeparado[0]);
						g.addVertice(textoSeparado[2]);

						if (linha.contains("label")) {
							String[] aux = textoSeparado[3].split("=");
							String aux2 = aux[1].replaceAll("\"", "").replaceAll("]", "").replaceAll(";", "")
									.replaceAll(".0", "");
							g.incluirAresta(Integer.parseInt(aux2), textoSeparado[0], textoSeparado[2]);

						} else {
							g.incluirAresta(1, textoSeparado[0], textoSeparado[2]);
						}

					} else if (linha.contains("->")) {
						g.setOrientacao(true);
						g.addVertice(textoSeparado[0]);
						g.addVertice(textoSeparado[2]);

						if (linha.contains("label")) {
							String[] aux = textoSeparado[3].split("=");
							String aux2 = aux[1].replaceAll("\"", "").replaceAll("]", "").replaceAll(";", "")
									.replaceAll(".0", "");
							g.incluirAresta(Integer.parseInt(aux2), textoSeparado[0], textoSeparado[2]);

						} else {
							g.incluirAresta(1, textoSeparado[0], textoSeparado[2]);
						}
					}

				}

				br.close();
				fr.close();

			}
		} catch (Exception e) {
			System.out.println("Erro! " + e.getMessage());
			return null;

		}
		return g;
	}

	private static void criar_grafo_naoValorado(Scanner leitura, Grafo grafo, int menu2) throws IOException {
		if (menu2 == 1) {
			adiciona_Vertice(leitura, grafo);
		} else if (menu2 == 2) {
			System.out.println("Qual vértice deseja remover: ");
			String verticeRemov = leitura.next();
			grafo.removeVertice(verticeRemov);
			System.out.println("Vértice Removido");

		} else if (menu2 == 3) {
			if (grafo.getOrientacao()) {
				adiciona_aresta_naoValorada_orientada(leitura, grafo);
			} else {
				adiciona_aresta_naoValorada_naoOrientada(leitura, grafo);
			}

		} else if (menu2 == 4) {
			if (grafo.getOrientacao()) {
				System.out.println("Digite o vértice de origem da aresta que deseja remover:");
				String vOrig = leitura.next();
				System.out.println("Digite o vértice de destino da aresta que deseja remover:");
				String vDest = leitura.next();
				grafo.excluirAresta(grafo.getVertice(vOrig), grafo.getVertice(vDest));
				System.out.println("Aresta removida!");
			} else {
				System.out.println("Digite o primeiro vértice da aresta que deseja remover:");
				String vOrig = leitura.next();
				System.out.println("Digite o segundo vértice da aresta que deseja remover:");
				String vDest = leitura.next();
				grafo.excluirAresta(grafo.getVertice(vOrig), grafo.getVertice(vDest));
				System.out.println("Aresta Removida!!");

			}

		} else if (menu2 == 5) {
			System.out.println(
					"1 - imprimir Matriz Adjacencia \n2 - Imprimir Matriz Incidencia \n3 - Imprimir Lista Adjacencia");
			int menu3_imprimir = leitura.nextInt();

			if (menu3_imprimir == 1) {
				grafo.criaMatrizAdj();
				imprimeMatrizAdj(grafo.getMat());
			} else if (menu3_imprimir == 2) {
				grafo.criaMatrizInc();
				imprimeMatrizInc(grafo.getMat());
			} else if (menu3_imprimir == 3) {
				System.out.println(grafo.retornaListaAdj());
			}

		} else if (menu2 == 6) {
			menu_3();
			int menu3 = leitura.nextInt();
			while (true) {
				if (menu3 == 1) {
					System.out.println("A ordem do grafo é :" + grafo.getOrdemGrafo());
					System.out.println();
					break;
				} else if (menu3 == 2) {
					grau_do_vertice(leitura, grafo);
					System.out.println();
					break;

				} else if (menu3 == 3) {
					if (grafo.grafoSimples()) {
						System.out.println("O grafo é simples!");
						System.out.println();
						break;
					} else {
						System.out.println("O grafo não é simples!");
						System.out.println();
						break;
					}

				} else if (menu3 == 4) {
					if (grafo.verificaRegular()) {
						System.out.println("O grafo é regular!");
						System.out.println();
						break;
					} else {
						System.out.println("O grafo NÃO é regular!");
						System.out.println();
						break;
					}
				} else if (menu3 == 5) {
					if (grafo.verificaGrafoCompleto()) {
						System.out.println("O grafo é completo!");
						System.out.println();
						break;
					} else {
						System.out.println("O grafo NÃO é completo!");
						System.out.println();
						break;
					}

				} else if (menu3 == 6) {
					if (grafo.existeConexao()) {
						System.out.println("Grafo conexo!");
						System.out.println();
						break;
					} else {
						System.out.println("Grafo desconexo!");
						System.out.println();
						break;
					}
				} else if (menu3 == 7) {
					if (grafo.getOrientacao()) {
						System.out.println("Qual vértice deseja analisar o FTD e FTI :");
						String vrtc = leitura.next();
						Vertice v = grafo.getVertice(vrtc);
						System.out.printf("Fecho Transitivivo direto:");
						imprimeLista(grafo.fechoTransitivoDireto(v));
						System.out.println();
						System.out.printf("Fecho Transitivivo inverso:");
						imprimeLista(grafo.fechoTransitivoInverso(v));
						System.out.println();
						break;
					} else {
						System.out.println("Para FTD e FTI, o grafo deve ser orientado");
						System.out.println();
						break;
					}

				} else if (menu3 == 8) {
					System.out.println("Os vértices fonte são: ");
					imprimeLista(grafo.verticesFonte());
					System.out.println();
					break;

				} else if (menu3 == 9) {
					System.out.println("Os vértices sumidouro são: ");
					imprimeLista(grafo.verticesSumidouro());
					System.out.println();
					break;

				} else if (menu3 == 10) {
					break;
				}
			}
		} else if (menu2 == 7) {
			System.out.println(
					"1 - Existe Caminho \n2 - Caminho Mínimo (Dijkstra) \n3 - Árvore Geradora Mínima(Kruskal) \n4 - Grafo Reduzido (Malgrange)");
			int menuAlg = leitura.nextInt();
			if (menuAlg == 1) {
				System.out.println("Digite o primeiro vértice: ");
				String v1 = leitura.next();
				System.out.println("Digite o segundo vértice: ");
				String v2 = leitura.next();
				ArrayList<Vertice> marcado = new ArrayList<Vertice>();

				if (grafo.existeCaminho(grafo.getVertice(v1), grafo.getVertice(v2), marcado)) {

					System.out.println("Existe caminho!!!");
					System.out.println();
				} else {
					System.out.println("Não existe caminho!!!");
					System.out.println();
				}

			} else if (menuAlg == 2) {
				System.out.println("Qual o vértice de origem para calcular os caminhos mínimos?");
				String dijkstra = leitura.next();
				System.out.println("Caminhos Mínimos:");
				System.out.println(grafo.dijkstra(grafo.getVertice(dijkstra)));
				System.out.println();
			}

		} else if (menu2 == 8) {

			System.out.println("###### Escolha uma opção:");
			System.out.println("## 1 - Salvar grafo em disco");
			System.out.println("## 2 - Salvar imagem do grafo");

			int menuExportar = leitura.nextInt();
			if (menuExportar == 1) {
				File arquivo = new File("Files/arquivoEmDot.txt");
				try {
					if (!arquivo.exists()) {
						arquivo.createNewFile();
					}
				} catch (Exception e) {

				}

				String grafoEmDot = grafo.retornaDot();

				FileWriter fw = new FileWriter(arquivo, true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(grafoEmDot);

				System.out.println("Arquivo salvo!!");
				bw.close();
				fw.close();
			} else if (menuExportar == 2) {
				try {
					gera_imagem(grafo);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void criar_grafo_valorado(Scanner leitura, Grafo grafo, int menu2) throws IOException {
		if (menu2 == 1) {
			adiciona_Vertice(leitura, grafo);
		} else if (menu2 == 2) {
			System.out.println("Qual vértice deseja remover: ");
			String verticeRemov = leitura.next();
			grafo.removeVertice(verticeRemov);
			System.out.println("Vértice removido");
		} else if (menu2 == 3) {
			if (grafo.getOrientacao()) {
				adiciona_aresta_valorada_orientada(leitura, grafo);
			} else {
				adiciona_aresta_valorada_naoOrientada(leitura, grafo);
			}
		} else if (menu2 == 4) {
			if (grafo.getOrientacao()) {
				System.out.println("Digite o vértice de origem da aresta que deseja remover:");
				String vOrig = leitura.next();
				System.out.println("Digite o vértice de destino da aresta que deseja remover:");
				String vDest = leitura.next();
				grafo.excluirAresta(grafo.getVertice(vOrig), grafo.getVertice(vDest));
				System.out.println("Aresta Removida!!");
			} else {
				System.out.println("Digite o primeiro vértice da aresta que deseja remover:");
				String vOrig = leitura.next();
				System.out.println("Digite o segundo vértice da aresta que deseja remover:");
				String vDest = leitura.next();
				grafo.excluirAresta(grafo.getVertice(vOrig), grafo.getVertice(vDest));
				System.out.println("Aresta Removida!!");

			}

		} else if (menu2 == 5) {
			System.out.println("\n###### Escolha uma opção:");
			System.out.println(
					"## 1 - Imprimir matriz de adjacência \n## 2 - Imprimir matriz de incidência \n## 3 - Imprimir lista de adjacência");
			int menu3_imprimir = leitura.nextInt();

			if (menu3_imprimir == 1) {
				grafo.criaMatrizAdj();
				imprimeMatrizAdj(grafo.getMat());
			} else if (menu3_imprimir == 2) {
				grafo.criaMatrizInc();
				imprimeMatrizInc(grafo.getMat());
			} else if (menu3_imprimir == 3) {
				System.out.println(grafo.retornaListaAdj());
			}

		} else if (menu2 == 6) {
			menu_3();
			int menu3 = leitura.nextInt();
			while (true) {
				if (menu3 == 1) {
					System.out.println("A ordem do grafo é :" + grafo.getOrdemGrafo());
					System.out.println();
					break;

				} else if (menu3 == 2) {
					grau_do_vertice(leitura, grafo);
					System.out.println();
					break;
				} else if (menu3 == 3) {

					if (grafo.grafoSimples()) {
						System.out.println("O grafo é simples!");
						System.out.println();
						break;
					} else if (!grafo.grafoSimples()) {
						System.out.println("O grafo não é simples!");
						System.out.println();
						break;
					}

				} else if (menu3 == 4) {
					if (grafo.verificaRegular()) {
						System.out.println("O grafo é regular!");
						System.out.println();
						break;
					} else {
						System.out.println("O grafo NÃO é regular!");
						System.out.println();
						break;
					}
				} else if (menu3 == 5) {
					if (grafo.verificaGrafoCompleto()) {
						System.out.println("O grafo é completo!");
						System.out.println();
						break;
					} else {
						System.out.println("O grafo NÃO é completo!");
						System.out.println();
						break;
					}

				} else if (menu3 == 6) {
					if (grafo.existeConexao()) {
						System.out.println("Grafo conexo!");
						System.out.println();
						break;
					} else {
						System.out.println("Grafo desconexo!");
						System.out.println();
						break;
					}

				} else if (menu3 == 7) {
					if (grafo.getOrientacao()) {
						System.out.println("Qual vértice deseja analisar o FTD e FTI :");
						String vrtc = leitura.next();
						Vertice v = grafo.getVertice(vrtc);
						System.out.printf("Fecho Transitivo direto:");
						imprimeLista(grafo.fechoTransitivoDireto(v));
						System.out.println();
						System.out.printf("Fecho Transitivo inverso:");
						imprimeLista(grafo.fechoTransitivoInverso(v));
						System.out.println();
						break;
					} else {
						System.out.println("Para FTD e FTI, o grafo deve ser orientado");
						System.out.println();
						break;
					}

				} else if (menu3 == 8) {
					System.out.println("Os vértices fonte são: ");
					imprimeLista(grafo.verticesFonte());
					System.out.println();
					break;
				} else if (menu3 == 9) {
					System.out.println("Os vértices sumidouro são: ");
					imprimeLista(grafo.verticesSumidouro());
					System.out.println();
					break;

				} else if (menu3 == 10) {
					break;
				}
			}
		} else if (menu2 == 7) {
			System.out.println("\n###### Escolha uma opção:");
			System.out.println("## 1 - Existe Caminho \n## 2 - Caminho Mínimo (Dijkstra)");
			int menuAlg = leitura.nextInt();
			if (menuAlg == 1) {
				System.out.println("Digite o primeiro vértice: ");
				String v1 = leitura.next();
				System.out.println("Digite o segundo vértice: ");
				String v2 = leitura.next();
				ArrayList<Vertice> marcado = new ArrayList<Vertice>();

				if (grafo.existeCaminho(grafo.getVertice(v1), grafo.getVertice(v2), marcado)) {

					System.out.println("Existe caminho!!");
					System.out.println();
				} else {
					System.out.println("Não existe caminho!!");
					System.out.println();
				}
			} else if (menuAlg == 2) {
				System.out.println("Qual o vértice de origem para calcular os caminhos mínimos?");
				String dijkstra = leitura.next();
				System.out.println("Caminhos Mínimos:");
				System.out.println(grafo.dijkstra(grafo.getVertice(dijkstra)));
				System.out.println();
			}
		}

		else if (menu2 == 8) {

			System.out.println("###### Escolha uma opção:");
			System.out.println("## 1 - Salvar grafo em disco");
			System.out.println("## 2 - Salvar imagem do grafo");

			int menuExportar = leitura.nextInt();
			if (menuExportar == 1) {
				File arquivo = new File("Files/arquivoEmDot.txt");
				try {
					if (!arquivo.exists()) {
						arquivo.createNewFile();
					}
				} catch (Exception e) {

				}

				String grafoEmDot = grafo.retornaDot();

				FileWriter fw = new FileWriter(arquivo, true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(grafoEmDot);

				System.out.println("Arquivo salvo!!");
				bw.close();
				fw.close();
			} else if (menuExportar == 2) {
				try {
					gera_imagem(grafo);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void adiciona_aresta_naoValorada_orientada(Scanner leitura, Grafo grafo) {
		System.out.println("Digite o nome do vértice de origem da aresta: ");
		String nomeVerticeOrigem = leitura.next();
		System.out.println("Digite o nome do vértice destino da aresta: ");
		String nomeVerticeDestino = leitura.next();
		grafo.incluirAresta(1, nomeVerticeOrigem, nomeVerticeDestino);
		System.out.println("Aresta adicionada!");
	}

	private static void adiciona_aresta_naoValorada_naoOrientada(Scanner leitura, Grafo grafo) {
		System.out.println("Digite o nome do primeiro vértice da aresta: ");
		String nomeVerticeOrigem = leitura.next();
		System.out.println("Digite o nome do segundo vértice da aresta: ");
		String nomeVerticeDestino = leitura.next();
		grafo.incluirAresta(1, nomeVerticeOrigem, nomeVerticeDestino);
		System.out.println("Aresta adicionada!");
	}

	private static void adiciona_aresta_valorada_naoOrientada(Scanner leitura, Grafo grafo) {
		System.out.println("Digite o peso da aresta que deseja adicionar: ");
		int peso = leitura.nextInt();
		System.out.println("Digite o nome do primeiro vértice da aresta: ");
		String nomeVerticeOrigem = leitura.next();
		System.out.println("Digite o nome do segundo vértice da aresta: ");
		String nomeVerticeDestino = leitura.next();
		grafo.incluirAresta(peso, nomeVerticeOrigem, nomeVerticeDestino);
		System.out.println("Aresta adicionada!");
	}

	private static void gera_imagem(Grafo grafo) throws IOException {
		String grafoEmDot = grafo.retornaDot();
		Graph.createStringDotToPng(grafoEmDot, "Imagens/Grafo.png");
		System.out.println("Imagem do grafo gerada e salva em disco! ");
	}

	private static void grau_do_vertice(Scanner leitura, Grafo grafo) {
		System.out.println("Qual o nome do vértice que deseja saber o grau?");
		String NomeVertice = leitura.next();
		System.out.println("Esse vértice possui grau: " + (grafo.getGrau(grafo.getVertice(NomeVertice))));

	}

	private static void menu_3() {
		System.out.println("\n###### Escolha uma opção:");
		System.out.println("## 1 - Ordem do grafo");
		System.out.println("## 2 - Grau do vértice");
		System.out.println("## 3 - Grafo simples");
		System.out.println("## 4 - Grafo regular");
		System.out.println("## 5 - Grafo completo");
		System.out.println("## 6 - Grafo conexo ou desconexo");
		System.out.println("## 7 - Fecho Transitivo Direto e Inverso");
		System.out.println("## 8 - Vértices fontes");
		System.out.println("## 9 - Vértices sumidouros");
		System.out.println("## 10 - Sair");
	}

	private static void adiciona_aresta_valorada_orientada(Scanner leitura, Grafo grafo) {
		System.out.println("Digite o peso da aresta que deseja adicionar: ");
		int peso = leitura.nextInt();
		System.out.println("Digite o nome do vértice origem da aresta: ");
		String nomeVerticeOrigem = leitura.next();
		System.out.println("Digite o nome do vértice destino da aresta: ");
		String nomeVerticeDestino = leitura.next();
		grafo.incluirAresta(peso, nomeVerticeOrigem, nomeVerticeDestino);
		System.out.println("Aresta adicionada!");
	}

	private static void adiciona_Vertice(Scanner leitura, Grafo grafo) {
		System.out.println("Digite o nome do vértice que deseja adicionar: ");
		String nome = leitura.next();
		grafo.addVertice(nome);
		System.out.println("\nVértice adicionado!\n");
	}

	private static void menu_1() {
		System.out.println("\n###### Escolha uma opção:");
		System.out.println("## 1 - Grafo Valorado");
		System.out.println("## 2 - Grafo Não Valorado");
		System.out.println("## 3 - Sair\n");
	}

	private static void menu_2(Scanner leitura) {
		System.out.println("\n###### Escolha uma opção:");
		System.out.println("## 1 - Incluir vértice");
		System.out.println("## 2 - Remover vértice");
		System.out.println("## 3 - Incluir aresta");
		System.out.println("## 4 - Remover aresta");
		System.out.println("## 5 - Representações do grafo");
		System.out.println("## 6 - Informações do grafo");
		System.out.println("## 7 - Caminhos");
		System.out.println("## 8 - Exportar");
		System.out.println("## 9 - Sair");
	}

	public static void imprimeMatrizAdj(int[][] matriz) {
		for (int l = 0; l < matriz[0].length; l++) {
			for (int c = 0; c < matriz[0].length; c++) {
				System.out.print(matriz[l][c] + " ");
			}
			System.out.println(" ");
		}
	}

	public static void imprimeMatrizInc(int[][] matriz) {
		for (int l = 0; l < matriz.length; l++) {
			for (int c = 0; c < matriz[0].length; c++) {
				System.out.print(matriz[l][c] + " ");
			}
			System.out.println(" ");
		}
	}

	public static void imprimeLista(ArrayList<Vertice> lista) {
		for (Vertice v : lista) {
			System.out.printf(v.getNome() + " ");
		}
	}

}