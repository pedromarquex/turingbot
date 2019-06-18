package program;

import java.sql.SQLException;
import java.util.ArrayList;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import music.exception.MusicaJaCadastrada;
import music.exception.MusicaNaoCadastrada;
import music.exception.PlaylistExistente;
import music.exception.PlaylistNaoExistente;
import music.exception.ValorInvalido;
import music.reposit.DAOEstilo;
import music.reposit.DAOMusica;
import music.reposit.DAOMusicaPlaylist;
import music.reposit.DAOPlaylist;
import user.Usuario;
import user.exception.UsuarioNaoCadastrado;
import user.reposit.DAOUsuarios;

public class LittleTuring extends TelegramLongPollingBot {
	DAOMusicaPlaylist musicaplaylistDAO = new DAOMusicaPlaylist();
	DAOEstilo estiloDAO = new DAOEstilo();
	DAOMusica musicaDAO = new DAOMusica();
	DAOPlaylist playlistDAO = new DAOPlaylist();
	DAOUsuarios usuarioDAO = new DAOUsuarios();

	String playlistAtual = null;
	String musicaAtual = null;

	ArrayList<FlagUsuarios> flagUsuarios = new ArrayList<>();

	@Override
	public String getBotUsername() {
		return "LittleTuringBot";
	}

	// Enviando mensagem do menu.
	public void menu(String idUsu, long chatID) {
		SendMessage send = new SendMessage();
		send.setChatId(chatID);
		Usuario usuario;
		try {
			usuario = usuarioDAO.pesquisar(idUsu);
			send.setText("Olá " + usuario.getName() + " , bem vindo de volta!\n\n" + "O que você deseja?\n"
					+ "    1 - Criar Playlist\n" + "    2 - Remover Playlist\n"
					+ "    3 - Alterar o nome de uma Playlist\n" + "    4 - Adicionar Musica à Playlist\n"
					+ "    0 - Sair\n\n" + "Digite o número correspondente ao comando que deseja executar: ");
			execute(send);
		} catch (ValorInvalido | UsuarioNaoCadastrado | TelegramApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// percorrendo vetor de flags at� encontrar a flag correspondente ao idUsu.
	public FlagUsuarios buscarFlag(String idUsu) {
		FlagUsuarios flagAtual = null;
		for (FlagUsuarios x : flagUsuarios) {
			if (x.getIdUsu().equals(idUsu)) {
				flagAtual = x;
			}
		}
		if (flagAtual == null) {
			flagAtual = new FlagUsuarios(idUsu);
			flagUsuarios.add(flagAtual);
		}

		return flagAtual;
	}

	public void valorInvalido(long chatID) {
		SendMessage send = new SendMessage();
		send.setChatId(chatID);
		send.setText("Digite um valor v�lido.");
		try {
			execute(send);
		} catch (TelegramApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void playlistNameMensage(long chatID) {
		SendMessage send = new SendMessage();
		send.setChatId(chatID);
		send.setText("Digite o nome da playlist: ");
		try {
			execute(send);
		} catch (TelegramApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void playlistExistenteMensage(long chatID) {
		SendMessage send = new SendMessage();
		send.setChatId(chatID);
		send.setText("Nome de playlist já em uso, digite outro nome: ");
		try {
			execute(send);
		} catch (TelegramApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void playlistInseridaMensage(long chatID, String playlistNome) {
		SendMessage send = new SendMessage();
		send.setChatId(chatID);
		send.setText("A playlist [ " + playlistNome + " ] foi adicianada a sua biblioteca.");
		try {
			execute(send);
		} catch (TelegramApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void playlistNaoExistenteMensage(long chatID) {
		SendMessage send = new SendMessage();
		send.setChatId(chatID);
		send.setText("A playlist procurada não existe, digite novamente: ");
		try {
			execute(send);
		} catch (TelegramApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void playlistRemovidaMensage(long chatID, String playlistNome) {
		SendMessage send = new SendMessage();
		send.setChatId(chatID);
		send.setText("A playlist [ " + playlistNome + " ] foi removida de sua biblioteca.");
		try {
			execute(send);
		} catch (TelegramApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void musicaNameMensage(long chatID) {
		SendMessage send = new SendMessage();
		send.setChatId(chatID);
		send.setText("Digite o nome da música :");
		try {
			execute(send);
		} catch (TelegramApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onUpdateReceived(Update u) {
		SendMessage send = new SendMessage();
		String mensagem = u.getMessage().getText();
		System.out.println(u.getMessage());
		String idUsu = u.getMessage().getFrom().getUserName();
		long chatID = u.getMessage().getChatId();

		FlagUsuarios flagAtual = buscarFlag(idUsu);
		
		if (mensagem.equals("/menu")) {
			flagAtual.inicioMenu();
			menu(idUsu, chatID);
		} else if (mensagem.equals("/reset")) {
			flagAtual.resetar();
		} else if (mensagem.equals("/help")) {
			send.setChatId(chatID);
			send.setText("Bem vindo ao Turing BOT!\n\n"
					+ "Use os números para interagir entre o menu.\n"
					+ "Lembre-se do comando /reset, que volta o bot para o inicio.\n"
					+ "\n Created by : Paulo & Pedro.");
			try {
				execute(send);
			} catch (TelegramApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (flagAtual.isNovo()) {
			menu(idUsu, chatID);
			flagAtual.inicioMenu();

		} else if (flagAtual.isMenu()) {
			switch (mensagem) {
			case "1":
				flagAtual.criarPlaylist();
				playlistNameMensage(chatID);
				break;
			case "2":
				flagAtual.removerPlaylist();
				playlistNameMensage(chatID);
				break;
			case "3":
				flagAtual.alterarPlaylist();
				playlistNameMensage(chatID);
				break;
			case "4":
				flagAtual.adicionarMusicaPlaylist();
				playlistNameMensage(chatID);
				break;
			case "0":
				flagAtual.resetar();
				send.setChatId(chatID);
				send.setText("Vou sentir saudade, adeus.");
				try {
					execute(send);
				} catch (TelegramApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			default:
				valorInvalido(chatID);
				break;
			}
		} else if (flagAtual.isCriar()) {
			try {
				playlistDAO.pesquisar(mensagem, idUsu);
				playlistExistenteMensage(chatID);
			} catch (PlaylistNaoExistente e) {
				try {
					playlistDAO.inserir(mensagem, idUsu);
					playlistInseridaMensage(chatID, mensagem);
					flagAtual.resetar();
				} catch (UsuarioNaoCadastrado | ValorInvalido | PlaylistExistente e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} else if (flagAtual.isRemover()) {
			try {
				playlistDAO.pesquisar(mensagem, idUsu);
				playlistDAO.remover(mensagem, idUsu);
				playlistRemovidaMensage(chatID, mensagem);
				flagAtual.resetar();
			} catch (PlaylistNaoExistente e) {
				playlistNaoExistenteMensage(chatID);
			}
		} else if (flagAtual.isAlterar()) {
			try {
				playlistDAO.pesquisar(mensagem, idUsu);
				playlistDAO.remover(mensagem, idUsu);
				send.setText("Digite um novo nome para a playlist :");
				send.setChatId(chatID);
				execute(send);
				flagAtual.criarPlaylist();
			} catch (PlaylistNaoExistente | TelegramApiException e) {
				playlistNaoExistenteMensage(chatID);
			}
		} else if (flagAtual.isAdicionarmusica()) {
			try {
				playlistDAO.pesquisar(mensagem, idUsu);
				playlistAtual = mensagem;
				flagAtual.adicionarMusicaPlaylist2();
				musicaNameMensage(chatID);
			} catch (PlaylistNaoExistente e) {
				playlistNaoExistenteMensage(chatID);
			}
		} else if (flagAtual.isAdicionarmusica2()) {
			musicaAtual = mensagem;
			send.setChatId(chatID);
			send.setText("Digite o nome do artista dono da musica: ");
			try {
				execute(send);
			} catch (TelegramApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			flagAtual.adicionarMusicaPlaylist3();

		} else if (flagAtual.isAdicionarmusica3()) {
			try {
				musicaDAO.pesquisar(musicaAtual, mensagem);
				musicaplaylistDAO.inserir(idUsu, playlistAtual, musicaAtual, mensagem);
				send.setText("Musica [ " + musicaAtual + " ] foi adicionada à playlist [ " + playlistAtual + " ]");
				send.setChatId(chatID);
				flagAtual.resetar();
				try {
					execute(send);
				} catch (TelegramApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (ValorInvalido | UsuarioNaoCadastrado | PlaylistNaoExistente | MusicaJaCadastrada
					| MusicaNaoCadastrada | SQLException e) {
				e.printStackTrace();
				send.setText("Musica não cadastrada, digite o nome da musica novamente.");
				send.setChatId(chatID);
				flagAtual.adicionarMusicaPlaylist2();
				try {
					execute(send);
				} catch (TelegramApiException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	@Override
	public String getBotToken() {
		// Token gerado na criação do bot
		return "614765803:AAEKv0CQzYMR0-bzKtiT9dAN5TdoYjHGyVw";
	}

	public static void main(String[] args) {
		ApiContextInitializer.init();
		TelegramBotsApi telegramBot = new TelegramBotsApi();
		LittleTuring bot = new LittleTuring();

		try {
			telegramBot.registerBot(bot);
		} catch (TelegramApiRequestException e) {
			System.out.println("Erro no Bot Pasn");
			e.printStackTrace();
		}
	}
}
