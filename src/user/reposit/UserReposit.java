package user.reposit;

import java.util.ArrayList;

import music.Playlist;
import music.exception.PlaylistExistente;
import music.exception.PlaylistNaoExistente;
import music.exception.ValorInvalido;
import user.Banda;
import user.Usuario;
import user.exception.UsuarioJaCadastrado;
import user.exception.UsuarioNaoCadastrado;

public class UserReposit {
	private ArrayList<Usuario> clientes = new ArrayList<>();
	private Integer quantity;

	public UserReposit() {
		this.quantity = 0;
	}

	public Usuario pesquisar(String idUsu) throws ValorInvalido, UsuarioNaoCadastrado {
		if (idUsu == null || idUsu.equals(""))
			throw new ValorInvalido();
		for (Usuario x : clientes) {
			if (idUsu.equalsIgnoreCase(x.getIdUsu()))
				return x;
		}
		throw new UsuarioNaoCadastrado();
	}
	
	public void remover(String idUsu) throws ValorInvalido, UsuarioNaoCadastrado {
		Usuario forRemove = pesquisar(idUsu);
		clientes.remove(forRemove);
	}

	public void add(Usuario aux) throws ValorInvalido, UsuarioJaCadastrado {
		if(aux instanceof Banda) {
			if( ( (Banda) aux ).getArtistas() == null || ( (Banda) aux ).getArtistas().isEmpty() )
				throw new ValorInvalido();
		}
		
		if (aux.getPassword() == null || aux.getName() == null || aux.getEmail() == null || aux.getIdUsu() == null
				|| aux.getEmail().equals("") || aux.getIdUsu().equals("") || aux.getName().equals("")
				|| aux.getPassword().equals(""))
			throw new ValorInvalido();
		try {
			pesquisar(aux.getIdUsu());
			throw new UsuarioJaCadastrado();
		} catch (UsuarioNaoCadastrado e) {
			clientes.add(aux);
			quantity++;
		}
	}

	public void adicionarPlaylist(String idUsu, String nomeLista)
			throws ValorInvalido, UsuarioNaoCadastrado, PlaylistExistente {
		Usuario x = pesquisar(idUsu);
		x.adicionarPlaylist(nomeLista);
	}

	public ArrayList<Playlist> pesquisarPlaylistEstilo(String idUsu, String estilo)
			throws UsuarioNaoCadastrado, PlaylistNaoExistente {
		ArrayList<Playlist> subPlaylist = new ArrayList<>();
		Usuario user;
		try {
			user = pesquisar(idUsu);
		} catch (ValorInvalido e) {
			throw new UsuarioNaoCadastrado();
		}
		subPlaylist = user.pesquisarPlaylistPorEstilo(estilo);
		return subPlaylist;

	}

	public ArrayList<Usuario> getClientes() {
		return clientes;
	}

	public Integer getQuantity() {
		return quantity;
	}

}
