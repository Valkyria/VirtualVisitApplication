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
	
	public Webservice(){
		ListUser = new ArrayList<User>();
	}
	
	public ArrayList<User> getAllVacanciers(){                 
		
		try {
			URL url = new URL("http://vva-project.cyxis-games.info/Webservice/allUsers");
			//URL url = new URL("http://vvaprojectcedrict.olikeopen.com/Webservice/allUsers"); //Down (free)
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
	
}
	


