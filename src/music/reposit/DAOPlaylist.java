package music.reposit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import music.Playlist;
import music.exception.PlaylistExistente;
import music.exception.PlaylistNaoExistente;
import music.exception.ValorInvalido;
import program.Conexao;
import user.exception.UsuarioNaoCadastrado;
import user.reposit.DAOUsuarios;


public class DAOPlaylist {
	public void removerTodos() {
		try {
			Connection con = Conexao.getConexao();
			String cmd = "delete from playlists";
			PreparedStatement st = con.prepareStatement(cmd);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Playlist pesquisar(String nome, String idUsu) throws PlaylistNaoExistente {
		Connection con = Conexao.getConexao();
		try {
			String cmd = "select * from playlists where nome = ? and usuario = ?";
			PreparedStatement st = con.prepareStatement(cmd);
			st.setString(1, nome);
			st.setString(2, idUsu);
			ResultSet rs = st.executeQuery();
			
			if (rs.next()) {
				Playlist playlist = new Playlist( rs.getString("nome"), rs.getString("usuario"));
				return playlist;
			} else {
				throw new PlaylistNaoExistente();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new PlaylistNaoExistente();
	}
	
	public void remover(String nome, String idUsu) throws PlaylistNaoExistente {
		pesquisar(nome, idUsu);
		try {
			Connection con = Conexao.getConexao();
			String sql = "delete from playlists where nome = ? and usuario = ?";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, nome);
			pst.setString(2, idUsu);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void inserir(String nome, String idUsu) throws UsuarioNaoCadastrado, ValorInvalido, PlaylistExistente {
		Connection con = Conexao.getConexao();
		try {
			try {
				pesquisar(nome, idUsu);
				throw new PlaylistExistente();
			} catch (PlaylistNaoExistente e) {
			}
			DAOUsuarios usuarioDAO = new DAOUsuarios();
			usuarioDAO.pesquisar(idUsu);
			String cmd = "insert into playlists values (?, ?)";
			PreparedStatement st = con.prepareStatement(cmd);
			st.setString(1, idUsu);
			st.setString(2, nome);
			st.executeUpdate();
			st.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void tranferirPlaylist(String idUsu1, String idUsuNovo) {
		try {
			Connection con = Conexao.getConexao();
			String cmd = "update playlists set usuario=? where usuario=?";
			PreparedStatement st = con.prepareStatement(cmd);
			st.setString(1, idUsuNovo);
			st.setString(2, idUsu1);
			st.executeUpdate(cmd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
