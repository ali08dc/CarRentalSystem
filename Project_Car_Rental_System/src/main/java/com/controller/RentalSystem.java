package com.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.model.Customer;

public class RentalSystem {

	// AVAILABLE CARS

	public ResultSet getAvailableCars(Connection con) {

		ResultSet rs = null;

		try {
			PreparedStatement psmt = con.prepareStatement("Select * from car");
			rs = psmt.executeQuery();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return rs;
	}

	// RENTED CAR

	public ResultSet getRentedCars(Connection con) {
		ResultSet rs = null;

		try {
			PreparedStatement psmt = con.prepareStatement("Select * from rentedcar");
			rs = psmt.executeQuery();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return rs;
	}

	// SAVEING CUSTOMER DATA

	public void saveCustomerData(Connection con, Customer cs) {
		try {
			PreparedStatement psmt = con.prepareStatement("insert into customer values(?,?,?,?)");

			psmt.setInt(1, cs.getCustomerId());
			psmt.setString(2, cs.getName());
			psmt.setLong(3, cs.getMobNo());
			psmt.setLong(4, cs.getAdhaarNo());

			boolean n = psmt.execute();

//			if(n == false) {
//				System.out.println("Customer Data Saved..");
//			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	// Saving Car detail to Car Table

	public void saveCarData(Connection con, ResultSet rs6) {

		try {
			PreparedStatement psmt = con.prepareStatement("insert into car values(?,?,?,?)");
			boolean n = true;
			while (rs6.next()) {

				psmt.setString(1, rs6.getString(2));
				psmt.setString(2, rs6.getString(3));
				psmt.setInt(3, rs6.getInt(4));
				psmt.setString(4, rs6.getString(1));

				n = psmt.execute();
			}
			if (n == false) {
				System.out.println("Car Returned Successfully...");
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	// Saving rented Car Details

	public void saveRentedCar(Connection con, ResultSet rs4, Customer cst) {

		try {
			PreparedStatement psmt = con.prepareStatement("insert into rentedcar values(?,?,?,?,?)");
			boolean n = true;
			while (rs4.next()) {

				psmt.setString(1, rs4.getString(4));
				psmt.setString(2, rs4.getString(1));
				psmt.setString(3, rs4.getString(2));
				psmt.setInt(4, rs4.getInt(3));
				psmt.setInt(5, cst.getCustomerId());

				n = psmt.execute();
			}
			if (n == false) {
				System.out.println("Car Rented Successfully...");
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	// NO OF CUSTOMER RECORDS

	public static ResultSet getNoOfRecords(Connection con) {
		ResultSet n = null;
		try {
			PreparedStatement psmt = con.prepareStatement("Select count(*) from customer");
			n = psmt.executeQuery();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return n;
	}

	// Getting Car Details By Id from car table

	public ResultSet getCarById(Connection con, String carId) {
		ResultSet rs = null;

		try {
			PreparedStatement psmt = con.prepareStatement("Select * from car where carid = ?");
			psmt.setString(1, carId);
			rs = psmt.executeQuery();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return rs;
	}

	// deleting car by ID FROM car Table

	public void deleteCarById(Connection con, String carId) {

		try {
			PreparedStatement psmt = con.prepareStatement("delete from car where carid = ?");

			psmt.setString(1, carId);

			boolean n = psmt.execute();

//			if(n == false) {
//				System.out.println("Car Deleted from stock Successfully...");
//			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}


	
	
	public void deleteRentedCarById(Connection con, String carId) {

		try {
			PreparedStatement psmt = con.prepareStatement("delete from rentedcar where carid = ?");

			psmt.setString(1, carId);

			boolean n = psmt.execute();

//			if (n == false) {
//				
//			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	// getting car by id from rentedcar table

	public ResultSet getRentedCarById(Connection con, String carId) {
		ResultSet rs = null;

		try {
			PreparedStatement psmt = con.prepareStatement("Select * from rentedcar where carid = ?");
			psmt.setString(1, carId);
			rs = psmt.executeQuery();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return rs;
	}

}
