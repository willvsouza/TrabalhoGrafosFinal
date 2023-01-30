
public class Aresta {
	int peso = 1;
	private Vertice inicio;
	private Vertice fim;

	public Aresta(int peso, Vertice inicio, Vertice fim) {
		this.peso = peso;
		this.inicio = inicio;
		this.fim = fim;
	}

	public Vertice getInicio() {
		return inicio;
	}

	public Vertice getFim() {
		return fim;
	}

	public int getPeso() {
		return peso;
	}

	public void setInicio(Vertice inicio) {
		this.inicio = inicio;
	}

	public void setFim(Vertice fim) {
		this.fim = fim;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

}
