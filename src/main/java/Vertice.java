import java.util.ArrayList;

public class Vertice {
	private String nome;
	private ArrayList<Aresta> arestasEntrada;
	private ArrayList<Aresta> arestasSaida;

	public Vertice(String nome) {
		this.nome = nome;
		this.arestasEntrada = new ArrayList<Aresta>();
		this.arestasSaida = new ArrayList<Aresta>();
	}

	public String getNome() {
		return nome;
	}

	public ArrayList<Aresta> getArestasEntrada() {
		return arestasEntrada;
	}

	public ArrayList<Aresta> getArestasSaida() {
		return arestasSaida;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void addArestaEntrada(Aresta aresta) {
		this.arestasEntrada.add(aresta);
	}

	public void addArestaSaida(Aresta aresta) {
		this.arestasSaida.add(aresta);
	}

	public boolean equals(Object obj) {
		return nome.equals(((Vertice) obj).getNome());
	}

	public int quantLigacoes(Vertice vert) {
		int cont = 0;

		for (Aresta a : vert.arestasEntrada) {
			if (getNome().equals(a.getFim().getNome()) || getNome().equals(a.getInicio().getNome())) {
				cont++;
			}
		}

		for (Aresta a : vert.arestasSaida) {
			if (getNome().equals(a.getFim().getNome()) || getNome().equals(a.getInicio().getNome())) {
				cont++;
			}
		}
		return cont;
	}

}
