package ui;
import db.interfaces.WorkerManager;
import db.interfaces.*;
import db.pojos.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class boss {
	private static BufferedReader reader ;
	
	private static WorkerManager wokerManager;
	private static PharmacyManager pharmacyManager;
	private static ComponentManager componentManager;
	private static ProductManager productManager;
	
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd)");
	
	private static void bossMenu() throws Exception {
		reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("What do you want to do?");
		System.out.println("1. Search worker by name");
		System.out.println("2. Search worker by position");
		System.out.println("3. Add a worker");
		System.out.println("4. Fire a worker");
		System.out.println("5. Search pharmacy by name");
		System.out.println("6. Add component");
		System.out.println("7. Search component by name");
		System.out.println("8. Search component by supplier");
		System.out.println("9. Search product by name");
		System.out.println("10. Search product by type");
		System.out.println("11. Search product by price");
		int choice = Integer.parseInt(reader.readLine());
		switch(choice) {
		case 1:
			searchWorkerByName(); 
			break;
		case 2:
			searchWorkerByPosition();
			break;
		case 3:
			addWorker();
			break;
		case 4:
			fireWorker();
			break;
		case 5:
			searchPharmacyByName();
			break;
		case 6:
			addComponent();
			break;
		case 7: 
			searchComponentByName();
			break;
		case 8:
			searchComponentBySupplier();
			break;
		case 9:
			searchProductByName();
			break;
		case 10:
			searchProductByType();
			break;
		case 11:
			searchProductByPrice();
			break;
		default:
			break;
		}
	}
	
	private static void searchWorkerByName() throws Exception {
		 System.out.print("Name: ");
		 String name = reader.readLine();
		 List<Worker> workers = wokerManager.searchByName(name);
		 for (Worker worker : workers) {
			 System.out.println(worker);
		 }
	}
	
	private static void searchWorkerByPosition() throws Exception {
		System.out.print("Position: ");
		 String name = reader.readLine();
		 List<Worker> workers = wokerManager.searchByName(name);
		 for (Worker worker : workers) {
			 System.out.println(worker);
		 }
	}
	
	private static void addWorker() throws Exception {
		System.out.println("Name: ");
		String name = reader.readLine();
		System.out.println("Position: ");
		String position = reader.readLine();
		System.out.println("Start date (yyyy-MM-dd) : ");
		String sd = reader.readLine();
		LocalDate start_date = LocalDate.parse(sd,formatter);
		System.out.println("Natinality: ");
		String nationality = reader.readLine();
		//System.out.println("Contract id: ");
		//Worker worker = new Worker(name,position,Date.valueOf(start_date),nationality);
		//to do 
	}
	
	private static void fireWorker() throws Exception {
		
	}
	
	private static void searchPharmacyByName() throws Exception {
		System.out.print("Name: ");
		 String name = reader.readLine();
		 List<Pharmacy> pharmacies = pharmacyManager.searchByName(name);
		 for (Pharmacy pharmacy : pharmacies) {
			 System.out.println(pharmacy);
		 }
	}
	
	private static void addComponent() throws Exception {
		System.out.println("Name: ");
		String name = reader.readLine();
		System.out.println("Price: ");
		Float price = Float.parseFloat(reader.readLine());
		System.out.println("Supplier: ");
		String supplier = reader.readLine();
		Component component = new Component(name,price,supplier);
	}
	
	private static void searchComponentByName() throws Exception {
		System.out.print("Name: ");
		 String name = reader.readLine();
		 List<Component> components = componentManager.searchByName(name);
		 for (Component component : components) {
			 System.out.println(component);
		 }
	}
	
	private static void searchComponentBySupplier() throws Exception {
		System.out.print("Supplier: ");
		 String name = reader.readLine();
		 List<Component> components = componentManager.searchByName(name);
		 for (Component component : components) {
			 System.out.println(component);
		 }
	}
	
	private static void searchProductByName() throws Exception {
		System.out.print("Name: ");
		 String name = reader.readLine();
		 List<Products> products = productManager.searchByName(name);
		 for (Products product : products) {
			 System.out.println(product);
		 }
	}
	
	private static void searchProductByType() throws Exception {
		System.out.print("Type: ");
		 String name = reader.readLine();
		 List<Products> products = productManager.searchByName(name);
		 for (Products product : products) {
			 System.out.println(product);
		 }
	}
	
	//intentar hacerlo por rangos
	private static void searchProductByPrice() throws Exception { 
		System.out.print("Price: ");
		 String name = reader.readLine();
		 List<Products> products = productManager.searchByName(name);
		 for (Products product : products) {
			 System.out.println(product);
		 }
	}
}
