package com.address.book.jdbc;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import com.sql.address.*;
import com.sql.address.AddressBookService.IOService;

public class AddressBookJDBCTest {

	@Test
	public void given3Contacts_WhenAdded_ShouldMatchContactsCount() {
		AddressBookData[] addBookData = {
				new AddressBookData(20, "pravin", "bagul", "ram nagar", "sakri", "maharashtra", "424304", "9767593660","prbagul@gmail.com"),
				new AddressBookData(21, "rajendra", "deshmukh", "nagai Colony", "dhule", "odisha", "5265245", "8811523658","raj@gmail.com"),
				new AddressBookData(14, "vijay", "chaudhari", "adarsh nagar", "aurangabad", "gujrat", "856525", "5425625656","vijay@gmail.com"), };
		AddressBookService addBookService = new AddressBookService();
		addBookService.readAddressBookData(IOService.DB_IO);
		Instant threadStart = Instant.now();
		addBookService.addContactsWithThreads(Arrays.asList(addBookData));
		Instant threadEnd = Instant.now();
		System.out.println("Duration with thread : " + Duration.between(threadStart, threadEnd));
		List<AddressBookData> addressBookData = addBookService.readAddressBookData(IOService.DB_IO);
		System.out.println(addressBookData.size());
		Assert.assertEquals(14, addressBookData.size());
	}

}