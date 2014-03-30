package com.model;

public class User {
	private String id, mdp, nom, prenom, DtDebut, DtFin, DtInscription, type_profil;
	
	public User( String id, String mdp, String nom, String prenom, String DtDebut, String DtFin, String DtInscription, String type_profil){
		this.setId(id);
		this.setMdp(mdp);
		this.nom = nom;
		this.prenom = prenom;
		this.DtDebut = DtDebut;
		this.DtFin = DtFin;
		this.DtInscription = DtInscription;
		this.type_profil = type_profil;
		
	}
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getDtDebut() {
		return DtDebut;
	}

	public void setDtDebut(String dtDebut) {
		DtDebut = dtDebut;
	}

	public String getDtFin() {
		return DtFin;
	}

	public void setDtFin(String dtFin) {
		DtFin = dtFin;
	}

	public String getDtInscription() {
		return DtInscription;
	}

	public void setDtInscription(String dtInscription) {
		DtInscription = dtInscription;
	}

	public String getType_profil() {
		return type_profil;
	}

	public void setType_profil(String type_profil) {
		this.type_profil = type_profil;
	}
	public String getMdp() {
		return mdp;
	}
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
