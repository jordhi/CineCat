package cat.jordihernandez.cinecat;

/*
 * <CINEMES>
 * 	<CINEID>37141</CINEID>
 * 	<CINENOM>Yelmo Multicines Abrera</CINENOM>
 * 	<CINEADRECA>Pol. Ind. Can Amat  Montserrat Centre Abrera, Carrer de l'Hostal del Pi, 4, 08630 Abrera</CINEADRECA>
 * 	<LOCALITAT>Abrera</LOCALITAT>
 * 	<COMARCA>Baix Llobregat</COMARCA>
 * </CINEMES>
 */

public class Cinema {
	private String Id, Nom, Adreca, Localitat, Comarca;

	public Cinema(String id, String nom, String adreca, String localitat, String comarca) {
		Id = id;
		Nom = nom;
		Adreca = adreca;
		Localitat = localitat;
		Comarca = comarca;
	}

	public Cinema() {
		Id = null;
		Nom = null;
		Adreca = null;
		Localitat = null;
		Comarca = null;
	}
	
	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getNom() {
		return Nom;
	}

	public void setNom(String nom) {
		Nom = nom;
	}

	public String getAdreca() {
		return Adreca;
	}

	public void setAdreca(String adreca) {
		Adreca = adreca;
	}

	public String getLocalitat() {
		return Localitat;
	}

	public void setLocalitat(String localitat) {
		Localitat = localitat;
	}

	public String getComarca() {
		return Comarca;
	}

	public void setComarca(String comarca) {
		Comarca = comarca;
	}
	
	
	

}
