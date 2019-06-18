package music.reposit;

import java.util.ArrayList;

import music.Musica;
import music.Playlist;
import music.exception.EstiloNaoCadastrado;
import music.exception.MusicaJaCadastrada;
import music.exception.PlaylistExistente;
import music.exception.PlaylistNaoExistente;
import music.exception.ValorInvalido;

public class PlaylistReposit {
	
	private ArrayList<Playlist> listaDePlaylist = new ArrayList<>();
	private Integer quantity;
	
	// Construtor
	public PlaylistReposit() {
		this.quantity = 0;
	}
	
	// Pesquisar
	public Playlist pesquisar(String nome) throws ValorInvalido, PlaylistNaoExistente {
		if(nome == null || nome.equals(""))
			throw new ValorInvalido();
		else {
			for(Playlist x : listaDePlaylist) {
				if(x.getName().equalsIgnoreCase(nome))
					return x;
			}
			throw new PlaylistNaoExistente();
		}
	}
	
	public ArrayList<Playlist> pesquisarPorEstilo(String estilo) throws PlaylistNaoExistente {
		ArrayList<Playlist> subPlaylist = new ArrayList<>();
		for(Playlist x : listaDePlaylist) {
			try {
				x.pesquisarEstiloNaPlaylist(estilo);
				subPlaylist.add(x);
			} catch (EstiloNaoCadastrado e) {
	
			}
		}
		if(subPlaylist.isEmpty())
			throw new PlaylistNaoExistente();
		return subPlaylist;
	}
	
	// Adicionar Playlist.
	public void add(String nome) throws ValorInvalido, PlaylistExistente {
		try {
			pesquisar(nome);
			throw new PlaylistExistente();
		} catch (PlaylistNaoExistente e) {
			Playlist novo = new Playlist(nome , null);
			listaDePlaylist.add(novo);
			this.quantity++;
		}
	}
	
	// Adicionar Musica a alguma Playlist.
	public void adicionarMusicaPlaylist(String nomeLista, Musica musica) throws ValorInvalido, PlaylistNaoExistente, MusicaJaCadastrada {
		if(nomeLista == null || nomeLista.equals(""))
			throw new ValorInvalido();
		else {
			Playlist x = pesquisar(nomeLista);
			x.adicionarMusica(musica);
		}
	}
	
	public ArrayList<Playlist> getPlaylists(){
		return listaDePlaylist;
	}
	
}
