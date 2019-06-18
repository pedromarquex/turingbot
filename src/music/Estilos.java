package music;

import java.util.ArrayList;
import music.exception.EstiloJaCadastrado;
import music.exception.EstiloNaoCadastrado;
import music.exception.ValorInvalido;

public class Estilos {
	private ArrayList<String> estilos = new ArrayList<>();
	private Integer quantity;

	public Estilos() {
		this.quantity = 0;
	}

	public void add(String aux) throws ValorInvalido, EstiloJaCadastrado {
		if (aux == null || aux.equals(""))
			throw new ValorInvalido();
		try {
			pesquisar(aux);
			throw new EstiloJaCadastrado();
		} catch (EstiloNaoCadastrado e) {
			this.estilos.add(aux);
			quantity++;
		}
	}

	public String pesquisar(String aux) throws EstiloNaoCadastrado {
		if (estilos.isEmpty())
			throw new EstiloNaoCadastrado();
		for (String x : estilos) {
			if (x.equalsIgnoreCase(aux))
				return x;
		}
		throw new EstiloNaoCadastrado();
	}

	public void remover(String aux) throws EstiloNaoCadastrado, ValorInvalido {
		if(aux == null || aux.equals(""))
			throw new ValorInvalido();
		pesquisar(aux);
		for (String x : estilos) {
			if (aux.equalsIgnoreCase(x)) {
				estilos.remove(aux);
			}
		}
	}

	public ArrayList<String> listarEstilos() {
		return estilos;
	}
}
