package com.model;

public class Menu {
	private String nom, date, plat;
	
	public Menu( String nom, String date, String plat){
		this.setNom(nom);
		this.setDate(date);
		this.setPlat(plat);
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPlat() {
		return plat;
	}

	public void setPlat(String plat) {
		this.plat = plat;
	}
}
