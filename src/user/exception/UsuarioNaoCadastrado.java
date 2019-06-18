package user.exception;

@SuppressWarnings("serial")
public class UsuarioNaoCadastrado extends Exception {
	public UsuarioNaoCadastrado() {
		super("Usuario Não Cadastrado.");
	}
}
