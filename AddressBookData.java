package com.address.book.jdbc;

public class AddressBookData {
	public int id;
	public String first_name;
	public String last_name;
	public String address;
	public String city;
	public String state;
	public String zip;
	public String phone_no;
	public String email;

	public AddressBookData(int id, String first_name, String last_name, String address, String city, String state,
			String zip, String phone, String email) {
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phone_no = phone_no;
		this.email = email;
	}

	@Override
	public String toString() {
		return "id=" + id + ", name=" + first_name + " " + last_name + " ";
	}

}