package com.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonValue.JsonIterator;

//Liste des web services: http://vva-project.cyxis-games.info/Webservice
public class Webservice {
	
	private ArrayList<User> ListUser;
	private ArrayList<Menu> ListMenu;
	private String urltxt = "http://vva-project.cyxis-games.info/";
	//private final static String urltxt = "http://vvaprojectcedrict.olikeopen.com/"; //Down (free)
	
	public Webservice(){
		ListUser = new ArrayList<User>();
	}
	
	public ArrayList<User> getAllVacanciers(){                 
		
		try {
			URL url = new URL(urltxt+"Webservice/allUsers");
			URLConnection conn = url.openConnection();
			
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String Jstring;
			while ((Jstring = rd.readLine()) != null) {
				JsonValue json = new JsonReader().parse(Jstring);
				JsonIterator it = json.iterator();
				while(it.hasNext()){
					
					JsonValue userJson = it.next();
					String Type = userJson.get("Webservice").getString("type_profil_id");
					if(Type.equals("Vacancier")){
							ListUser.add(new User(userJson.get("Webservice").getString("id"), userJson.get("Webservice").getString("MDP"), 
								userJson.get("Webservice").getString("NOMPROFIL"), userJson.get("Webservice").getString("PRENOMPROFIL"), 
								userJson.get("Webservice").getString("DATEDEBSEJOUR"), userJson.get("Webservice").getString("DATEFINSEJOUR"), 
								userJson.get("Webservice").getString("DATEINSCRIP"), userJson.get("Webservice").getString("type_profil_id")));
					}
				}
			}
			rd.close();
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		return ListUser;
	}
	
	public ArrayList<Menu> getAllMenu(){                 
		
		URL url = null;
		try {
			url = new URL(urltxt+"Webservice/allMenus");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return this.getListMenu(url);
	}
	
	public ArrayList<Menu> getCurrentMenu(){                 
		
		URL url = null;
		try {
			url = new URL(urltxt+"Webservice/getCurrentMenu");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return this.getListMenu(url);
	}
	
	public ArrayList<Menu> getUserMenu(String id){                 
		URL url = null;
		try {
			url = new URL(urltxt+"Webservice/getRepas/"+id);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return this.getListMenu(url);
	}
	
	private ArrayList<Menu> getListMenu(URL url){
		ListMenu = new ArrayList<Menu>();
		try {
			
			URLConnection conn = url.openConnection();
			
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String Jstring;
			while ((Jstring = rd.readLine()) != null) {
				JsonValue json = new JsonReader().parse(Jstring);
				JsonIterator it = json.iterator();
				while(it.hasNext()){
					
					JsonValue menu = it.next();
					int size = menu.get("plat").size;
					String plats = "";
					for (int i=0; i<size; i++){
						plats = plats+menu.get("plat").get(i).toString()+"\n";
					}
					ListMenu.add(new Menu(menu.getString("nom").toString(), menu.getString("date").toString(), plats));
				}
			}
			rd.close();
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		return ListMenu;
	}
}
	


