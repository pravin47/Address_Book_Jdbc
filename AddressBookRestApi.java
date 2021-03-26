package com.address.book.jdbc;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.sql.address.AddressBookData;
import com.sql.address.AddressBookService;
import com.sql.address.AddressBookService.IOService;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AddressBookRESTAPI {
	@Before
	public void Setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
	}

	@Test
	public void givenAddressInJSONServer_whenRetrieved_ShouldMatchTheCount() {
		AddressBookData[] arrayOfEmps = getAddressBookList();
		AddressBookService service = new AddressBookService(Arrays.asList(arrayOfEmps));
		long entries = service.countEntries();
		Assert.assertEquals(3, entries);
	}

	private AddressBookData[] getAddressBookList() {
		Response response = RestAssured.get("/persons");
		System.out.println("AddressBook ENTRIES IN JSONServer:\n" + response.asString());
		AddressBookData[] arrayOfBooks = new Gson().fromJson(response.asString(), AddressBookData[].class);
		return arrayOfBooks;
	}

	@Test
	public void givenNewPerson_WhenAdded_ShouldMatch201ResponseAndCount() {
		AddressBookService service;
		AddressBookData[] ArrayOfEmps = getAddressBookList();
		service = new AddressBookService(Arrays.asList(ArrayOfEmps));
		AddressBookData personData = new AddressBookData(20, "pravin", "bagul", "ram nagar", "sakri", "maharashtra", "424304", "9767593660","prbagul@gmail.com"),
		Response response = addPersonToJsonServer(personData);
		int statusCode = response.getStatusCode();
		Assert.assertEquals(201, statusCode);
		personData = new Gson().fromJson(response.asString(), AddressBookData.class);
		service.addPerson(personData, com.sql.address.AddressBookService.IOService.REST_IO);
		long entries = service.countEntries();
		Assert.assertEquals(3, entries);
	}

	private Response addPersonToJsonServer(AddressBookData employeePayrollData) {
		String empJson = new Gson().toJson(employeePayrollData);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(empJson);
		return request.post("/persons");
	}

	@Test
	public void givenNewPersons_WhenAdded_ShouldMatch201ResponseAndCount() {
		AddressBookService service;
		AddressBookData[] ArrayOfEmps = getAddressBookList();
		service = new AddressBookService(Arrays.asList(ArrayOfEmps));
		AddressBookData[] arrayPersonData = {
				new AddressBookData(21, "rajendra", "deshmukh", "nagai Colony", "dhule", "odisha", "5265245",
						"8811523658", "raj@gmail.com"),
				new AddressBookData(14, "vijay", "chaudhari", "adarsh nagar", "aurangabad", "gujrat", "856525",
						"5425625656", "vijay@gmail.com"), };
		for (AddressBookData personData : Arrays.asList(arrayPersonData)) {
			Response response = addPersonToJsonServer(personData);
			int statusCode = response.getStatusCode();
			Assert.assertEquals(201, statusCode);
			personData = new Gson().fromJson(response.asString(), AddressBookData.class);
			service.addPerson(personData, com.sql.address.AddressBookService.IOService.REST_IO);
		}
		long entries = service.countEntries();
		Assert.assertEquals(5, entries);
	}

	@Test
	public void givenNewCityForPerson_WhenUpdated_ShouldMatch200ResponseAndCount() {
		AddressBookService service;
		AddressBookData[] ArrayOfEmps = getAddressBookList();
		service = new AddressBookService(Arrays.asList(ArrayOfEmps));
		service.updatePersonCity("mno", "newCity1", com.sql.address.AddressBookService.IOService.REST_IO);
		AddressBookData personData = service.getAddressBookData("mno");
		String personJson = new Gson().toJson(personData);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(personJson);
		Response response = request.put("/persons/" + personData.id);
		int statusCode = response.getStatusCode();
		Assert.assertEquals(200, statusCode);
	}

	@Test
	public void givenEmployeeName_WhenDeleted_ShouldMatch200ResponseAndCount() {
		AddressBookService service;
		AddressBookData[] ArrayOfEmps = getAddressBookList();
		service = new AddressBookService(Arrays.asList(ArrayOfEmps));
		AddressBookData personData = service.getAddressBookData("jeff");
		String personJson = new Gson().toJson(personData);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		Response response = request.delete("/persons/" + personData.id);
		int statusCode = response.getStatusCode();
		Assert.assertEquals(200, statusCode);
		service.deletePersonData(personData.first_name, IOService.REST_IO);
		long entries = service.countEntries();
		Assert.assertEquals(4, entries);
	}
}