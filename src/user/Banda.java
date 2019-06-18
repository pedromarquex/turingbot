package user;

import java.util.ArrayList;

public class Banda extends Usuario{

	private ArrayList<Artista> artistas = new ArrayList<>();
	
	public Banda(String idUser, String name, String email, String password, ArrayList<Artista> array) {
		super(idUser, name, email, password);
		this.artistas = array;
	}

	public ArrayList<Artista> getArtistas() {
		return artistas;
	}	
	
}
