package cat.jordihernandez.cinecat;

public class Cicle {

	private String id, nom, info, img;
	private final String RES = "--";

	public Cicle(String id, String nom, String info, String img) {
		this.id = id;
		this.nom = nom;
		this.info = info;
		this.img = img;
	}

	public Cicle() {
		this.id = RES;
		this.nom = RES;
		this.info = RES;
		this.img = RES;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getRES() {
		return RES;
	}
	
	
}
