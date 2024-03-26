package com.view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.controller.RentalSystem;
import com.model.Car;
import com.model.Customer;

public class Car_View {

	private static Scanner sc = new Scanner(System.in);
	private static Connection con = null;

	public static void main(String[] args) {

		RentalSystem RS = new RentalSystem();

		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/carRentalSystem", "postgres", "root");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		while (true) {
			System.out.println("\n========Welcome to Car Rental System========\n");

			System.out.println("1. Show Available Cars");
			System.out.println("2. Show Rented Cars");
			System.out.println("3. Rent a Car");
			System.out.println("4. Return a Car");
			System.out.println("5. Exit");

			System.out.println("\nEnter your chioce: ");
			int choice = sc.nextInt();

			switch (choice) {
			
			// CASE 1
			case 1:
				ResultSet rs = RS.getAvailableCars(con);

				try {
					if (rs.isBeforeFirst() != false) {
						printAvailableCar(rs);
					} else {
						System.out.println("\n No Car Availabe....");
					}
				} catch (SQLException e) {

					e.printStackTrace();
				}

				break;

				
			// CASE 2
			case 2:
				ResultSet rs2 = RS.getRentedCars(con);

				try {
					if (rs2.isBeforeFirst() != false) {
						printData(rs2);
					} else {
						System.out.println("No Car Rented...\n");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;

				
			// CASE 3
			case 3:
				ResultSet rs3 = RS.getAvailableCars(con);

				while (true) {
					printAvailableCar(rs3);

					System.out.println("\nEnter Car ID: ");
					sc.nextLine();
					String carId = sc.nextLine();
					carId = carId.toUpperCase();
					System.out.println("Enter No. of days: ");
					int days = sc.nextInt();
					int price = 0;
					String carName = "";
					try {
						ResultSet rs4 = RS.getCarById(con, carId);
						if (rs4.isBeforeFirst()) {
							while (rs4.next()) {
								price = rs4.getInt(3);
								carName = rs4.getString(1) + " " + rs4.getString(2);
							}
						} else {
							System.out.println("No Record Found!");
							break;
						}
						System.out.println("Cost for renting a " + carName + " car is " + (price * days) + " !.... \n");

						System.out.println("Enter 'Y' to Confirm!");
						System.out.println("Enter 'Exit' to exit! ");
						sc.nextLine();
						String inp = sc.nextLine();
						inp = inp.toLowerCase();

						switch (inp) {
						case "y": {
							Customer cstm = getCustomerObject();
							RS.saveCustomerData(con, cstm);

							ResultSet rs4Copy = RS.getCarById(con, carId);
							RS.saveRentedCar(con, rs4Copy, cstm);

							RS.deleteCarById(con, carId);

							break;

						}
						case "exit": {
							System.out.println("Thank You!");
							return;
						}
						}
					} catch (Exception e) {

						e.printStackTrace();
					}
					break;

				}
				break;

				
			// CASE 4
			case 4:
				Car car = new Car();
				System.out.println("To return car please enter CAR ID: ");
				sc.nextLine();
				String inp = sc.nextLine();
				inp = inp.toUpperCase();

				ResultSet rs5 = RS.getRentedCarById(con, inp);
				try {
					if (rs5.isBeforeFirst()) {
						printData(rs5);
					}
					else {
						System.out.println("No Record Found..");
						break;
					}
				} catch (SQLException e) {
				
					e.printStackTrace();
				}
				System.out.println("Enter 'Y' to confirm: ");
				System.out.println("Enter exit to Cancel: ");
				String inp2 = sc.nextLine();
				inp2 = inp2.toLowerCase();

				switch (inp2) {

				case "y":
					ResultSet rs6 = RS.getRentedCarById(con, inp);
					RS.saveCarData(con, rs6);
					RS.deleteRentedCarById(con, inp);
					break;

				case "exit":

					System.out.println("Thank You, Visit Again!");

					return;
				}

				
			// CASE 5
			case 5:
				System.out.println("Thank You for Renting Car from Us..\n .........Visit Again........");
				try {
					con.close();
				} catch (SQLException e) {

					e.printStackTrace();
				}
				return;

			}

		}

	}

	// PRICE CALCULATOR

	public static int calculatePrice(int days, int price) {
		int prc = days * price;
		return prc;
	}

	// PRINTING AVAILABLE CARS

	public static void printAvailableCar(ResultSet rs) {
		try {
			while (rs.next()) {
				System.out.println(
						rs.getString(4) + " | " + rs.getString(1) + " | " + rs.getString(2) + " | " + rs.getInt(3));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// PRINTING RENTED CARS

	public static void printData(ResultSet rs) {
		try {

			if (rs.isBeforeFirst()) {
				System.out.println("Rented Cars...\n");
				while (rs.next()) {
					System.out.println(rs.getString(1) + " | " + rs.getString(2) + " | " + rs.getString(3) + " | "
							+ rs.getInt(4) + " | " + rs.getInt(5));

				}
			} else {
				System.out.println("No Car record found...");
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	// CUSTOMER DATA Object

	public static Customer getCustomerObject() {

		Customer cst = new Customer();
		int customerID = 101;
		ResultSet n = RentalSystem.getNoOfRecords(con);
		try {
			if (n.next())
				customerID += n.getLong(1);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		System.out.println("Enter Your Mobile No. : ");
		long mob = sc.nextLong();

		System.out.println("Enter your Adhaar No. : ");
		long adhaarNo = sc.nextLong();
		sc.nextLine();

		System.out.println("Enter Your name: ");
		String name = sc.nextLine();

		cst.setCustomerId(customerID);
		cst.setName(name);
		cst.setMobNo(mob);
		cst.setAdhaarNo(adhaarNo);

		return cst;
	}

}
