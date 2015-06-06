package cat.jordihernandez.cinecat;

/*
 * <SESSIONS>
 * 	<IDFILM>28166</IDFILM>
 * 	<ses_id>1</ses_id>
 * 	<CINEID>37172</CINEID>
 * 	<TITOL>Samba</TITOL>
 * 	<ses_data>18/03/2015</ses_data>
 * 	<CINENOM>Cinesa Sant Cugat</CINENOM>
 * 	<LOCALITAT>Sant Cugat del Vallès</LOCALITAT>
 * 	<COMARCA>Vallès Occidental</COMARCA>
 * 	<CICLEID>1</CICLEID>
 * 	<ver>VOSC</ver>
 * </SESSIONS>
 */
public class Sessio {
	private String idfilm, sesid, cineid, titol, sesdata, cinenom, localitat, comarca, cicleid, ver;
	private final String RES = "--";
	
	public Sessio(String idfilm, String sesid, String cineid, String titol,
			String sesdata, String cinenom, String localitat, String comarca, String cicleid,
			String ver) {
		this.idfilm = idfilm;
		this.sesid = sesid;
		this.cineid = cineid;
		this.titol = titol;
		this.sesdata = sesdata;
		this.cinenom = cinenom;
		this.localitat = localitat;
		this.comarca = comarca;
		this.cicleid = cicleid;
		this.ver = ver;
	}
	
	public Sessio() {
		this.idfilm = RES;
		this.sesid = RES;
		this.cineid = RES;
		this.titol = RES;
		this.sesdata = RES;
		this.cinenom = RES;
		this.localitat = RES;
		this.comarca = RES;
		this.cicleid = RES;
		this.ver = RES;
	}

	public String getCinenom() {
		return cinenom;
	}

	public void setCinenom(String cinenom) {
		this.cinenom = cinenom;
	}

	public String getIdfilm() {
		return idfilm;
	}

	public void setIdfilm(String idfilm) {
		this.idfilm = idfilm;
	}

	public String getSesid() {
		return sesid;
	}

	public void setSesid(String sesid) {
		this.sesid = sesid;
	}

	public String getCineid() {
		return cineid;
	}

	public void setCineid(String cineid) {
		this.cineid = cineid;
	}

	public String getTitol() {
		return titol;
	}

	public void setTitol(String titol) {
		this.titol = titol;
	}

	public String getSesdata() {
		return sesdata;
	}

	public void setSesdata(String sesdata) {
		this.sesdata = sesdata;
	}

	public String getLocalitat() {
		return localitat;
	}

	public void setLocalitat(String localitat) {
		this.localitat = localitat;
	}

	public String getComarca() {
		return comarca;
	}

	public void setComarca(String comarca) {
		this.comarca = comarca;
	}

	public String getCicleid() {
		return cicleid;
	}

	public void setCicleid(String cicleid) {
		this.cicleid = cicleid;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}
	
	

}
