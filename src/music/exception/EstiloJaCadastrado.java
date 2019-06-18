package music.exception;

@SuppressWarnings("serial")
public class EstiloJaCadastrado extends Exception {
	public EstiloJaCadastrado() {
		super("Estilo Já Cadastrado");
	}
}
