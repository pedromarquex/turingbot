package user.exception;

@SuppressWarnings("serial")
public class UsuarioJaCadastrado extends Exception {
	public UsuarioJaCadastrado() {
		super("Usuario Já Cadastrado.");
	}
}
