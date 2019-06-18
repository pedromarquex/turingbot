package music.exception;

@SuppressWarnings("serial")
public class MusicaJaCadastrada extends Exception {
	public MusicaJaCadastrada() {
		super("Musica Já Cadastrada.");
	}
}
