package music;

import java.util.ArrayList;

import music.exception.EstiloNaoCadastrado;
import music.exception.MusicaJaCadastrada;
import music.exception.MusicaNaoCadastrada;
import music.exception.ValorInvalido;

public class Playlist {
	private String nome;
	private String idUsu;
	private int duracaoTotal;
	private ArrayList<String> estilos = new ArrayList<>();
	private ArrayList<Musica> musicas = new ArrayList<>();
	
	public void adicionarMusica(Musica musica) throws ValorInvalido, MusicaJaCadastrada {
		if(musica == null)
			throw new ValorInvalido();
		else {
			try {
				pesquisarMusicaDaPlaylist(musica);
				throw new MusicaJaCadastrada();
			} catch (MusicaNaoCadastrada e) {
				musicas.add(musica);
				duracaoTotal += musica.getDuration();
				try {
					pesquisarEstiloNaPlaylist(musica.getStyle());
				} catch (EstiloNaoCadastrado e2) {
					estilos.add(musica.getStyle());
				}
			}
		}	
	}
	
	public Musica pesquisarMusicaDaPlaylist(Musica musica) throws MusicaNaoCadastrada {
		for(Musica x : musicas) {
			if(x.equals(musica))
				return x;
		}
		throw new MusicaNaoCadastrada();
	}
	
	public String pesquisarEstiloNaPlaylist(String estilo) throws EstiloNaoCadastrado {
		for(String x : estilos) {
			if(x.equals(estilo))
				return x;
		}
		throw new EstiloNaoCadastrado();
	}
	
	public String getName() {
		return nome;
	}

	public void setName(String name) {
		this.nome = name;
	}

	public int getDuracaoTotal() {
		return duracaoTotal;
	}

	public void setDuracaoTotal(int duracaoTotal) {
		this.duracaoTotal = duracaoTotal;
	}

	public ArrayList<String> getStyles() {
		return estilos;
	}

	public ArrayList<Musica> getMusicas() {
		return musicas;
	}

	public Playlist(String name, String nomeUsuario) {
		this.nome = name;
		this.setIdUsu(nomeUsuario);
		this.duracaoTotal = 0;
	}

	public String getIdUsu() {
		return idUsu;
	}

	public void setIdUsu(String idUsu) {
		this.idUsu = idUsu;
	}
	
}
