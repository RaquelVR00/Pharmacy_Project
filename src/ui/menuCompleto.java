package ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
//import java.util.Date;
import java.util.List;

import db.interfaces.ComponentManager;
import db.interfaces.DBManager;
import db.interfaces.PharmacyManager;
import db.interfaces.ProductManager;
import db.interfaces.WorkerManager;
import db.pojos.Component;
import db.pojos.Pharmacy;
import db.pojos.Products;
import db.pojos.Worker;
import db.sqlite.SQLiteManager;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class menuCompleto {
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //cada vez que introduzcan una fecha van a tener que introducir este formato
	private static BufferedReader reader; //para leeer de consola
	
	//para añadir nuevos productos a la base de datos una vez que ya se han creado
	private static DBManager dbManager;
	private static ProductManager productManager;
	private static WorkerManager workerManager;
	private static ComponentManager componentManager;
	private static PharmacyManager pharmacyManager;
	public static void main(String[] args) throws Exception{
		
		//para conectar con la base de datos
		
		dbManager= new SQLiteManager();
		dbManager.connect();
		productManager=dbManager.getProductManager();
		workerManager=dbManager.getWorkerManager();
		componentManager=dbManager.getComponentManager();
		pharmacyManager=dbManager.getPharmacyManager();
		
		
		//para inicializar el bufferedReader
		reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Welcome to our data base!");
		System.out.println("Do you want to create the tables?");
		dbManager.createTables();
		System.out.println("Who are you? Choose between the following options:  ");
		System.out.println("1. Worker");
		System.out.println("2. Boss");
		System.out.println("3.Pharmacy");
		int choice=Integer.parseInt(reader.readLine()); //guardamos la opcion que eliga en un entero
		switch(choice) {
		case 1:
			workerMenu();
			break;
		case 2:
			bossMenu();
			break;
		default:
			pharmacyMenu();
			break;
			
		}
	}

	private static void workerMenu() throws Exception {
	while(true) {
		System.out.println("What would you like to do?");
		System.out.println("1. Search component by name");
		System.out.println("2. Search component by supplier");
		System.out.println("3. Add product");
		System.out.println("4. Search product by name");
		System.out.println("5. Search product by type");
		System.out.println("6. Search product by price");
		System.out.println("7. Create product");
		System.out.println("8. Go back");
		int choice=Integer.parseInt(reader.readLine()); 
		
		
		
		switch(choice) {
		case 1:
			searchComponentByName();
			break;
		case 2:
			searchComponentBySupplier();
			break;
		case 3:
			addProduct();
			break;
		case 4:
			searchProductByName();
			break;
		case 5:
			searchProductByType();
			break;
		case 6:
			searchProductByPrice();
			break;
		case 7:
			createProduct();
			break;
		default:
			return;
		}
	}
	}
	
	private static void searchComponentByName() throws Exception{
		System.out.println("Please, enter the following information: ");
		System.out.println("Inserte the name of the component you want to search: ");
		String name= reader.readLine();
		List<Component> componentList=componentManager.searchByName(name);
		for (Component component : componentList) {
			System.out.println(component);
		}
	}
	
	private static void searchComponentBySupplier() throws Exception{
		System.out.println("Please, enter the following information: ");
		System.out.println("Inserte the name of the supplier: ");
		String supplier= reader.readLine();
		List<Component> componentList=componentManager.searchBySupplier(supplier);
		for (Component component : componentList) {
			System.out.println(component);
		}
	}
	private static void addProduct() throws Exception{
		String product=searchProduct();
		System.out.println("Introduce the selected product´s id");
		int id=Integer.parseInt(reader.readLine());
		Products toBeModified=productManager.getProduct(id);
		int preexistingNumber=toBeModified.getNumberProducts();
		System.out.println("The number of products that are now avaiable are: " +preexistingNumber);
		System.out.println("Introduce the number of products you would like to add: ");
		int numberproducts=Integer.parseInt(reader.readLine());
		int updatedNumber=preexistingNumber+numberproducts;
		Products updateProduct=new Products(updatedNumber);
		productManager.update(updateProduct);	
	}
	
	private static String searchProduct() throws Exception{
		System.out.println("Introduce the name of the product you would like to add: ");
		String name= reader.readLine();
		return name;	
	}
	private static void searchProductByName() throws Exception{
		System.out.println("Please, enter the following information: ");
		System.out.println("Inserte the name of the product you want to search: ");
		String name= reader.readLine();
		List<Products> productList=productManager.searchByName(name);
		for (Products product : productList) {
			System.out.println(product);
		}
	}
	
	private static void searchProductByType() throws Exception{
		System.out.println("Please, enter the following information: ");
		System.out.println("Inserte the type of the product you want to search: ");
		String type= reader.readLine();
		List<Products> productList=productManager.searchByType(type);
		for (Products product : productList) {
			System.out.println(product);
		}
	}
	
	private static void searchProductByPrice() throws Exception{
		System.out.println("Please, enter the following information: ");
		System.out.println("Inserte the price of the product you want to search: ");
		Float price=Float.parseFloat(reader.readLine());
		List<Products> productList=productManager.searchByPrice(price);
		for (Products product : productList) {
			System.out.println(product);
		}
	}
	
	private static void createProduct() throws Exception{
		System.out.println("Please, enter the following information: ");
		System.out.println("Name: ");
		String name= reader.readLine();
		System.out.println("Type: ");
		String type=reader.readLine();
		System.out.println("Price: ");
		Float price = null;
		boolean wrongText=false;
		do {
			try {
				price=Float.parseFloat(reader.readLine());
			} catch (Exception e) {
				wrongText=true;
			}
		} while (!wrongText);
		System.out.println("Number of products: ");
		Integer numberproducts=Integer.parseInt(reader.readLine());
		Products product=new Products(name,type,price,numberproducts);
		//una vez que hemos creado el producto necesitamos insertarlo en la base de datos
		productManager.add(product);
	}
	
	private static void bossMenu() throws Exception{
		System.out.println("What would you like to do?");
		System.out.println("1. Search worker by name");
		System.out.println("2. Search worker by position");
		System.out.println("3. Add worker");
		System.out.println("4. Fire worker");
		System.out.println("5. Search pharmacy by name");
		System.out.println("6. Purchase component");
		System.out.println("7. Add component");
		System.out.println("8. Search component by name");
		System.out.println("9. Search component by supplier");
		System.out.println("10. Search product by name");
		System.out.println("11. Search product by type");
		System.out.println("12. Search product by price");
		System.out.println("13. Go back");
		int choice=Integer.parseInt(reader.readLine()); 
		
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
			purchaseComponent();
			break;
		case 7:
			addComponent();
			break;
		case 8:
			searchComponentByName();
			break;
		case 9:
			searchComponentBySupplier();
			break;
		case 10:
			searchProductByName();
			break;
		case 11:
			searchProductByType();
			break;
		case 12:
			searchProductByPrice();
			break;
		default:
			return;
		}
	}
	
	private static void searchWorkerByName() throws Exception{
		System.out.println("Please, enter the following information: ");
		System.out.println("Insert the name of the worker you want to search: ");
		String name= reader.readLine();
		List<Worker> productList=workerManager.searchByName(name);
		for (Worker worker : productList) {
			System.out.println(worker);
		}
	}
	
	private static void searchWorkerByPosition() throws Exception{
		System.out.println("Please, enter the following information: ");
		System.out.println("Insert the position of the worker you want to search: ");
		String position= reader.readLine();
		List<Worker> productList=workerManager.searchByPosition(position);
		for (Worker worker : productList) {
			System.out.println(worker);
		}
	}
	private static void addWorker() throws Exception{
		System.out.println("Please, enter the following information: ");
		System.out.println("Name: ");
		String name= reader.readLine();
		System.out.println("Position: ");
		String position=reader.readLine();
		System.out.println("Start Date (yyyy-MM-dd): ");
		String sd = reader.readLine();
		LocalDate start_date = LocalDate.parse(sd,formatter);
		System.out.println("Nationality: ");
		String nationality= reader.readLine();
		Worker worker = new Worker(name,position,Date.valueOf(start_date),nationality);
		//una vez que hemos creado el producto necesitamos insertarlo en la base de datos
		workerManager.add(worker);
	}
	
	private static void fireWorker() throws Exception{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Choose an employee to delete, type its ID: ");
		printWorkers();
		int dep_id = Integer.parseInt(reader.readLine());
		String sql = "DELETE FROM employees WHERE id=?";
		PreparedStatement prep = c.prepareStatement(sql);
		prep.setInt(1, dep_id);
		prep.executeUpdate();
		System.out.println("Deletion finished.");
	}
	
	private static void searchPharmacyByName() throws Exception{
		System.out.println("Please, enter the following information: ");
		System.out.println("Insert the name of the worker you want to search: ");
		String name= reader.readLine();
		List<Pharmacy> pharmacyList=pharmacyManager.searchByName(name);
		for (Pharmacy pharmacy : pharmacyList) {
			System.out.println(pharmacy);
		}
	}
	
	private static void purchaseComponent() throws Exception{
		System.out.println("Please, enter the following information: ");
		System.out.println("Name: ");
		String name= reader.readLine();
		System.out.println("Price: ");
		Float price = null;
		boolean wrongText=false;
		do {
			try {
				price=Float.parseFloat(reader.readLine());
			} catch (Exception e) {
				wrongText=true;
			}
		} while (!wrongText);
		System.out.println("Supplier: ");
		String supplier=reader.readLine();
		System.out.println("Number of components: ");
		Integer numbercomponents=Integer.parseInt(reader.readLine());
		Component component=new Component(name,price,supplier,numbercomponents);
		//una vez que hemos creado el producto necesitamos insertarlo en la base de datos
		componentManager.add(component);
	}
	
	private static void addComponent() throws Exception{
		String component=searchComponent();
		System.out.println("Introduce the selected component´s id");
		int id=Integer.parseInt(reader.readLine());
		Component toBeModified=componentManager.getComponent(id);
		int preexistingNumber=toBeModified.getNumberComponents();
		System.out.println("The number of components that are now avaiable are: " +preexistingNumber);
		System.out.println("Introduce the number of components you want to add: ");
		int numbercomponents=Integer.parseInt(reader.readLine());
		int updatedNumber=preexistingNumber+numbercomponents;
		Component updateComponent=new Component(updatedNumber);
		componentManager.update(updateComponent);	
	}

	private static String searchComponent() throws Exception{
		System.out.println("Please, introduce the name of the component you want to add: ");
		String name= reader.readLine();
		return name;	
	}
	
	private static void pharmacyMenu() throws Exception{
		System.out.println("What would you like to do?");
		System.out.println("1. Search product by name");
		System.out.println("2. Search product by type");
		System.out.println("3. Search product by price");
		System.out.println("4. Buy");
		System.out.println("5. Go back");
		int choice=Integer.parseInt(reader.readLine()); 
		
		switch(choice) {
		case 1:
			searchProductByName();
			break;
		case 2:
			searchProductByType();
			break;
		case 3:
			searchProductByPrice();
			break;
		case 4:
			buy();
			break;
		default: 
			return;
		}
	}
	
	private static void buy() throws Exception{
		String product=searchProduct();
		System.out.println("Introduce the selected product´s id");
		int id=Integer.parseInt(reader.readLine());
		Products toBeModified=productManager.getProduct(id);
		int preexistingNumber=toBeModified.getNumberProducts();
		System.out.println("The number of products that are now avaiable are: " +preexistingNumber);
		System.out.println("Introduce the number of products you want to buy: ");
		int numberproducts=Integer.parseInt(reader.readLine());
		int updatedNumber=preexistingNumber-numberproducts;
		Products updateProducts=new Products(updatedNumber);
		productManager.update(updateProducts);	
	}
}
