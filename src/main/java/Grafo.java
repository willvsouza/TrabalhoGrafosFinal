import java.util.ArrayList;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
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

	public Grafo(boolean orientado) {
		this.vertices = new ArrayList<Vertice>();
		this.arestas = new ArrayList<Aresta>();
		this.orientado = orientado;
	}
	
	public Grafo(boolean orient, ArrayList<Aresta> arest, ArrayList<Vertice> vrtcs) {
		this.vertices = new ArrayList<Vertice>();
		this.arestas = new ArrayList<Aresta>();
		
		for (Vertice v : vrtcs) {
			addVertice(v.getDado());
		}
		
		for(Aresta a : arest) {
			incluirAresta(a.getPeso(), a.getInicio().getDado(), a.getFim().getDado());
		}
		this.orientado = orient;
	}
	
	public void orientaGrafo() {
		this.orientado = true;
	}

	public void setOrientacao(boolean b) {
		this.orientado = b;
	}

	public ArrayList<Aresta> getArestas() {
		return arestas;
	}

	public ArrayList<Vertice> getVertices() {
		return vertices;
	}
	
	public void setArestas(ArrayList<Aresta> arestas) {
		this.arestas = arestas;
	}

	public int[][] getMat() {
		return mat;
	}
	
	public void addVertice(String vertice) {
        Vertice novoVertice = new Vertice(vertice);
        if (vertices.indexOf(novoVertice) == -1) {
            this.vertices.add(novoVertice);
        }

    }
	
	public void addVertice(Vertice vert) {
		this.vertices.add(vert);
	}

	public void incluirAresta(int peso, String dadoInicio, String dadoFim) {
		Vertice inicio = this.getVertice(dadoInicio);
		Vertice fim = this.getVertice(dadoFim);
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
		
		if(orientado) {
			for (int i=0; i<arestas.size(); i++) {
				if (arestas.get(i).getInicio()==v1 && arestas.get(i).getFim()==v2) {
					arestas.remove(i);
				}
			}
		}else if(!orientado){
			for (int i=0; i<arestas.size(); i++) {
				if (((arestas.get(i).getInicio()==v1 && arestas.get(i).getFim()==v2)) || ((arestas.get(i).getInicio()==v2 && arestas.get(i).getFim()==v1))) {
					arestas.remove(i);
			}
		}
	}
}
	
	
	public Vertice getVertice(String dado) {
		Vertice vertice = null;
		for (int i = 0; i < this.vertices.size(); i++) {
			if (this.vertices.get(i).getDado().equals(dado)) {
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
		System.out.println(atual.getDado());
		fila.add(atual);
		while (fila.size() > 0) {
			Vertice visitado = fila.get(0);
			for (int i = 0; i < visitado.getArestasSaida().size(); i++) {
				Vertice proximo = visitado.getArestasSaida().get(i).getFim();
				if (!marcados.contains(proximo)) {
					marcados.add(proximo);
					System.out.println(proximo.getDado());
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
				str += a.getInicio().getDado() + " -> " + a.getFim().getDado();
			} else {
				str += a.getInicio().getDado() + " -- " + a.getFim().getDado();
			}
			str += " [label=\"" + a.getPeso() + "\"]; \n";
			vertice.add(a.getInicio());
			vertice.add(a.getFim());
		}
		for (Vertice v : vertices) {
			if (!vertice.contains(v)) {
				str += v.getDado() + ";\n";
			}
		}
		str += "}";

		return str;
	}


	public void removeVertice(String dado) {

		Vertice vrtc = this.getVertice(dado);

		this.vertices.remove(vrtc);
		this.removeVertice(vrtc);

	}

	public void removeVertice(Vertice vrtc) {

		ArrayList<Aresta> aux = new ArrayList<>();
		for (Aresta b : vrtc.getArestasEntrada()) {
			arestas.remove(b);
			aux.add(b);
		}

		for (Aresta b : vrtc.getArestasSaida()) {
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

	public int getGrau(Vertice vrtc) {

		int grauVrtc = vrtc.getArestasEntrada().size() + vrtc.getArestasSaida().size();
		
		return grauVrtc;

	}

	public ArrayList<Vertice> verticesSumidouro() {
        ArrayList<Vertice> sumidouro = new ArrayList<>();
        for (Vertice vertice : vertices) {
            if (fechoTransitivoDireto(vertice).size()==1 && !fechoTransitivoInverso(vertice).isEmpty()) {
                sumidouro.add(vertice);
            }
        }
        return sumidouro;
	}

	public ArrayList<Vertice> verticesFonte() {
        ArrayList<Vertice> fonte = new ArrayList<>();
        for (Vertice vertice : vertices) {
            if (!fechoTransitivoDireto(vertice).isEmpty() && fechoTransitivoInverso(vertice).size()==1) {
                fonte.add(vertice);
            }
        }
        return fonte;
	}

	public boolean verificaRegular() {
		int grau = getGrau(this.vertices.get(0));
		
		for (Vertice v : vertices) {
			if(getGrau(v) != grau) {
				return false;
			}
		}
		
		return true;
	}

	public boolean verificaExisteVertice(String dado) {
		for (Vertice v : this.vertices) {
			if(v.getDado().equals(dado));
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

		for (Vertice vrtc : vertices) {
			if (existeCaminho(v, vrtc, new ArrayList<Vertice>())) {
				fechoTransitivoDireto.add(vrtc);
			}
		}
		return fechoTransitivoDireto;
	}

	public ArrayList<Vertice> fechoTransitivoInverso(Vertice v) {
		ArrayList<Vertice> fechoTransitivoInverso = new ArrayList<Vertice>();
		fechoTransitivoInverso.add(v);

		for (Vertice vrtc : vertices) {
			if (existeCaminho(vrtc, v, new ArrayList<Vertice>())) {
				fechoTransitivoInverso.add(vrtc);
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
        
		List<Vertice> vrtc = new ArrayList<>();
        List<Set<Vertice>> conexao = new ArrayList<>();

        vrtc.addAll(vertices);

        while (!vrtc.isEmpty()) {

            Vertice v = vrtc.get(0);

            Set<Vertice> intersecao = interseccao(fechoTransitivoDireto(v), fechoTransitivoInverso(v));

            conexao.add(intersecao);

            for (Vertice vertice : intersecao) {
                if (vrtc.contains(vertice)) {
                    vrtc.remove(vertice);
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
    	
    	if(orientado) {
    		for (Aresta a1 : arestas) {
    			this.mat[vertices.indexOf(a1.getInicio())][vertices.indexOf(a1.getFim())] = 1;
    		}
    
    	}
    	else {	
    		for(Aresta a1 : arestas) {
    			this.mat[vertices.indexOf(a1.getInicio())][vertices.indexOf(a1.getFim())] = 1;
    			this.mat[vertices.indexOf(a1.getFim())][vertices.indexOf(a1.getInicio())] = 1;
    		}
    	}
	}
    
	public void criaMatrizInc() {
    	this.mat = new int[vertices.size()][arestas.size()];
    	
    	if(orientado) {
    		for (Aresta a : arestas) {
    			
    			this.mat[vertices.indexOf(a.getInicio())][arestas.indexOf(a)] = 1;
    			this.mat[vertices.indexOf(a.getFim())][arestas.indexOf(a)] = -1;
   	
	}
      	}
    	else {	
    		for(Aresta a : arestas) {
    			this.mat[vertices.indexOf(a.getInicio())][arestas.indexOf(a)] = 1;
    			this.mat[vertices.indexOf(a.getFim())][arestas.indexOf(a)] = 1;
    		}
    	}
	}

	
	public String dijkstra (Vertice origem) {
		
		int[][] matDijkstra = criaMatrizDijkstra(origem);
		
		
		int v = matDijkstra.length;
		String dijkstra = new String();
		boolean visitado[] = new boolean[v];
		int distancia[] = new int[v];
		distancia[0] = 0;
		
		for(int i=1; i < v; i++) {
			distancia[i] = Integer.MAX_VALUE;
		}
		
		for(int i=0; i < v-1; i++) {
			int menorIndice = 0;
			menorIndice = retornaMenorIndice (distancia, visitado);   
			visitado[menorIndice] = true;
			
			for (int j=0; j<v ; j++) {
				if(matDijkstra[menorIndice][j] != 0 && !visitado[j] && distancia[menorIndice] != Integer.MAX_VALUE) {
					int novaDist = distancia[menorIndice] + matDijkstra[menorIndice][j];
					if(novaDist < distancia[j]) {
						distancia[j] = novaDist;
					}
					}
			}
		}
		
		
	
		
		for(int i=0; i < this.vertices.size(); i++) {
			dijkstra += (vertices.get(i).getDado() + " " + distancia[i] + "\n");
		}
	
		return dijkstra;
	}
    


	public int retornaMenorIndice(int [] distancia, boolean visitado[]) {
		
		int menorIndice = -1;
		for (int i=0; i<distancia.length; i++) {
			if(!visitado[i] && (menorIndice == -1 || distancia[i] < distancia[menorIndice])) {
				menorIndice = i;
			}
		}
		return menorIndice;
	}
						

	public int[][] criaMatrizDijkstra(Vertice origem){
		Vertice aux = vertices.get(0);
		int posicao = vertices.indexOf(origem);
				
		vertices.set(0, origem);
		vertices.set(posicao, aux);
		 
		
		int[][] matDij = new int[arestas.size()+1][arestas.size()+1];
		
		for(Aresta a: arestas) {
			int a1 = vertices.indexOf(a.getInicio());
			int a2 = vertices.indexOf(a.getFim());
			
			matDij[a1][a2] = a.getPeso();
		}
	
		return matDij;
	
	}
	
	
	
	public Grafo Kruskal() {
		
		Aresta aux = new Aresta(0,null,null);
		ArrayList<Aresta> copiaAresta = new ArrayList<Aresta>();
		for(Aresta a : arestas) {
		Aresta nova = new Aresta(a.getPeso(), a.getInicio(),a.getFim());
		copiaAresta.add(nova);
			
		}
		
		for (int i =0; i<copiaAresta.size() ; i++) {
			for (int j =0; j < copiaAresta.size(); j++) {
				if(copiaAresta.get(i).getPeso() < copiaAresta.get(j).getPeso()) {
					
					aux = copiaAresta.get(i);
					copiaAresta.set(i, copiaAresta.get(j));
					copiaAresta.set(j, aux);
				}
			}
		}
		
		int n = this.getVertices().size();
		
		ArrayList<Aresta> novaListaAresta = new ArrayList<Aresta>();
		
		int[][] matriz = new int[2][vertices.size()+1];
		
		for (int i=0; i<vertices.size(); i++){
			matriz[0][i] = i+1;
			matriz[1][i] = i+1;
		}
		
		
		int comp_u;
		int comp_v;
		
		while ((novaListaAresta.size()<n-1) && (!copiaAresta.isEmpty())){
			
			
			aux = copiaAresta.get(0);
			copiaAresta.remove(0);
			
			int u = vertices.indexOf(aux.getInicio());
			int v = vertices.indexOf(aux.getFim());
			
			comp_u = matriz[1][u];
			comp_v = matriz[1][v];
			
						
			if (comp_u != comp_v) {
				merge(comp_u, comp_v, matriz);
				novaListaAresta.add(aux);
			}
				
	
		}
		
		
		Grafo grf = new Grafo(this.orientado, novaListaAresta, getVertices());
		return grf;	
	}
		

	private void merge(int comp_u, int comp_v, int[][] matriz) {
		matriz[0][comp_v] = matriz[0][comp_u];
	}

	
	public Grafo grafoReduzido() {
		List<Vertice> copiaVertices = new ArrayList<Vertice>();
		List<Set<Vertice>> vrtcIntersecao = new ArrayList<>();
		Grafo grafoReduzido = new Grafo(this.orientado);
		
		copiaVertices.addAll(vertices);
		
		
		
		while(!copiaVertices.isEmpty()) {
			
		List<Vertice> ftd = new ArrayList<Vertice>();
		List<Vertice> fti = new ArrayList<Vertice>();
		Set<Vertice> intersecao = new HashSet<Vertice>();
		
		ftd = fechoTransitivoDireto(copiaVertices.get(0));
		fti = fechoTransitivoInverso(copiaVertices.get(0));	
		intersecao = interseccao(ftd,fti);
		
		vrtcIntersecao.add(intersecao);
		grafoReduzido.addVertice("G" + (vrtcIntersecao.size()));
		
		
		for(Vertice vrtc : intersecao) {
			if(copiaVertices.contains(vrtc)) {
				copiaVertices.remove(vrtc);
			}
		}
	}
		
		int cont = 1;
		
		for(int j=0; j<vrtcIntersecao.size(); j++) {
			Set<Vertice> grupo1	= vrtcIntersecao.get(j);
			
			for(int i=cont; i<vrtcIntersecao.size(); i++) {
				Set<Vertice> grupo2 = vrtcIntersecao.get(i);
				
				if(existeLigacao(grupo1, grupo2)) {
					grafoReduzido.incluirAresta(1, "G" + (j+1), "G" + (i+1));
					
				}
				}
		
			cont++;
		}
		return grafoReduzido;
	}
			
	public boolean grafoSimples() {
		
		
		for(Vertice vrtc1 : vertices ) {
			for(Vertice vrtc2 : vertices) {
				if ((vrtc1 != vrtc2) && (vrtc1.qtdadeLig(vrtc2) >= 2)) {
					return false;
				}
			}
		}
		for(Aresta a : arestas) {
			if(a.getInicio().equals(a.getFim())) {
				return false;
			}
		
		}
		return true;
	}


	public boolean verificaGrafoCompleto() { 
		
		
		if(!this.grafoSimples()) {
			return false;
		}
		
		if(!orientado) {
			for(Vertice v : vertices) {
				if(v.getArestasEntrada().size() != (this.getVertices().size()-1)) {
					return false;
				}
			}
		}else
			for(Vertice v : vertices) {
				if((v.getArestasEntrada().size()+v.getArestasSaida().size()) != (this.getVertices().size()-1)) {
					return false;
				}
			}
		return true;
	}

	
	public String retornaListaAdj() {
		String listaAdj = "";
		
		if(orientado) {
			
			for(Vertice vrtc : vertices) {
				listaAdj += vrtc.getDado() + " -> ";
				
				for(Aresta a : vrtc.getArestasEntrada()) {
					Vertice v = a.getFim();
					
					listaAdj += v.getDado() + ", ";
				}
			
			listaAdj += "\n";
				
			}
		}else {
			
			for(Vertice vrtc : vertices) {
				
				listaAdj += vrtc.getDado() + " -> ";
				
					for(Aresta a : arestas) {
						if(a.getInicio().equals(vrtc)) {
							listaAdj += a.getFim().getDado() +", ";
						}else if(a.getFim().equals(vrtc)) {
							listaAdj += a.getInicio().getDado() +", ";
						}
					}
			listaAdj += "\n";
			}
		}
		return listaAdj;
	}
	
	
}
		
		
		
		
		
			
		
