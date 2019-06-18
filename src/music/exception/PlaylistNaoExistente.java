package music.exception;

@SuppressWarnings("serial")
public class PlaylistNaoExistente extends Exception {
	public PlaylistNaoExistente() {
		super("Playlist Não Existente.");
	}
}
