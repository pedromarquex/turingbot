package music.reposit;

import java.util.ArrayList;
import java.util.Date;

import music.Musica;
import music.exception.MusicaNaoCadastrada;
import music.exception.ValorInvalido;

public class MusicaReposit {
	private ArrayList<Musica> listaDeMusica = new ArrayList<>();
	private Integer quantity;

	// Construtor
	public MusicaReposit() {
		this.quantity = 0;
	}

	// Pesquisar
	public Musica pesquisarMusicaAcervo(String IdUser, String Name) throws ValorInvalido, MusicaNaoCadastrada {
		if (IdUser.equals(null) || Name.equals(""))
			throw new ValorInvalido();
		for (Musica x : listaDeMusica) {
			if (x.getIdUsu().equalsIgnoreCase(IdUser) && x.getName().equalsIgnoreCase(Name))
				return x;
		}
		throw new MusicaNaoCadastrada();
	}

	public ArrayList<Musica> pesquisarPorEstilo(String style) {
		ArrayList<Musica> subPlayList = new ArrayList<>();

		for (Musica x : listaDeMusica) {
			if (x.getStyle().equalsIgnoreCase(style))
				subPlayList.add(x);
		}
		return subPlayList;
	}

	public ArrayList<Musica> pesquisarPorData(Date inicial) {
		ArrayList<Musica> subPlayList = new ArrayList<>();

		for (Musica x : listaDeMusica) {
			if (inicial.before(x.getReleaseDate())) {
				subPlayList.add(x);
			}
		}
		return subPlayList;
	}

	public ArrayList<Musica> pesquisarPorArtista(String Name) {
		ArrayList<Musica> subPlayList = new ArrayList<>();

		for (Musica x : listaDeMusica) {
			if (x.getIdUsu().equalsIgnoreCase(Name))
				subPlayList.add(x);
		}
		return subPlayList;
	}

	// Adicionar Musica
	public void add(Musica aux) throws ValorInvalido {
		if (aux == null || aux.getArtistName() == null || aux.getDuration() == null || aux.getIdUsu() == null
				|| aux.getLink() == null || aux.getName() == null || aux.getReleaseDate() == null
				|| aux.getStyle() == null || aux.getArtistName().equals("") || aux.getDuration() < 1 || aux.getIdUsu().equals("")
				|| aux.getLink().equals("") || aux.getName().equals("") || aux.getReleaseDate().getTime() == 0.0 || aux.getStyle().equals(""))
			throw new ValorInvalido();
		try {
			pesquisarMusicaAcervo(aux.getIdUsu(), aux.getName());
		} catch (MusicaNaoCadastrada e) {
			listaDeMusica.add(aux);
			quantity++;
		}
	}

	public void removerPorEstilo(String nome) {
		listaDeMusica.removeAll(pesquisarPorEstilo(nome));
	}
}
