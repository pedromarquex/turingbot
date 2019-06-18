package program;

import java.util.ArrayList;
import java.util.Date;

import music.Musica;
import music.Playlist;
import music.exception.EstiloJaCadastrado;
import music.exception.EstiloNaoCadastrado;
import music.exception.MusicaJaCadastrada;
import music.exception.MusicaNaoCadastrada;
import music.exception.PlaylistExistente;
import music.exception.PlaylistNaoExistente;
import music.exception.ValorInvalido;
import user.Usuario;
import user.exception.UsuarioJaCadastrado;
import user.exception.UsuarioNaoCadastrado;

public interface Interface {

	public void cadastrarEstilo(String nome) throws ValorInvalido, EstiloJaCadastrado;

	public ArrayList<Musica> pesquisarPorEstilo(String nome);

	public ArrayList<Musica> pesquisarPorData(Date inicial);

	public ArrayList<Musica> pesquisarPorArtista(String nome);

	public void cadastrarUsuario(Usuario usuario) throws ValorInvalido, UsuarioJaCadastrado;

	public void adicionarMusica(String idUsu, String nomeMusica, String estilo, String link, int duracao,
			Date lancamento) throws ValorInvalido, UsuarioNaoCadastrado, MusicaJaCadastrada, EstiloNaoCadastrado;

	public void criarPlaylist(String idUsu, String nomeLista)
			throws ValorInvalido, UsuarioNaoCadastrado, PlaylistExistente;

	public void adicionarMusicaPlaylist(String idUsu, String nomeLista, String nomeAutor, String nomeMusica)
			throws UsuarioNaoCadastrado, PlaylistNaoExistente, MusicaNaoCadastrada, MusicaJaCadastrada;

	public Playlist pesquisaPlaylistUsuario(String idUsu, String nomeLista)
			throws UsuarioNaoCadastrado, PlaylistNaoExistente;

	public ArrayList<Playlist> pesquisaPlaylistEstilo(String idUsu, String estilo)
			throws UsuarioNaoCadastrado, PlaylistNaoExistente;

	public void removerEstilo(String nomeEstilo) throws EstiloNaoCadastrado;

	public void juntarUsuarios(String idUsu1, String idUsu2, String idUsuNovo) throws UsuarioNaoCadastrado;

}
