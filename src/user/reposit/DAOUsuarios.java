package user.reposit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import music.exception.ValorInvalido;
import program.Conexao;
import user.*;
import user.exception.UsuarioNaoCadastrado;

public class DAOUsuarios {

	public Usuario pesquisar(String idUsu) throws ValorInvalido, UsuarioNaoCadastrado {
		if (idUsu == null || idUsu.equals(""))
			throw new ValorInvalido();
		try {
			Connection con = Conexao.getConexao();
			String cmd = "select * from usuarios where identificador = ?";
			PreparedStatement st = con.prepareStatement(cmd);
			st.setString(1, idUsu);
			ResultSet rs = st.executeQuery();
			Usuario usuario = null;
			if (rs.next()) {
				if (rs.getInt("tipo") == 1) {
					usuario = new Assinante(rs.getString("identificador"), rs.getString("nome"), rs.getString("email"),
							rs.getString("senha"));
					st.close();
					return usuario;
				} else if (rs.getInt("tipo") == 2) {
					usuario = new Artista(rs.getString("identificador"), rs.getString("nome"), rs.getString("email"),
							rs.getString("senha"));
					st.close();
					return usuario;

				} else if (rs.getInt("tipo") == 3) {
					ArrayList<Artista> artistas = new ArrayList<Artista>();
					cmd = "select * from usuarios where idbanda = ?";
					st = con.prepareStatement(cmd);
					st.setString(1, rs.getString("identificador"));
					ResultSet rs2 = st.executeQuery();
					while (rs2.next()) {
						Artista a = new Artista(rs2.getString("identificador"), rs2.getString("nome"),
								rs2.getString("email"), rs2.getString("senha"));
						artistas.add(a);
					}
					usuario = new Banda(rs.getString("identificador"), rs.getString("nome"), rs.getString("email"),
							rs.getString("senha"), artistas);
					st.close();
					return usuario;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		throw new UsuarioNaoCadastrado();
	}

	public void inserir(Usuario novo) throws ValorInvalido {

		try {
			pesquisar(novo.getIdUsu());
		} catch (UsuarioNaoCadastrado e) {
			try {
				Connection con = Conexao.getConexao();

				if (novo instanceof Artista) {
					String cmd = "insert into usuarios (identificador, nome, email, senha, tipo) "
							+ "values (?, ?, ?, ?, ?)";
					PreparedStatement st = con.prepareStatement(cmd);
					st.setString(1, novo.getIdUsu());
					st.setString(2, novo.getName());
					st.setString(3, novo.getEmail());
					st.setString(4, novo.getPassword());
					st.setInt(5, 2);
					st.executeUpdate();
					st.close();
				} else if (novo instanceof Assinante) {
					String cmd = "insert into usuarios (identificador, nome, email, senha, tipo) "
							+ "values (?, ?, ?, ?, ?)";
					PreparedStatement st = con.prepareStatement(cmd);
					st.setString(1, novo.getIdUsu());
					st.setString(2, novo.getName());
					st.setString(3, novo.getEmail());
					st.setString(4, novo.getPassword());
					st.setInt(5, 1);
					st.executeUpdate();
					st.close();
				} else if (novo instanceof Banda) {
					PreparedStatement st;
					if (((Banda) novo).getArtistas() == null || ((Banda) novo).getArtistas().isEmpty()) {
						throw new ValorInvalido();
					}
					for (Usuario a : ((Banda) novo).getArtistas()) {
						if (a.getEmail() == null || a.getEmail().equals("") || a.getIdUsu() == null
								|| a.getIdUsu().equals("") || a.getListas() == null || a.getName() == null
								|| a.getName().equals("") || a.getPassword() == null || a.getPassword().equals(""))
							throw new ValorInvalido();
						String sql = "insert into usuarios (identificador, nome, email, senha, idbanda, tipo) "
								+ "values (?, ?, ?, ?, ?)";
						st = con.prepareStatement(sql);
						st.setString(1, a.getIdUsu());
						st.setString(2, a.getName());
						st.setString(3, a.getEmail());
						st.setString(4, a.getPassword());
						st.setString(5, novo.getIdUsu());
						st.setInt(6, 2);
						st.executeUpdate();
					}

					String sql = "insert into usuarios (identificador, nome, email, senha, tipo) "
							+ "values (?, ?, ?, ?, ?)";
					st = con.prepareStatement(sql);
					st.setString(1, novo.getIdUsu());
					st.setString(2, novo.getName());
					st.setString(3, novo.getEmail());
					st.setString(4, novo.getPassword());
					st.setInt(5, 3);
					st.executeUpdate();
					st.close();
				}

			} catch (Exception e2) {
				// TODO: handle exception
			}
		}

	}

	public void removerTodos() {
		try {
			Connection con = Conexao.getConexao();
			String sql = "delete from usuarios";
			PreparedStatement pst = con.prepareStatement(sql);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void remover(String idUsu) throws UsuarioNaoCadastrado, ValorInvalido {
		pesquisar(idUsu);
		try {
			Connection con = Conexao.getConexao();
			String cmd = "delete from usuarios where identificador = ?";
			PreparedStatement pst = con.prepareStatement(cmd);
			pst.setString(1, idUsu);
			pst.executeUpdate();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
