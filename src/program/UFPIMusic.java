package program;

import java.sql.SQLException;
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
import music.reposit.DAOEstilo;
import music.reposit.DAOMusica;
import music.reposit.DAOMusicaPlaylist;
import music.reposit.DAOPlaylist;
import user.Artista;
import user.Assinante;
import user.Usuario;
import user.exception.UsuarioJaCadastrado;
import user.exception.UsuarioNaoCadastrado;
import user.reposit.DAOUsuarios;

public class UFPIMusic implements Interface {

	@Override
	public void cadastrarEstilo(String nome) throws ValorInvalido, EstiloJaCadastrado {
		DAOEstilo dao = new DAOEstilo();
		dao.inserir(nome);

	}

	@Override
	public ArrayList<Musica> pesquisarPorEstilo(String nome) {
		DAOMusica dao = new DAOMusica();
		ArrayList<Musica> array = new ArrayList<Musica>();
		try {
			array = dao.pesquisarPorEstilo(nome);
		} catch (ValorInvalido e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array;
	}

	@Override
	public ArrayList<Musica> pesquisarPorData(Date inicial) {
		DAOMusica dao = new DAOMusica();
		ArrayList<Musica> array = new ArrayList<Musica>();
		try {
			array = dao.pesquisarPorData(inicial);
		} catch (ValorInvalido e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array;
	}

	@Override
	public ArrayList<Musica> pesquisarPorArtista(String nome) {
		DAOMusica dao = new DAOMusica();
		ArrayList<Musica> array = new ArrayList<Musica>();
		try {
			array = dao.pesquisarPorArtista(nome);
		} catch (ValorInvalido e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array;
	}

	@Override
	public void cadastrarUsuario(Usuario usuario) throws ValorInvalido, UsuarioJaCadastrado {
		DAOUsuarios dao = new DAOUsuarios();
		dao.inserir(usuario);

	}

	@Override
	public void adicionarMusica(String idUsu, String nomeMusica, String estilo, String link, int duracao,
			Date lancamento) throws ValorInvalido, UsuarioNaoCadastrado, MusicaJaCadastrada, EstiloNaoCadastrado {
		DAOMusica musicaDAO = new DAOMusica();
		DAOUsuarios usuarioDAO = new DAOUsuarios();
		Artista artista = (Artista) usuarioDAO.pesquisar(idUsu);
		Musica mus = new Musica(nomeMusica, idUsu, artista.getName(), lancamento, (Integer) duracao, estilo, link);
		try {
			musicaDAO.inserir(mus);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void criarPlaylist(String idUsu, String nomeLista)
			throws ValorInvalido, UsuarioNaoCadastrado, PlaylistExistente {
		DAOPlaylist dao = new DAOPlaylist();
		dao.inserir(nomeLista, idUsu);

	}

	@Override
	public void adicionarMusicaPlaylist(String idUsu, String nomeLista, String nomeAutor, String nomeMusica)
			throws UsuarioNaoCadastrado, PlaylistNaoExistente, MusicaNaoCadastrada, MusicaJaCadastrada {
		DAOMusicaPlaylist dao = new DAOMusicaPlaylist();
		try {
			dao.inserir(idUsu, nomeLista, nomeMusica, nomeAutor);
		} catch (ValorInvalido e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public Playlist pesquisaPlaylistUsuario(String idUsu, String nomeLista)
			throws UsuarioNaoCadastrado, PlaylistNaoExistente {
		DAOPlaylist dao = new DAOPlaylist();
		return dao.pesquisar(nomeLista, idUsu);
	}

	@Override
	public ArrayList<Playlist> pesquisaPlaylistEstilo(String idUsu, String estilo)
			throws UsuarioNaoCadastrado, PlaylistNaoExistente {
		return null;
	}

	@Override
	public void removerEstilo(String nomeEstilo) throws EstiloNaoCadastrado {
		DAOEstilo dao = new DAOEstilo();
		dao.remover(nomeEstilo);

	}

	@Override
	public void juntarUsuarios(String idUsu1, String idUsu2, String idUsuNovo) throws UsuarioNaoCadastrado {
		DAOUsuarios usuarioDAO = new DAOUsuarios();
		DAOMusicaPlaylist musplayDAO = new DAOMusicaPlaylist();
		try {
			usuarioDAO.pesquisar(idUsu1);
			usuarioDAO.pesquisar(idUsu2);
			Usuario novo = new Assinante(idUsuNovo, "concatenacao", "concatenacao@gmail.com", "concatenacao");
			usuarioDAO.inserir(novo);
			DAOPlaylist playlistDAO = new DAOPlaylist();
			playlistDAO.tranferirPlaylist(idUsu1, idUsuNovo);
			playlistDAO.tranferirPlaylist(idUsu2, idUsuNovo);
			musplayDAO.transferirPlaylist(idUsu1, idUsuNovo);
			musplayDAO.transferirPlaylist(idUsu2, idUsuNovo);
			usuarioDAO.remover(idUsu1);
			usuarioDAO.remover(idUsu2);
			
		} catch (ValorInvalido e) {
			e.printStackTrace();
		}
	}

}
