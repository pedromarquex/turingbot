package music;

import java.util.Date;


public class Musica {
	private String nome;
	private String idUsu;
	private String nomeAutor;
	private Date releaseDate;
	private Integer duration;
	private String style;
	private String link;
	
	public Musica(String name, String idUsu, String artistName, Date releaseDate, Integer duration, String style, String link) {
		super();
		this.nome = name;
		this.idUsu = idUsu;
		this.nomeAutor = artistName;
		this.releaseDate = releaseDate;
		this.duration = duration;
		this.style = style;
		this.link = link;
	}

	public String getName() {
		return nome;
	}

	public String getIdUsu() {
		return idUsu;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public Integer getDuration() {
		return duration;
	}

	public String getStyle() {
		return style;
	}

	public String getLink() {
		return link;
	}

	public String getArtistName() {
		return nomeAutor;
	}
	
}
