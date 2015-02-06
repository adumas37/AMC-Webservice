package m2.hw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.annotate.JsonProperty;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;



@Path("creationQuestionnaireJSON")
@Consumes(MediaType.APPLICATION_JSON)
public class CreationQuestionnaireJSON {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response creation(String data,@Context UriInfo context){
		
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		//On parse les données reçues pour créer l'objet JSON
		JsonObject obj = parser.parse(data).getAsJsonObject();
		//On converti l'objet JSON en une classe Java définie par nos soins
		//Il faut que cette classe possède des attributs repérables par Jersey 
		//ex: @JsonProperty("nom") private String nom;
		Questionnaire quest=gson.fromJson(obj,Questionnaire.class);
		System.out.println(quest);
		
		String url = context.getBaseUri().toString();
		url=url.substring(0,url.length()-5); //Supression du "rest/" a la fin de l'url
		URI uri = UriBuilder.fromUri(url)
				.path("{a}")
				.build("Projet.html");
		return Response.seeOther(uri).build();
	}
	
}
