

public class Aresta {
	int peso = 1 ;
	private Vertice inicio;
	private Vertice fim;
	
	
	public Aresta (int pesoAresta, Vertice inicio, Vertice fim) {
		this.peso = pesoAresta;
		this.inicio = inicio;
		this.fim = fim;
	}


	public Vertice getInicio() {
		return inicio;
	}


	public void setInicio(Vertice inicio) {
		this.inicio = inicio;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}


	public int getPeso() {
		return peso;
	}
	
	public Vertice getFim() {
		return fim;
	}


	public void setFim(Vertice fim) {
		this.fim = fim;
	}

}	
