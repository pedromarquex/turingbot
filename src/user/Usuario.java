package user;

import java.util.ArrayList;

import music.Musica;
import music.Playlist;
import music.exception.MusicaJaCadastrada;
import music.exception.PlaylistExistente;
import music.exception.PlaylistNaoExistente;
import music.exception.ValorInvalido;
import music.reposit.PlaylistReposit;

public class Usuario {
	
	private String idUsu;
	private String name;
	private String email;
	private String password;
	
	private PlaylistReposit listas = new PlaylistReposit();
	
	public Usuario(String idUsu, String name, String email, String password) {
		this.idUsu = idUsu;
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	public void adicionarVariasPlaylists(ArrayList<Playlist> lista) {
		listas.getPlaylists().addAll(lista);
	}
	
	public void adicionarPlaylist(String nomeLista) throws ValorInvalido, PlaylistExistente {
		listas.add(nomeLista);
	}
	
	public void adicionarMusicaPlaylist(String nomeLista, Musica musica) throws PlaylistNaoExistente, MusicaJaCadastrada, ValorInvalido {
		listas.adicionarMusicaPlaylist(nomeLista, musica);
	}
	
	public Playlist pesquisarPlaylist(String nomeLista) throws ValorInvalido, PlaylistNaoExistente {
		return listas.pesquisar(nomeLista);
	}
	
	public ArrayList<Playlist> pesquisarPlaylistPorEstilo(String estilo) throws PlaylistNaoExistente{
		return listas.pesquisarPorEstilo(estilo);
	}

	public String getIdUsu() {
		return idUsu;
	}

	public void setIdUsu(String idUsu) {
		this.idUsu = idUsu;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public ArrayList<Playlist> getListas(){
		return listas.getPlaylists();
	}
	
	
	
}
