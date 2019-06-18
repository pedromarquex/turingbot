package music.reposit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import music.exception.EstiloJaCadastrado;
import music.exception.EstiloNaoCadastrado;
import music.exception.ValorInvalido;
import program.Conexao;

public class DAOEstilo {
	
	public ArrayList<String> pesquisar(String estilo) throws EstiloNaoCadastrado {
		try {
			Connection con = Conexao.getConexao();
			String cmd = "select * from estilos where nome = " + estilo;
			PreparedStatement st = con.prepareStatement(cmd);
			
			
			ResultSet rs = st.executeQuery(cmd);
			ArrayList<String> estilos = new ArrayList<>();
			while(rs.next()) {
				estilos.add(rs.getString("nome"));
			}
			if(estilos.isEmpty()) {
				throw new EstiloNaoCadastrado();
			}
			st.close();
			return estilos;
		} catch (Exception e) {
			// TODO: handle exception
		}
		throw new EstiloNaoCadastrado();
	}
	
	public void inserir(String estilo) throws EstiloJaCadastrado, ValorInvalido {
		if(estilo == null || estilo.equals(""))
			throw new ValorInvalido();
		try {
			pesquisar(estilo);
			throw new EstiloJaCadastrado();
		} catch (EstiloNaoCadastrado e) {
			Connection con = Conexao.getConexao();
			String cmd = "insert into estilos values = \'" + estilo + "\'"; 
			PreparedStatement st = null;
			try {
				st = con.prepareStatement(cmd);
				st.executeUpdate(cmd);
				st.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void removerTodos() {
		try {
			Connection con = Conexao.getConexao();
			String cmd = "delete from estilos";
			PreparedStatement st = con.prepareStatement(cmd);
			st.executeUpdate(cmd);
			st.close();
		} catch (Exception e) {

		}
	}
	
	public void remover(String estilo) throws EstiloNaoCadastrado {
		pesquisar(estilo);
		try {
			Connection con = Conexao.getConexao();
			String cmd = "delete from estilos where = \'" + estilo + "\'";
			PreparedStatement st = con.prepareStatement(cmd);
			st.executeUpdate(cmd);
			st.close();
		} catch (Exception e) {

		}
	}
}
