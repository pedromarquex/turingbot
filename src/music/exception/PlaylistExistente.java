package music.exception;

@SuppressWarnings("serial")
public class PlaylistExistente extends Exception {
	public PlaylistExistente() {
		super("Playlist j� existente.");
	}
}
