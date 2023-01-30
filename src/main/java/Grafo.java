import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Grafo {
	private ArrayList<Vertice> vertices;
	private ArrayList<Aresta> arestas;
	private boolean orientado = false;
	private int[][] mat;

	public Grafo() {
		this.vertices = new ArrayList<Vertice>();
		this.arestas = new ArrayList<Aresta>();
	}

	public Grafo(boolean orient) {
		this.orientado = orient;
		this.vertices = new ArrayList<Vertice>();
		this.arestas = new ArrayList<Aresta>();
	}

	public Grafo(boolean orient, ArrayList<Aresta> arest, ArrayList<Vertice> vert) {
		this.orientado = orient;
		this.vertices = new ArrayList<Vertice>();
		this.arestas = new ArrayList<Aresta>();

		for (Vertice v : vert) {
			addVertice(v.getNome());
		}

		for (Aresta a : arest) {
			incluirAresta(a.getPeso(), a.getInicio().getNome(), a.getFim().getNome());
		}
	}

	public void orientaGrafo() {
		this.orientado = true;
	}

	public void setOrientacao(boolean orienta) {
		this.orientado = orienta;
	}

	public ArrayList<Vertice> getVertices() {
		return vertices;
	}

	public ArrayList<Aresta> getArestas() {
		return arestas;
	}

	public void setArestas(ArrayList<Aresta> arestas) {
		this.arestas = arestas;
	}

	public int[][] getMat() {
		return mat;
	}

	public void addVertice(Vertice vert) {
		this.vertices.add(vert);
	}

	public void addVertice(String vertice) {
		Vertice novoVertice = new Vertice(vertice);
		if (vertices.indexOf(novoVertice) == -1) {
			this.vertices.add(novoVertice);
		}

	}

	public void incluirAresta(int peso, String NomeInicio, String NomeFim) {
		Vertice inicio = this.getVertice(NomeInicio);
		Vertice fim = this.getVertice(NomeFim);
		Aresta aresta = new Aresta(peso, inicio, fim);
		inicio.addArestaEntrada(aresta);

		if (orientado) {
			fim.addArestaSaida(aresta);

		} else {
			fim.addArestaEntrada(aresta);
		}
		this.arestas.add(aresta);
	}

	public void excluirAresta(Vertice v1, Vertice v2) {

		if (orientado) {
			for (int i = 0; i < arestas.size(); i++) {
				if (arestas.get(i).getInicio() == v1 && arestas.get(i).getFim() == v2) {
					arestas.remove(i);
				}
			}
		} else if (!orientado) {
			for (int i = 0; i < arestas.size(); i++) {
				if (((arestas.get(i).getInicio() == v1 && arestas.get(i).getFim() == v2))
						|| ((arestas.get(i).getInicio() == v2 && arestas.get(i).getFim() == v1))) {
					arestas.remove(i);
				}
			}
		}
	}

	public Vertice getVertice(String Nome) {
		Vertice vertice = null;
		for (int i = 0; i < this.vertices.size(); i++) {
			if (this.vertices.get(i).getNome().equals(Nome)) {
				vertice = this.vertices.get(i);
				break;
			}
		}
		return vertice;
	}

	public void buscaEmLargura() {
		ArrayList<Vertice> marcados = new ArrayList<Vertice>();
		ArrayList<Vertice> fila = new ArrayList<Vertice>();
		Vertice atual = this.vertices.get(0);
		marcados.add(atual);
		System.out.println(atual.getNome());
		fila.add(atual);
		while (fila.size() > 0) {
			Vertice visitado = fila.get(0);
			for (int i = 0; i < visitado.getArestasSaida().size(); i++) {
				Vertice proximo = visitado.getArestasSaida().get(i).getFim();
				if (!marcados.contains(proximo)) {
					marcados.add(proximo);
					System.out.println(proximo.getNome());
					fila.add(proximo);
				}
			}
			fila.remove(0);
		}

	}

	public int getOrdemGrafo() {
		int ordem = vertices.size();
		return ordem;
	}

	public boolean existeCaminho(Vertice origem, Vertice destino, List<Vertice> marcado) {
		if (origem == null || destino == null) {
			return false;
		}
		marcado.add(origem);
		if (orientado) {
			for (Aresta a : origem.getArestasEntrada()) {
				if (a.getFim().equals(destino)) {
					return true;
				}
				if (marcado.indexOf(a.getFim()) == -1) {
					boolean aux = existeCaminho(a.getFim(), destino, marcado);
					if (aux) {
						return true;
					}
				}

			}
			return false;

		} else {
			for (Aresta a : origem.getArestasEntrada()) {
				if (origem == destino) {
					if (a.getFim().equals(destino) && a.getInicio().equals(destino)) {
						return true;
					}
				} else {
					if (a.getFim().equals(destino) || a.getInicio().equals(destino)) {
						return true;
					}
					if (marcado.indexOf(a.getFim()) == -1) {
						boolean aux = existeCaminho(a.getFim(), destino, marcado);
						if (aux) {
							return true;
						}
					}
					if (marcado.indexOf(a.getInicio()) == -1) {
						boolean aux = existeCaminho(a.getInicio(), destino, marcado);
						if (aux) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	public String retornaDot() {
		String str;
		if (this.orientado) {
			str = "digraph G {\n";
		} else {
			str = "graph G {\n";
		}

		ArrayList<Vertice> vertice = new ArrayList<Vertice>();

		for (Aresta a : arestas) {
			if (this.orientado) {
				str += a.getInicio().getNome() + " -> " + a.getFim().getNome();
			} else {
				str += a.getInicio().getNome() + " -- " + a.getFim().getNome();
			}
			str += " [label=\"" + a.getPeso() + "\"]; \n";
			vertice.add(a.getInicio());
			vertice.add(a.getFim());
		}
		for (Vertice v : vertices) {
			if (!vertice.contains(v)) {
				str += v.getNome() + ";\n";
			}
		}
		str += "}";

		return str;
	}

	public void removeVertice(String Nome) {

		Vertice vert = this.getVertice(Nome);

		this.vertices.remove(vert);
		this.removeVertice(vert);

	}

	public void removeVertice(Vertice vert) {

		ArrayList<Aresta> aux = new ArrayList<>();
		for (Aresta b : vert.getArestasEntrada()) {
			arestas.remove(b);
			aux.add(b);
		}

		for (Aresta b : vert.getArestasSaida()) {
			arestas.remove(b);
			aux.add(b);
		}

		for (Aresta b : aux) {
			if (b.getInicio().getArestasEntrada().contains(b)) {
				b.getInicio().getArestasEntrada().remove(b);
			}
			if (b.getInicio().getArestasSaida().contains(b)) {
				b.getInicio().getArestasSaida().remove(b);
			}
			if (b.getFim().getArestasEntrada().contains(b)) {
				b.getFim().getArestasEntrada().remove(b);
			}
			if (b.getFim().getArestasSaida().contains(b)) {
				b.getFim().getArestasSaida().remove(b);
			}
		}
	}

	public int getGrau(Vertice vert) {

		int grauVrtc = vert.getArestasEntrada().size() + vert.getArestasSaida().size();

		return grauVrtc;
	}

	public ArrayList<Vertice> verticesSumidouro() {
		ArrayList<Vertice> sumidouro = new ArrayList<>();
		for (Vertice vertice : vertices) {
			if (fechoTransitivoDireto(vertice).size() == 1 && !fechoTransitivoInverso(vertice).isEmpty()) {
				sumidouro.add(vertice);
			}
		}
		return sumidouro;
	}

	public ArrayList<Vertice> verticesFonte() {
		ArrayList<Vertice> fonte = new ArrayList<>();
		for (Vertice vertice : vertices) {
			if (!fechoTransitivoDireto(vertice).isEmpty() && fechoTransitivoInverso(vertice).size() == 1) {
				fonte.add(vertice);
			}
		}
		return fonte;
	}

	public boolean verificaRegular() {
		int grau = getGrau(this.vertices.get(0));

		for (Vertice v : vertices) {
			if (getGrau(v) != grau) {
				return false;
			}
		}

		return true;
	}

	public boolean verificaExisteVertice(String Nome) {
		for (Vertice v : this.vertices) {
			if (v.getNome().equals(Nome))
				;
			return true;
		}
		return false;
	}

	public boolean getOrientacao() {
		if (this.orientado) {
			return true;
		}

		return false;
	}

	public ArrayList<Vertice> fechoTransitivoDireto(Vertice v) {
		ArrayList<Vertice> fechoTransitivoDireto = new ArrayList<Vertice>();
		fechoTransitivoDireto.add(v);

		for (Vertice vert : vertices) {
			if (existeCaminho(v, vert, new ArrayList<Vertice>())) {
				fechoTransitivoDireto.add(vert);
			}
		}
		return fechoTransitivoDireto;
	}

	public ArrayList<Vertice> fechoTransitivoInverso(Vertice v) {
		ArrayList<Vertice> fechoTransitivoInverso = new ArrayList<Vertice>();
		fechoTransitivoInverso.add(v);

		for (Vertice vert : vertices) {
			if (existeCaminho(vert, v, new ArrayList<Vertice>())) {
				fechoTransitivoInverso.add(vert);
			}
		}
		return fechoTransitivoInverso;
	}

	public Set<Vertice> interseccao(List<Vertice> lista1, List<Vertice> lista2) {
		Set<Vertice> interseccao = new HashSet<Vertice>();

		for (Vertice a : lista1) {
			for (Vertice b : lista2) {
				if (a == b) {
					interseccao.add(b);
				}
			}
		}
		return interseccao;
	}

	public boolean existeConexao() {

		List<Vertice> vert = new ArrayList<>();
		List<Set<Vertice>> conexao = new ArrayList<>();

		vert.addAll(vertices);

		while (!vert.isEmpty()) {

			Vertice v = vert.get(0);

			Set<Vertice> intersecao = interseccao(fechoTransitivoDireto(v), fechoTransitivoInverso(v));

			conexao.add(intersecao);

			for (Vertice vertice : intersecao) {
				if (vert.contains(vertice)) {
					vert.remove(vertice);
				}
			}
		}

		int cont = 1;

		Set<Vertice> grupo1 = conexao.get(0);

		for (int i = cont; i < conexao.size(); i++) {
			Set<Vertice> grupo2 = conexao.get(i);

			if (existeLigacao(grupo1, grupo2)) {
				grupo1.addAll(grupo2);

			}
		}
		cont++;

		if (vertices.size() == grupo1.size()) {
			return true;
		}

		return false;
	}

	public boolean existeLigacao(Set<Vertice> list1, Set<Vertice> list2) {
		List<Vertice> lista1 = new ArrayList<Vertice>();
		List<Vertice> lista2 = new ArrayList<Vertice>();

		lista1.addAll(list1);
		lista2.addAll(list2);

		for (Vertice v1 : lista1) {
			for (Vertice v2 : lista2) {
				for (Aresta a : arestas) {
					if ((a.getInicio() == v1 && a.getFim() == v2) || (a.getInicio() == v2 && a.getFim() == v1)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void criaMatrizAdj() {
		this.mat = new int[vertices.size()][vertices.size()];

		if (orientado) {
			for (Aresta a1 : arestas) {
				this.mat[vertices.indexOf(a1.getInicio())][vertices.indexOf(a1.getFim())] = 1;
			}

		} else {
			for (Aresta a1 : arestas) {
				this.mat[vertices.indexOf(a1.getInicio())][vertices.indexOf(a1.getFim())] = 1;
				this.mat[vertices.indexOf(a1.getFim())][vertices.indexOf(a1.getInicio())] = 1;
			}
		}
	}

	public void criaMatrizInc() {
		this.mat = new int[vertices.size()][arestas.size()];

		if (orientado) {
			for (Aresta a : arestas) {

				this.mat[vertices.indexOf(a.getInicio())][arestas.indexOf(a)] = 1;
				this.mat[vertices.indexOf(a.getFim())][arestas.indexOf(a)] = -1;

			}
		} else {
			for (Aresta a : arestas) {
				this.mat[vertices.indexOf(a.getInicio())][arestas.indexOf(a)] = 1;
				this.mat[vertices.indexOf(a.getFim())][arestas.indexOf(a)] = 1;
			}
		}
	}

	public String dijkstra(Vertice origem) {

		int[][] matDijkstra = criaMatrizDijkstra(origem);

		int v = matDijkstra.length;
		String dijkstra = new String();
		boolean visitado[] = new boolean[v];
		int distancia[] = new int[v];
		distancia[0] = 0;

		for (int i = 1; i < v; i++) {
			distancia[i] = Integer.MAX_VALUE;
		}

		for (int i = 0; i < v - 1; i++) {
			int menorIndice = 0;
			menorIndice = retornaMenorIndice(distancia, visitado);
			visitado[menorIndice] = true;

			for (int j = 0; j < v; j++) {
				if (matDijkstra[menorIndice][j] != 0 && !visitado[j] && distancia[menorIndice] != Integer.MAX_VALUE) {
					int novaDist = distancia[menorIndice] + matDijkstra[menorIndice][j];
					if (novaDist < distancia[j]) {
						distancia[j] = novaDist;
					}
				}
			}
		}

		for (int i = 0; i < this.vertices.size(); i++) {
			dijkstra += (vertices.get(i).getNome() + " " + distancia[i] + "\n");
		}

		return dijkstra;
	}

	public int retornaMenorIndice(int[] distancia, boolean visitado[]) {

		int menorIndice = -1;
		for (int i = 0; i < distancia.length; i++) {
			if (!visitado[i] && (menorIndice == -1 || distancia[i] < distancia[menorIndice])) {
				menorIndice = i;
			}
		}
		return menorIndice;
	}

	public int[][] criaMatrizDijkstra(Vertice origem) {
		Vertice aux = vertices.get(0);
		int posicao = vertices.indexOf(origem);

		vertices.set(0, origem);
		vertices.set(posicao, aux);

		int[][] matDij = new int[arestas.size() + 1][arestas.size() + 1];

		for (Aresta a : arestas) {
			int a1 = vertices.indexOf(a.getInicio());
			int a2 = vertices.indexOf(a.getFim());

			matDij[a1][a2] = a.getPeso();
		}

		return matDij;

	}

	public boolean grafoSimples() {

		for (Vertice vert1 : vertices) {
			for (Vertice vert2 : vertices) {
				if ((vert1 != vert2) && (vert1.quantLigacoes(vert2) >= 2)) {
					return false;
				}
			}
		}
		for (Aresta a : arestas) {
			if (a.getInicio().equals(a.getFim())) {
				return false;
			}

		}
		return true;
	}

	public boolean verificaGrafoCompleto() {

		if (!this.grafoSimples()) {
			return false;
		}

		if (!orientado) {
			for (Vertice v : vertices) {
				if (v.getArestasEntrada().size() != (this.getVertices().size() - 1)) {
					return false;
				}
			}
		} else
			for (Vertice v : vertices) {
				if ((v.getArestasEntrada().size() + v.getArestasSaida().size()) != (this.getVertices().size() - 1)) {
					return false;
				}
			}
		return true;
	}

	public String retornaListaAdj() {
		String listaAdj = "";

		if (orientado) {

			for (Vertice vert : vertices) {
				listaAdj += vert.getNome() + " -> ";

				for (Aresta a : vert.getArestasEntrada()) {
					Vertice v = a.getFim();

					listaAdj += v.getNome() + ", ";
				}

				listaAdj += "\n";

			}
		} else {

			for (Vertice vert : vertices) {

				listaAdj += vert.getNome() + " -> ";

				for (Aresta a : arestas) {
					if (a.getInicio().equals(vert)) {
						listaAdj += a.getFim().getNome() + ", ";
					} else if (a.getFim().equals(vert)) {
						listaAdj += a.getInicio().getNome() + ", ";
					}
				}
				listaAdj += "\n";
			}
		}
		return listaAdj;
	}

}
