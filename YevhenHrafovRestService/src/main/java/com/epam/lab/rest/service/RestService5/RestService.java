package com.epam.lab.rest.service.RestService5;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import com.google.gson.Gson;

@Path("/")
public class RestService {
	//private static final Logger log = Logger.getLogger(RestService.class);
	public RestService() {
	}
	private static final String serviceDirectory = ".\\files\\";
	
	// http://localhost:8080/RestService5/rest/download
	@GET
	@Path("/download/{fileName}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response downloadFile(@PathParam("fileName") String fileName) {
		File file = new File(serviceDirectory + fileName);		
		if (file.length() > 1048576) {			
			return Response.status(Response.Status.OK).entity("=server= file: " + fileName + " > 1Mb! and will not be uploaded")
					.type(MediaType.TEXT_PLAIN).build();
		} else {		
			if (file.exists()) {
				ResponseBuilder response = Response.ok((Object) file);
				response.header("Content-Disposition", "attachment;filename=" + fileName);
				return response.build();
			} else {
				return Response.status(404).entity("=server= file not found").build();
			}
		}
	}

	@DELETE
	@Path("/delete/{fileName}")
	public Response deleteFile(@PathParam("fileName") String fileName) {
		File file = new File(serviceDirectory + fileName);
		if (file.exists()) {
			file.delete();
			return Response.status(200).entity("=server= file deleted").build();
		} else {
			return Response.status(404).entity("=server= file not found").build();
		}
	}

	// https://www.jeejava.com/upload-file-using-rest-webservice/
	@POST
	@Path("/upload/{fileName}")
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	public Response uploadFile(@PathParam("fileName") String fileName, InputStream fileInputStream) {
		OutputStream out = null;
		String filePath = serviceDirectory + fileName;
		File file = new File(filePath);
		if (file.length() > 1048576) {
			//log.info("=service= file: " + fileName + " > 1Mb! and will not be uploaded");
			return Response.status(Response.Status.OK).entity("=server= file: " + fileName + " > 1Mb! and will not be uploaded")
					.type(MediaType.TEXT_PLAIN).build();
		} else {
			try {
				out = new FileOutputStream(new File(filePath));
				byte[] buf = new byte[1024];
				int len;
				while ((len = fileInputStream.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					out.close();
					fileInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return Response.status(Response.Status.OK).entity("=server= file: " + fileName + " uploaded successfully")
					.type(MediaType.TEXT_PLAIN).build();
		}
	}

	@GET
	@Path("/listOfFiles")
	@Produces(MediaType.APPLICATION_XML)
	public Response listOfFiles() {
		File directory = new File(serviceDirectory);
		File[] fList = directory.listFiles();
		//log.info("=service= absolute path = " + directory.getAbsolutePath());
		String[] s = new String[fList.length];
		for (int i = 0; i < fList.length; i++) {
			if (fList[i].isFile()) {
				s[i] = fList[i].getName();
			} else {
				s[i] = null;
			}
		}
		Gson gson = new Gson();
		return Response.ok().entity(gson.toJson(s)).build();
	}

}