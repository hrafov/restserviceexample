package com.epam.lab.rest.client;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.epam.lab.listeners.CustomListener;

@Listeners({ CustomListener.class })
public class TestFileRepo {
	private static final Logger log = Logger.getLogger(TestFileRepo.class);

	private static RestClient client = new RestClient();	
	private List<String> list = new ArrayList<>();
	
	@Test(priority=0)
	public void upload() {
		log.info("\n test======= upload file");
		client.uploadFile("coverfromclient.jpg");
		list = client.listOfFiles();
		Assert.assertTrue(list.size() == 3);
	}

	@Test(priority=1)
	public void delete() {
		log.info("\n test======= delete file");
		client.deleteFile("coverfromclient.jpg");
		list = client.listOfFiles();
		Assert.assertTrue(list.size() == 2);
	}

	@Test(priority=2)
	public void download() {
		log.info("\n test======= download file");
		client.downloadFile("cover1.jpg");
		list = client.listOfFiles();
		Assert.assertTrue(list.size() == 2);
	}

	@Test(priority=3)
	public void upload1mb() {
		log.info("\n test======= upload file >1Mb");
		client.uploadFile("selenium-server-standalone-3.12.0.jar");
		list = client.listOfFiles();
		Assert.assertTrue(list.size() == 2);
	}

	@Test(priority=4)
	public void download1Mb() {
		log.info("\n test======= download file > 1Mb");
		client.downloadFile("selenium-server-standalone-3.12.0.jar");
		Assert.assertTrue(list.size() == 2);
	}

	@Test(priority=5)
	public void uploadIncorrect() {
		log.info("\n test======= upload file with incorrect name");
		client.uploadFile("upload.u");
		list = client.listOfFiles();
		Assert.assertTrue(list.size() == 2);
	}

	@Test(priority=6)
	public void deleteIncorrect() {
		log.info("\n test======= delete file with incorrect name");
		client.deleteFile("dlte.jpg");
		list = client.listOfFiles();
		Assert.assertTrue(list.size() == 2);
	}

	@Test(priority=7)
	public void downloadIncorrect() {
		log.info("\n test======= download file with incorrect name");
		client.downloadFile("dwnld.jpg");
		list = client.listOfFiles();
		Assert.assertTrue(list.size() == 2);
	}

	@Test(priority=8)
	public void uploadIdempotent() {
		log.info("\n test======= idempotent upload");
		client.uploadFile("coverfromclient.jpg");
		client.uploadFile("coverfromclient.jpg");
		list = client.listOfFiles();
		Assert.assertTrue(list.size() == 3);
	}

	@Test(priority=9)
	public void deleteIdempotent() {
		log.info("\n test======= idempotent delete");
		client.deleteFile("coverfromclient.jpg");
		client.deleteFile("coverfromclient.jpg");
		list = client.listOfFiles();
		Assert.assertTrue(list.size() == 2);
	}

	@Test(priority=10)
	public void downloadIdempotent() {
		log.info("\n test======= idempotent download");
		client.downloadFile("cover1.jpg");
		client.downloadFile("cover1.jpg");
		list = client.listOfFiles();
		Assert.assertTrue(list.size() == 2);
	}

}
