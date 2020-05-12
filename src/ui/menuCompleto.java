package ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
//import java.util.Date;
import java.util.List;

import db.interfaces.ComponentManager;
import db.interfaces.ContractPharmacyManager;
import db.interfaces.ContractWorkerManager;
import db.interfaces.DBManager;
import db.interfaces.PharmacyManager;
import db.interfaces.ProductManager;
import db.interfaces.UserManager;
import db.interfaces.WorkerManager;
import db.jpa.JPAUserManager;
import db.pojos.Component;
import db.pojos.ContractPharmacy;
import db.pojos.Pharmacy;
import db.pojos.Products;
import db.pojos.ContractWorker;
import db.pojos.Worker;
import db.sqlite.SQLiteManager;
import db.pojos.users.Role;
import db.pojos.users.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class menuCompleto {
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Each time dates are added this is the format that is needed.
	private static BufferedReader reader; // To read from the console

	// para a�adir nuevos productos a la base de datos una vez que ya se han creado
	private static DBManager dbManager;
	private static ProductManager productManager;
	private static WorkerManager workerManager;
	private static ComponentManager componentManager;
	private static PharmacyManager pharmacyManager;
	private static ContractWorkerManager contractWorkerManager;
	private static ContractPharmacyManager contractPharmacyManager;
	private static UserManager userManager;

	public static void main(String[] args) throws Exception {

		// In order to connect with the DB

		dbManager = new SQLiteManager();
		dbManager.connect();
		productManager = dbManager.getProductManager();
		workerManager = dbManager.getWorkerManager();
		componentManager = dbManager.getComponentManager();
		pharmacyManager = dbManager.getPharmacyManager();
		contractWorkerManager = dbManager.getContractWorkerManager();
		contractPharmacyManager = dbManager.getContractPharmacyManager();
		dbManager.createTables();
		
		userManager = new JPAUserManager();
		userManager.connect();
		// To initialize the bufferedReader
		reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Welcome to our data base!");
		// System.out.println("Do you want to create the tables?");
		
		while (true) {
			System.out.println("What do you want to do?");
			System.out.println("1. Create a new role");
			System.out.println("2. Create a new user");
			System.out.println("3. Login");
			System.out.println("0. Exit");
			int choice = Integer.parseInt(reader.readLine()); // We save the chosen option in an integer
			switch (choice) {
			case 1:
				newRole();
				break;
			case 2:
				newUser();
				break;
			case 3:
				login();
				break;
			case 0:
				dbManager.disconnect();
				userManager.disconnect();
				return;
			default:
				break;
			}
		}
	}
	
	private static void newRole() throws Exception {
		System.out.println("Please type the new role information:");
		System.out.print("Role name:");
		String roleName = reader.readLine();
		Role role = new Role(roleName);
		userManager.createRole(role);
	}
	
	private static void newUser() throws Exception {
		System.out.println("Please type the new user information:");
		System.out.print("Username:");
		String username = reader.readLine();
		System.out.print("Password:");
		String password = reader.readLine();
		// Create the password's hash
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] hash = md.digest();
		// Show all the roles and let the user choose one
		List<Role> roles = userManager.getRoles();
		for (Role role : roles) {
			System.out.println(role);
		}
		System.out.print("Type the chosen role id:");
		int roleId = Integer.parseInt(reader.readLine());
		// Get the chosen role from the database
		Role chosenRole = userManager.getRole(roleId);
		// Create the user and store it
		User user = new User(username, hash, chosenRole);
		userManager.createUser(user);
	}
	
	private static void login() throws Exception {
		System.out.println("Please input your credentials");
		System.out.print("Username:");
		String username = reader.readLine();
		System.out.print("Password:");
		String password = reader.readLine();
		User user = userManager.checkPassword(username, password);
		// We check if the user/password combination was OK
		if (user == null) {
			System.out.println("Wrong credentials, please try again!");
		}
		// We check the role
		else if (user.getRole().getRole().equalsIgnoreCase("boss")) {
			System.out.println("Welcome boss " + username + "!");
			bossMenu();
		}
		else if (user.getRole().getRole().equalsIgnoreCase("worker")) {
			System.out.println("Welcome worker " + username + "!");
			workerMenu();
		}
		else if (user.getRole().getRole().equalsIgnoreCase("pharmacy")) {
			System.out.println("Welcome pharmacy " + username + "!");
			pharmacyMenu();
		}
		else {
			System.out.println("Invalid role.");
		}
	}

	private static void workerMenu() throws Exception {
		while (true) {
			System.out.println("What would you like to do?");
			System.out.println("1. Search component by name");
			System.out.println("2. Search component by supplier");
			System.out.println("3. Add product");
			System.out.println("4. Search product by name");
			System.out.println("5. Search product by type");
			System.out.println("6. Search product by price");
			System.out.println("7. Create product");
			System.out.println("8. Go back");
			int choice = Integer.parseInt(reader.readLine());
			switch (choice) {
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
			case 8:
				return;
			}
		}
	}

	private static void searchComponentByName() throws Exception {
		System.out.println("Please, enter the following information: ");
		System.out.println("Enter the name of the component you want to search: ");
		String name = reader.readLine();
		List<Component> componentList = componentManager.searchByName(name);
		for (Component component : componentList) {
			System.out.println(component);
			System.out.println("\n");
		}
	}

	private static void searchComponentBySupplier() throws Exception {
		System.out.println("Please, enter the following information: ");
		System.out.println("Enter the name of the supplier: ");
		String supplier = reader.readLine();
		List<Component> componentList = componentManager.searchBySupplier(supplier);
		for (Component component : componentList) {
			System.out.println(component);
		}
	}

	private static void addProduct() throws Exception {
		List<Products> productList = productManager.showProducts();
		for (Products product : productList) {
			System.out.println(product);
		}
		System.out.println("Enter the selected product�s id");
		int id=0;
		try{
	        id = Integer.parseInt(reader.readLine());
    	}catch(NumberFormatException ex){
        	System.out.println("No es un n�mero, porfavor introduzca uno");
    	}
		Products toBeModified = productManager.getProduct(id);
		int preexistingNumber = toBeModified.getNumberProducts();
		System.out.println("The number of products that are now avaiable are: " + preexistingNumber);
		System.out.println("Enter the number of products you would like to add: ");
		int numberproducts = Integer.parseInt(reader.readLine());
		int updatedNumber = preexistingNumber + numberproducts;
		toBeModified.setNumberProducts(updatedNumber);
		productManager.update(toBeModified);
	}
	
	private static void searchProductByName() throws Exception {
		System.out.println("Please, enter the following information: ");
		System.out.println("Enter the name of the product you want to search: ");
		String name = reader.readLine();
		List<Products> productList = productManager.searchByName(name);
		for (Products product : productList) {
			System.out.println(product);
		}
	}

	private static void searchProductByType() throws Exception {
		System.out.println("Please, enter the following information: ");
		System.out.println("Enter the type of the product you want to search: ");
		String type = reader.readLine();
		List<Products> productList = productManager.searchByType(type);
		for (Products product : productList) {
			System.out.println(product);
		}
	}

	private static void searchProductByPrice() throws Exception {
		System.out.println("Please, enter the following information: ");
		System.out.println("Enter the price of the product you want to search: ");
		Float price = Float.parseFloat(reader.readLine());
		List<Products> productList = productManager.searchByPrice(price);
		for (Products product : productList) {
			System.out.println(product);
		}
	}

	private static void createProduct() throws Exception {
		System.out.println("Please, enter the following information: ");
		System.out.println("Name: ");
		String name = reader.readLine();
		System.out.println("Type: ");
		String type = reader.readLine();
		System.out.println("Price: ");
		Float price = Float.parseFloat(reader.readLine());
		;
		/*
		 * boolean wrongText=false; do { try {
		 * price=Float.parseFloat(reader.readLine()); } catch (Exception e) {
		 * wrongText=true; } } while (!wrongText);
		 */
		System.out.println("Number of products: ");
		Integer numberproducts = Integer.parseInt(reader.readLine());
		Products product = new Products(name, type, price, numberproducts);
		// Once we have created the product we have to add it to the DB
		productManager.add(product);
		// Products p = productManager.getProduct(productId)
		System.out.println("Now you will find all the available components:");
		List<Component> components = componentManager.showComponents();
		for (Component component : components) {
			System.out.println(component);
		}
		System.out.println("Choose the desired ID's:");
		int x = 5;
		while (x != 0) {
			int componentId = Integer.parseInt(reader.readLine());

			componentManager.give(dbManager.getLastId(), componentId);

			System.out.println("If you want to add another component enter 5, if you don't want to add another component enter 0.");
			x = Integer.parseInt(reader.readLine());
		}

	}

	private static void bossMenu() throws Exception {
		while (true) {
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
			System.out.println("13. Add contract with a pharmacy");
			System.out.println("14. Add contract with a worker");
			System.out.println("15. Add pharmacy");
			System.out.println("16. Show all pharmacies");
			System.out.println("17. Go back");
			int choice = Integer.parseInt(reader.readLine());

			switch (choice) {
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
			case 13:
				addContractPharmacy();
				break;
			case 14:
				addContractWorker();
				break;
			case 15:
				addPharmacy();
				break;
			case 16:
				showPharmacies();
				break;
			case 17:
				return;
			}
		}
	}

	private static void searchWorkerByName() throws Exception {
		System.out.println("Please, enter the following information: ");
		System.out.println("Enter the name of the worker you want to search: ");
		String name = reader.readLine();
		List<Worker> workerList = workerManager.searchByName(name);
		for (Worker worker : workerList) {
			System.out.println(worker);
		}
	}

	private static void searchWorkerByPosition() throws Exception {
		System.out.println("Please, enter the following information: ");
		System.out.println("Enter the position of the worker you want to search: ");
		String position = reader.readLine();
		List<Worker> productList = workerManager.searchByPosition(position);
		for (Worker worker : productList) {
			System.out.println(worker);
		}
	}

	private static void addWorker() throws Exception {
		System.out.println("Please, enter the following information: ");
		System.out.println("Name: ");
		String name = reader.readLine();
		System.out.println("Position: ");
		String position = reader.readLine();
		System.out.println("Start Date (yyyy-MM-dd): ");
		String sd = reader.readLine();
		LocalDate start_date = LocalDate.parse(sd, formatter);
		System.out.println("Nationality: ");
		String nationality = reader.readLine();
		// Worker worker = new
		// Worker(name,position,Date.valueOf(start_date),nationality);
		// una vez que hemos creado el producto necesitamos insertarlo en la base de
		// datos
		// workerManager.add(worker);
		System.out.println("Now you will find the contacts: ");
		List<ContractWorker> contracts = contractWorkerManager.showContracts();
		for (ContractWorker contract : contracts) {
			System.out.println(contract);
		}
		System.out.println("Choose the ID of the desired contract:");
		int contractId = Integer.parseInt(reader.readLine());
		Worker worker = new Worker(name, position, Date.valueOf(start_date), nationality, contractId);
		workerManager.add(worker);
	}

	private static void fireWorker() throws Exception {
		// BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Choose a worker to delete, type its ID: ");
		workerManager.printWorkers();
		int worker_id = Integer.parseInt(reader.readLine());
		workerManager.fire(worker_id);
		System.out.println("Deletion finished.");
	}

	private static void searchPharmacyByName() throws Exception {
		System.out.println("Please, enter the following information: ");
		System.out.println("Enter the name of the pharmacy you want to search: ");
		String name = reader.readLine();
		List<Pharmacy> pharmacyList = pharmacyManager.searchByName(name);
		for (Pharmacy pharmacy : pharmacyList) {
			System.out.println(pharmacy);
		}
	}

	private static void purchaseComponent() throws Exception {
		System.out.println("Please, enter the following information: ");
		System.out.println("Name: ");
		String name = reader.readLine();
		System.out.println("Price: ");
		Float price = Float.parseFloat(reader.readLine());
		/*
		 * boolean wrongText=false; do { try {
		 * price=Float.parseFloat(reader.readLine()); } catch (Exception e) {
		 * wrongText=true; } } while (!wrongText);
		 */
		System.out.println("Supplier: ");
		String supplier = reader.readLine();
		System.out.println("Number of components: ");
		Integer numbercomponents = Integer.parseInt(reader.readLine());
		Component component = new Component(name, price, supplier, numbercomponents);
		System.out.println(component);
		componentManager.add(component);
	}

	private static void addComponent() throws Exception {
		System.out.println("Introduce the selected component's id");
		List<Component> list_components = componentManager.showComponents();
		for (Component components : list_components) {
			System.out.println(components);
		}
		int id = Integer.parseInt(reader.readLine());
		Component toBeModified = componentManager.getComponent(id);
		System.out.println(toBeModified);
		int preexistingNumber = toBeModified.getNumberComponents();
		System.out.println("The number of components that are now avaiable are: " + preexistingNumber);
		System.out.println("Introduce the number of components you want to add: ");
		int numbercomponents = Integer.parseInt(reader.readLine());
		int updatedNumber = preexistingNumber + numbercomponents;
		// Component updateComponent=new Component(updatedNumber);
		toBeModified.setNumberComponents(updatedNumber);
		componentManager.update(toBeModified);
	}


	private static void addContractPharmacy() throws Exception {
		System.out.println("Please, enter the following information: ");
		System.out.println("Type: ");
		String type = reader.readLine();
		System.out.println("Numer of products ");
		Integer n_products = Integer.parseInt(reader.readLine());
		System.out.println("Expenditure ");
		Float expenditure = Float.parseFloat(reader.readLine());
		ContractPharmacy contract = new ContractPharmacy(type, expenditure, n_products);
		System.out.println(contract);
		contractPharmacyManager.add(contract);
	}

	private static void addContractWorker() throws Exception {
		System.out.println("Please, enter the following information: ");
		System.out.println("Bonus: ");
		Float bonus = Float.parseFloat(reader.readLine());
		System.out.println("Salary: ");
		Float salary = Float.parseFloat(reader.readLine());
		System.out.println("Type: ");
		String type = reader.readLine();
		ContractWorker contract = new ContractWorker(bonus, salary, type);
		contractWorkerManager.add(contract);
	}

	private static void addPharmacy() throws Exception {
		System.out.println("Please, enter the following information: ");
		System.out.println("Name: ");
		String name = reader.readLine();
		System.out.println("Location");
		String location = reader.readLine();
		System.out.println("Available contracts: ");
		List<ContractPharmacy> contracts = contractPharmacyManager.showContracts();
		for (ContractPharmacy contract : contracts) {
			System.out.println(contract);
		}
		System.out.println("Contract: ");
		int contract_pid = Integer.parseInt(reader.readLine());
		Pharmacy pharmacy = new Pharmacy(name, contract_pid, location);
		pharmacyManager.add(pharmacy);
	}

	private static void showPharmacies() throws Exception {
		List<Pharmacy> list_pharmacies = pharmacyManager.showPharmacy();
		for (Pharmacy pharmacies : list_pharmacies) {
			System.out.println(pharmacies);
		}
	}
	
	private static void pharmacyMenu() throws Exception {
		while (true) {
			System.out.println("What would you like to do?");
			System.out.println("1. Search product by name");
			System.out.println("2. Search product by type");
			System.out.println("3. Search product by price");
			System.out.println("4. Buy");
			System.out.println("5. Go back");
			int choice = Integer.parseInt(reader.readLine());

			switch (choice) {
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
			case 5:
				return;
			}
		}
	}

	private static void buy() throws Exception {
		List<Products> productList = productManager.showProducts();
		for (Products product : productList) {
			System.out.println(product);
		}
		System.out.println("Enter the selected product�s id");
		int id = Integer.parseInt(reader.readLine());
		Products toBeModified = productManager.getProduct(id);
		int preexistingNumber = toBeModified.getNumberProducts();
		System.out.println("The number of products that are now avaiable are: " + preexistingNumber);
		System.out.println("Enter the number of products you want to buy: ");
		int numberproducts = Integer.parseInt(reader.readLine());
		int updatedNumber = preexistingNumber - numberproducts;
		toBeModified.setNumberProducts(updatedNumber);
		productManager.update(toBeModified);
		
		searchPharmacyByName();
		System.out.println("Enter your pharmacy's id: ");
		int id_p = Integer.parseInt(reader.readLine());
		pharmacyManager.give(id_p, id);
	}
}
