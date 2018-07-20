package com.epam.lab.rest.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.google.gson.Gson;

public class RestClient { 
	
	private static final String clientDirectory = ".\\files\\";	
	
	// from http://www.mastertheboss.com/jboss-frameworks/resteasy/using-rest-services-to-manage-download-and-upload-of-files
		public void downloadFile(String fileName) {			
		    String BASE_URL="http://localhost:8080/RestService5/rest/download/" + fileName;		    
		    //Client client = ClientBuilder.newClient();
		    // TODO ? what without client ?
		    try {
		        URL website = new URL(BASE_URL);
		        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		        FileOutputStream fos = new FileOutputStream( clientDirectory + fileName );
		        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);	
		        fos.close();
		        rbc.close();
		    } catch ( FileNotFoundException e){
		    	System.out.println("=client= file " + fileName + " not found");
		    } catch ( Exception ex) {
		        ex.printStackTrace();
		    } 		      
		}		
	
		public void deleteFile(String fileName) {
			String BASE_URL="http://localhost:8080/RestService5/rest/delete/" + fileName;		    
		    Client client = ClientBuilder.newClient();
		    client.target(BASE_URL).request().delete();
		    System.out.println("=client= file: " + fileName + " deleted if existed");
		}	
	
	// https://www.jeejava.com/upload-file-using-rest-webservice/		
		public void uploadFile( String fileName ){
			String BASE_URL="http://localhost:8080/RestService5/rest/upload/" + fileName;
			String filePath = clientDirectory + fileName;
			try {
				File file = new File(filePath);
				InputStream fileInStream = new FileInputStream(file);
				String contentDisposition = "attachment; filename=" + fileName;
				Client client = ClientBuilder.newClient();
				Response response = client.target(BASE_URL).request()
						.header("Content-Disposition", contentDisposition)
						.post(Entity.entity(fileInStream, MediaType.APPLICATION_OCTET_STREAM));
				System.out.println(response.readEntity(String.class));
			} catch (FileNotFoundException e) {
				System.out.println("=client= file: " + fileName + " not found");
			}
		}	
		
		public List<String> listOfFiles() {			
			List<String> list = new ArrayList<>();
			Gson gson = new Gson();
			String BASE_URL="http://localhost:8080/RestService5/rest/listOfFiles/";			
			try {				
			    Client client = ClientBuilder.newClient();			    
			    Response response = client.target(BASE_URL).request().get();			    
		        String output = response.readEntity(String.class);		        		        
		        String[] s = gson.fromJson(output, String[].class);				
				System.out.println("=client= number of files = " + s.length);
				for (int i = 0; i < s.length; i++) {
					list.add(s[i]);
					System.out.println("=client= file: " + list.get(i));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}		
			return list;
		}
}
