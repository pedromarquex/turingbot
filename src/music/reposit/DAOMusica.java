package music.reposit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import music.Musica;
import music.exception.EstiloNaoCadastrado;
import music.exception.MusicaJaCadastrada;
import music.exception.MusicaNaoCadastrada;
import music.exception.ValorInvalido;
import program.Conexao;
import user.Artista;
import user.exception.UsuarioNaoCadastrado;
import user.reposit.DAOUsuarios;

public class DAOMusica {

	public void removerTodos() {
		try {
			Connection con = Conexao.getConexao();
			String cmd = "delete from musicas";
			PreparedStatement st = con.prepareStatement(cmd);
			st.executeUpdate();
			st.close();
		} catch (SQLException e) {
		}
	}

	@SuppressWarnings("deprecation")
	public void inserir(Musica nova)
			throws SQLException, ValorInvalido, UsuarioNaoCadastrado, EstiloNaoCadastrado, MusicaJaCadastrada {
		Connection con = Conexao.getConexao();
		try {
			pesquisar(nova.getName(), nova.getIdUsu());
			throw new MusicaJaCadastrada();
		} catch (MusicaNaoCadastrada e) {
			DAOEstilo EstiloDAO = new DAOEstilo();
			EstiloDAO.pesquisar(nova.getStyle());
			DAOUsuarios UsuarioDAO = new DAOUsuarios();
			UsuarioDAO.pesquisar(nova.getIdUsu());
			String sql = "insert into musicas values (?, ?, ?, ?, ?, ?)";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, nova.getName());
			st.setString(2, nova.getArtistName());
			st.setInt(3, nova.getReleaseDate().getDate());
			st.setInt(4, nova.getDuration());
			st.setString(4, nova.getLink());
			st.setString(6, nova.getStyle());
			st.executeUpdate();
			st.close();
		}
	}

	public Musica pesquisar(String nome, String idUsuArtista)
			throws ValorInvalido, UsuarioNaoCadastrado, MusicaNaoCadastrada, SQLException {
		Connection con = Conexao.getConexao();
		String cmd = "select * from musicas where nome = ? and artista = ?";
		PreparedStatement st = con.prepareStatement(cmd);
		st.setString(1, nome);
		st.setString(2, idUsuArtista);
		ResultSet rs = st.executeQuery();
		if (rs.next()) {
			DAOUsuarios DAO = new DAOUsuarios();
			Artista artista = (Artista) DAO.pesquisar(idUsuArtista);
			Date lanc = new Date(rs.getLong("lancamento"));
			Musica musica = new Musica(nome, idUsuArtista, artista.getName(), lanc, rs.getInt("duracao"),
					rs.getString("estilo"), rs.getString("link"));
			return musica;
		} else {
			throw new MusicaNaoCadastrada();
		}
	}

	public ArrayList<Musica> pesquisarPorEstilo(String nome) throws ValorInvalido {
		ArrayList<Musica> musicas = new ArrayList<Musica>();
		Connection con = Conexao.getConexao();
		DAOUsuarios usuarioDAO = new DAOUsuarios();
		try {
			String cmd = "select * from musicas where estilo = ?";
			PreparedStatement st = con.prepareStatement(cmd);
			st.setString(1, nome);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Date lanc = new Date(rs.getInt("lancamento"));
				Artista artista;
				try {
					artista = (Artista) usuarioDAO.pesquisar(rs.getString("artista"));
					Musica mus = new Musica(rs.getString("nome"), artista.getIdUsu(), rs.getString("artista"), lanc,
							rs.getInt("duracao"), rs.getString("estilo"), rs.getString("link"));
					musicas.add(mus);
				} catch (UsuarioNaoCadastrado e) {

				}
			}
		} catch (SQLException e) {

		}
		return musicas;
	}

	@SuppressWarnings("deprecation")
	public ArrayList<Musica> pesquisarPorData(Date inicial) throws ValorInvalido {
		ArrayList<Musica> musicas = new ArrayList<Musica>();
		Connection con = Conexao.getConexao();
		DAOUsuarios usuarioDAO = new DAOUsuarios();
		try {
			String cmd = "select * from musicas where lancamento > ?";
			PreparedStatement st = con.prepareStatement(cmd);
			st.setInt(1, inicial.getDate());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Date lanc = new Date(rs.getInt("lancamento"));
				Artista artista;
				try {
					artista = (Artista) usuarioDAO.pesquisar(rs.getString("artista"));
					Musica mus = new Musica(rs.getString("nome"), artista.getIdUsu(), rs.getString("artista"), lanc,
							rs.getInt("duracao"), rs.getString("estilo"), rs.getString("link"));
					musicas.add(mus);
				} catch (UsuarioNaoCadastrado e) {

				}
			}
		} catch (SQLException e) {

		}
		return musicas;
	}

	public ArrayList<Musica> pesquisarPorArtista(String nome) throws ValorInvalido {
		ArrayList<Musica> musicas = new ArrayList<Musica>();
		Connection con = Conexao.getConexao();
		DAOUsuarios usuarioDAO = new DAOUsuarios();
		try {
			String cmd = "select * from musicas where artista = ?";
			PreparedStatement st = con.prepareStatement(cmd);
			st.setString(1, nome);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Date lanc = new Date(rs.getInt("lancamento"));
				Artista artista;
				try {
					artista = (Artista) usuarioDAO.pesquisar(rs.getString("artista"));
					Musica mus = new Musica(rs.getString("nome"), artista.getIdUsu(), rs.getString("artista"), lanc,
							rs.getInt("duracao"), rs.getString("estilo"), rs.getString("link"));
					musicas.add(mus);
				} catch (UsuarioNaoCadastrado e) {

				}
			}
		} catch (SQLException e) {

		}
		return musicas;
	}

	public void removerPorEstilo(String estilo) {
		try {
			Connection con = Conexao.getConexao();
			String cmd = "delete from musicas where estilo = ?";
			PreparedStatement st = con.prepareStatement(cmd);
			st.setString(1, estilo);
			st.executeUpdate();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
