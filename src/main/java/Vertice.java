import java.util.ArrayList;

public class Vertice {
	private String dado;
	private ArrayList<Aresta> arestasEntrada;
	private ArrayList<Aresta> arestasSaida;
	
	public Vertice(String dado) {
		this.dado = dado;
		this.arestasEntrada = new ArrayList<Aresta>();
		this.arestasSaida = new ArrayList<Aresta>();
	}
	
	public String getDado() {
		return dado;
	}

	public void setDado(String dado) {
		this.dado = dado;
	}

	public void addArestaEntrada(Aresta aresta) {
		this.arestasEntrada.add(aresta);
	}
	
	public void addArestaSaida(Aresta aresta) {
		this.arestasSaida.add(aresta);
	}

	public ArrayList<Aresta> getArestasEntrada() {
		return arestasEntrada;
	}

	public ArrayList<Aresta> getArestasSaida() {
		return arestasSaida;
	}

	public boolean equals(Object obj){
		return dado.equals(((Vertice) obj).getDado());
	}
	
	public int qtdadeLig (Vertice vrtc) {
		int cont = 0;
		
		for (Aresta a : vrtc.arestasEntrada) {
			if (getDado().equals(a.getFim().getDado())||getDado().equals(a.getInicio().getDado())) {
				cont++;
			}
		}
		
		for(Aresta a : vrtc.arestasSaida) {
			if (getDado().equals(a.getFim().getDado())||getDado().equals(a.getInicio().getDado())) {
				cont++;
			}
		}
		return cont;
	}
	

}
