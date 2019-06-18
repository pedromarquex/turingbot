package program;

public class FlagUsuarios {
		private String idUsu = "";
		private boolean novo = true;
		private boolean menu = false;
		private boolean criar = false;
		private boolean remover = false;
		private boolean alterar = false;
		private boolean adicionarmusica = false;
		private boolean adicionarmusica2 = false;
		private boolean adicionarmusica3 = false;
		
		public FlagUsuarios(String idUsu) {
			this.setIdUsu(idUsu);
		}
		
		public void resetar() {
			novo = true;
			menu = false;
			criar = false;
			remover = false;
			alterar = false;
			adicionarmusica = false;
			adicionarmusica2 = false;
			setAdicionarmusica3(false);
		}
		
		public void criarPlaylist() {
			novo = false;
			menu = false;
			criar = true;
			remover = false;
			alterar = false;
			adicionarmusica = false;
			adicionarmusica2 = false;
			setAdicionarmusica3(false);
		}
		
		public void removerPlaylist() {
			novo = false;
			menu = false;
			criar = false;
			remover = true;
			alterar = false;
			adicionarmusica = false;
			adicionarmusica2 = false;
			setAdicionarmusica3(false);
		}
		
		public void alterarPlaylist() {
			novo = false;
			menu = false;
			criar = false;
			remover = false;
			alterar = true;
			adicionarmusica = false;
			adicionarmusica2 = false;
			setAdicionarmusica3(false);
		}
		
		public void adicionarMusicaPlaylist() {
			novo = false;
			menu = false;
			criar = false;
			remover = false;
			alterar = false;
			adicionarmusica = true;
			adicionarmusica2 = false;
			setAdicionarmusica3(false);
		}
		
		public void adicionarMusicaPlaylist2() {
			novo = false;
			menu = false;
			criar = false;
			remover = false;
			alterar = false;
			adicionarmusica = false;
			adicionarmusica2 = true;
			setAdicionarmusica3(false);
			
		}
		
		public void adicionarMusicaPlaylist3() {
			novo = false;
			menu = false;
			criar = false;
			remover = false;
			alterar = false;
			adicionarmusica = false;
			adicionarmusica2 = false;
			setAdicionarmusica3(true);
		}
		
		public void inicioMenu() {
			novo = false;
			menu = true;
			criar = false;
			remover = false;
			alterar = false;
			adicionarmusica = false;
			adicionarmusica2 = false;
			setAdicionarmusica3(false);
		}

		public boolean isMenu() {
			return menu;
		}

		public void setMenu(boolean inicio) {
			this.menu = inicio;
		}

		public boolean isCriar() {
			return criar;
		}

		public void setCriar(boolean criar) {
			this.criar = criar;
		}

		public boolean isRemover() {
			return remover;
		}

		public void setRemover(boolean remover) {
			this.remover = remover;
		}

		public boolean isAlterar() {
			return alterar;
		}

		public void setAlterar(boolean alterar) {
			this.alterar = alterar;
		}

		public boolean isAdicionarmusica() {
			return adicionarmusica;
		}

		public void setAdicionarmusica(boolean adicionarmusica) {
			this.adicionarmusica = adicionarmusica;
		}

		public String getIdUsu() {
			return idUsu;
		}

		public void setIdUsu(String idUsu) {
			this.idUsu = idUsu;
		}

		public boolean isNovo() {
			return novo;
		}

		public void setNovo(boolean novo) {
			this.novo = novo;
		}

		public boolean isAdicionarmusica2() {
			return adicionarmusica2;
		}

		public void setAdicionarmusica2(boolean adicionarmusica2) {
			this.adicionarmusica2 = adicionarmusica2;
		}

		public boolean isAdicionarmusica3() {
			return adicionarmusica3;
		}

		public void setAdicionarmusica3(boolean adicionarmusica3) {
			this.adicionarmusica3 = adicionarmusica3;
		}
}
