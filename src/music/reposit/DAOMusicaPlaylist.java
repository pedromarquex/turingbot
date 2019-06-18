package music.reposit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import music.exception.MusicaJaCadastrada;
import music.exception.MusicaNaoCadastrada;
import music.exception.PlaylistNaoExistente;
import music.exception.ValorInvalido;
import program.Conexao;
import user.exception.UsuarioNaoCadastrado;
import user.reposit.DAOUsuarios;

public class DAOMusicaPlaylist {

	public void removerTodos() {
		try {
			Connection con = Conexao.getConexao();
			String cmd = "delete from musicasplaylists";
			PreparedStatement st = con.prepareStatement(cmd);
			st.executeUpdate();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void pesquisar(String idUsu, String playlist, String musica, String artista)
			throws UsuarioNaoCadastrado, MusicaNaoCadastrada, PlaylistNaoExistente, ValorInvalido {
		DAOUsuarios usuarioDAO = new DAOUsuarios();
		DAOPlaylist playlistDAO = new DAOPlaylist();
		DAOMusica musicaDAO = new DAOMusica();
		usuarioDAO.pesquisar(idUsu);
		usuarioDAO.pesquisar(artista);
		try {
			musicaDAO.pesquisar(musica, artista);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		playlistDAO.pesquisar(playlist, idUsu);
		Connection con = Conexao.getConexao();
		try {
			String cmd = "select * from musicasplaylists where usuario = ? and playlist = ? and "
					+ "musica = ? and artista = ?";
			PreparedStatement st = con.prepareStatement(cmd);
			st.setString(1, idUsu);
			st.setString(2, playlist);
			st.setString(3, musica);
			st.setString(4, artista);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				st.close();
			} else {
				st.close();
				throw new MusicaNaoCadastrada();
			}
		} catch (SQLException e) {
			e.getStackTrace();
		}
	}

	public void inserir(String idUsu, String playlist, String musica, String artista)
			throws UsuarioNaoCadastrado, PlaylistNaoExistente, ValorInvalido, MusicaJaCadastrada {
		try {
			pesquisar(idUsu, playlist, musica, artista);
			throw new MusicaJaCadastrada();
		} catch (MusicaNaoCadastrada e1) {
			
		}
		Connection con = Conexao.getConexao();
		try {
			String cmd = "insert into musicasplaylists values (?, ?, ?, ?)";
			PreparedStatement st = con.prepareStatement(cmd);
			st.setString(1, idUsu);
			st.setString(2, playlist);
			st.setString(3, musica);
			st.setString(4, artista);
			st.executeUpdate();
			st.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void transferirPlaylist(String idUsu1, String idUsuNovo){
		Connection con = Conexao.getConexao();
		String cmd = "update musicaplaylists set usuario = ? where usuario = ?";
		PreparedStatement st;
		try {
			st = con.prepareStatement(cmd);
			st.setString(1, idUsuNovo);
			st.setString(2, idUsu1);
			st.executeUpdate(cmd);
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
