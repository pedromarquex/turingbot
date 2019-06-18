package music.exception;

@SuppressWarnings("serial")
public class MusicaNaoCadastrada extends Exception {
	public MusicaNaoCadastrada() {
		super("Musica Não Cadastrada.");
	}
}
