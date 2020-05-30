package ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
//import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import db.interfaces.*;
import db.jpa.JPAUserManager;
import db.pojos.*;
import db.pojos.users.Role;
import db.pojos.users.User;
import db.sqlite.SQLiteManager;
import xml.utils.CustomErrorHandler;

import java.sql.*;

public class menuCompleto {
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Each time dates are added
																							// this is the format that
																							// is needed.
	private static BufferedReader reader; // To read from the console
	// para añadir nuevos productos a la base de datos una vez que ya se han creado
	private static DBManager dbManager;
	private static ProductManager productManager;
	private static WorkerManager workerManager;
	private static ComponentManager componentManager;
	private static PharmacyManager pharmacyManager;
	private static ContractWorkerManager contractWorkerManager;
	private static ContractPharmacyManager contractPharmacyManager;
	private static UserManager userManager;
	private static String pharmacyName = "";
	private static String bossName = "";
	private static String workerName = "";
	public static String numbers = "0123456789";
	public static String caps = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static String low_case = "abcdefghijklmnopqrstuvwxyz";

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
			int choice = 8;
			do {
				System.out.println("Introduce the number of the option you would like to choose: ");
				choice = Integer.parseInt(reader.readLine()); // We save the chosen option in an integer
			} while (choice < 0 || choice > 3);
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
		String roleName;
		do {
			System.out.println("Please type the new role information:");
			System.out.print("Role name:");
			roleName = reader.readLine();
		} while (!roleName.equals("boss") && !roleName.equals("worker") && !roleName.equals("pharmacy"));
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
			if (role.getId() != 2) {
				System.out.println(role);
			}
		}
		System.out.print("Type the chosen role id:");
		int roleId = Integer.parseInt(reader.readLine());
		// Get the chosen role from the database
		while (roleId != 1 && roleId != 3) {
			System.out.println("Not valid id:");
			System.out.print("Type the chosen role id:");
			roleId = Integer.parseInt(reader.readLine());
		}
		Role chosenRole = userManager.getRole(roleId);
		// Create the user and store it
		User user = new User(username, hash, chosenRole);
		userManager.createUser(user);
		if (roleId == 3) {
			String pharmacyName = username;
			System.out.println("Please, enter the following information: ");
			System.out.println("Location");
			String location = reader.readLine();
			ContractPharmacy contract = new ContractPharmacy();
			contractPharmacyManager.add(contract);
			int contract_pid = contractPharmacyManager.getId();
			Pharmacy pharmacy = new Pharmacy(pharmacyName, contract_pid, location);
			pharmacyManager.add(pharmacy);
		}
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
			bossName = username;
			bossMenu();
		} else if (user.getRole().getRole().equalsIgnoreCase("worker")) {
			System.out.println("Welcome worker " + username + "!");
			workerName = username;
			workerMenu();
		} else if (user.getRole().getRole().equalsIgnoreCase("pharmacy")) {
			System.out.println("Welcome pharmacy " + username + "!");
			pharmacyName = username;
			pharmacyMenu();
		} else {
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
			System.out.println("8. Generate XML");
			System.out.println("9. Create product from XML");
			System.out.println("10. Change your user name");
			System.out.println("11. Change your password");
			System.out.println("12. Go back");
			int choice = 14;
			do {
				System.out.println("Introduce the number of the option you would like to choose: ");
				choice = Integer.parseInt(reader.readLine()); // We save the chosen option in an integer
			} while (choice < 0 || choice > 12);
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
				generateXML();
				break;
			case 9:
				createProductXML();
				break;
			case 10:
				userManager.updateUserName(workerName);
				return;
			case 11:
				userManager.updatePassword(workerName);
				return;
			case 12:
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
		List<Product> productList = productManager.showProducts();
		for (Product product : productList) {
			System.out.println(product);
		}
		Integer id = new Integer(0);
		boolean wrongtext = false;
		do {
			System.out.println("Enter the selected product´s id");
			do {
				try {
					id = Integer.parseInt(reader.readLine());
					wrongtext = false;
				} catch (NumberFormatException ex) {
					wrongtext = true;
					System.out.println("It's not a int, please enter a int.");
				}
			} while (wrongtext);
		} while (productManager.getProduct(id) == null);
		Product toBeModified = productManager.getProduct(id);
		int preexistingNumber = toBeModified.getNumberProducts();
		System.out.println("The number of products that are now avaiable are: " + preexistingNumber);
		boolean correctNumber = true;
		int counter = 0;
		int counter2 = 0;
		List<Component> componentList = toBeModified.getComponents();
		for (Component componentInList : componentList) {
			int numberComponents = componentInList.getNumberComponents();
			if (numberComponents > 0) {
				counter++;
			}
		}
		if (counter == componentList.size()) {
			while (correctNumber) {
				Integer numberproducts = new Integer(0);
				boolean wrongtext1 = false;
				do {
					System.out.println("Enter the number of products you would like to add: ");
					try {
						numberproducts = Integer.parseInt(reader.readLine());
						wrongtext1 = false;
					} catch (NumberFormatException ex) {
						wrongtext1 = true;
						System.out.println("It's not a int, please enter a int.");
					}
				} while (wrongtext1);
				int updatedNumber = preexistingNumber + numberproducts;
				toBeModified.setNumberProducts(updatedNumber);
				boolean componentsRight = false;
				for (Component componentChecker : componentList) {
					int idComponent = componentChecker.getId();
					Component realComponent = componentManager.getComponent(idComponent);
					int numberComponents = realComponent.getNumberComponents();
					if (numberComponents >= numberproducts) {
						counter2++;
						if (counter2 == componentList.size()) {
							componentsRight = true;
							productManager.update(toBeModified);
						}
					} else {
						System.out.println("There are not enough components for the number of products you want to add.");
						System.out.println("In the following list you can check the number of availavable components: ");
						for (Component component : componentList) {
							System.out.println(component);
						}
						counter2 = 0;
						correctNumber = true;
					}
				}
				for (Component component : componentList) {
					int idComponent = component.getId();
					Component realComponent = componentManager.getComponent(idComponent);
					int numberComponents = realComponent.getNumberComponents();
					if (componentsRight) {
						int updatedComponentsNumber = numberComponents - numberproducts;
						realComponent.setNumberComponents(updatedComponentsNumber);
						componentManager.update(realComponent);
						correctNumber = false;
					} else if (numberComponents == 0) {
						System.out.println("There are no components available.");
						correctNumber = false;
						break;
					}
				}
			}
		} else {
			System.out.println("At least one of the components needed has an availability of 0. Products can't be added.");
		}
	}

	private static void searchProductByName() throws Exception {
		System.out.println("Please, enter the following information: ");
		System.out.println("Enter the name of the product you want to search: ");
		String name = reader.readLine();
		List<Product> productList = productManager.searchByName(name);
		for (Product product : productList) {
			System.out.println(product);
		}
	}

	private static void searchProductByType() throws Exception {
		System.out.println("Please, enter the following information: ");
		System.out.println("Enter the type of the product you want to search: ");
		String type = reader.readLine();
		List<Product> productList = productManager.searchByType(type);
		for (Product product : productList) {
			System.out.println(product);
		}
	}

	private static void searchProductByPrice() throws Exception {
		System.out.println("Please, enter the following information: ");

		Float price = new Float(0.0);
		boolean wrongtext = false;
		do {
			System.out.println("Enter the price of the product you want to search: ");
			do {
				try {
					price = Float.parseFloat(reader.readLine());
					wrongtext = false;
				} catch (NumberFormatException ex) {
					wrongtext = true;
					System.out.println("It's not a float, please enter a float.");
				}
			} while (wrongtext);
		} while (productManager.searchByPrice(price) == null);
		List<Product> productList = productManager.searchByPrice(price);
		for (Product product : productList) {
			System.out.println(product);
		}
	}

	private static void createProduct() throws Exception {
		int counter = 0;
		List<Component> componentsList = componentManager.showComponents();
		for (Component componentInList : componentsList) {
			int numberComponents = componentInList.getNumberComponents();
			if (numberComponents > 0) {
				counter++;
			}
		}
		if (counter >= 1) {
			System.out.println("Please, enter the following information: ");
			System.out.println("Name: ");
			String name = reader.readLine();
			System.out.println("Type: ");
			String type = reader.readLine();
			Float price = new Float(0.0);
			boolean wrongtext = false;
			do {
				System.out.println("Price: ");
				try {
					price = Float.parseFloat(reader.readLine());
					wrongtext = false;
				} catch (NumberFormatException ex) {
					wrongtext = true;
					System.out.println("It's not a float, please enter a float.");
				}
			} while (wrongtext);
			Integer numberproducts = new Integer(0);
			boolean wrongtext1 = false;
			do {
				System.out.println("Number of products: ");
				try {
					numberproducts = Integer.parseInt(reader.readLine());
					wrongtext1 = false;
				} catch (NumberFormatException ex) {
					wrongtext1 = true;
					System.out.println("It's not a int, please enter a int.");
				}
			} while (wrongtext1);
			Product product = new Product(name, type, price, numberproducts);
			product.setId(dbManager.getLastId());
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
			List<Component> componentList = new ArrayList<Component>();
			while (x != 0) {
				Integer componentId = new Integer(0);
				boolean wrongtext2 = false;
				do {
					do {
						System.out.println("Component Id: ");
						try {
							componentId = Integer.parseInt(reader.readLine());
							wrongtext2 = false;
						} catch (NumberFormatException ex) {
							wrongtext2 = true;
							System.out.println("It's not a int, please enter a int.");
						}
					} while (wrongtext2);
				} while (componentManager.getComponent(componentId) == null);
				Component realComponent = componentManager.getComponent(componentId);
				int numberComponents = realComponent.getNumberComponents();
				if (numberComponents >= numberproducts) {
					componentList.add(realComponent);
				} else {
					do {
						System.out
								.println("There are not enough components for the number of products you want to add.");
						System.out.println(
								"Please, enter the id of a valid component (a component that has at least the same number available"
										+ " as the number of products you want to create.");
						do {
							System.out.println("Component Id: ");
							try {
								componentId = Integer.parseInt(reader.readLine());
								wrongtext2 = false;
							} catch (NumberFormatException ex) {
								wrongtext2 = true;
								System.out.println("It's not a int, please enter a int.");
							}
						} while (wrongtext2);
						realComponent = componentManager.getComponent(componentId);
						numberComponents = realComponent.getNumberComponents();
					} while (componentManager.getComponent(componentId) == null && (numberComponents < numberproducts));
					componentList.add(realComponent);
				}
				System.out.println(
						"If you want to add another component enter 5, if you don't want to add another component enter 0.");
				x = Integer.parseInt(reader.readLine());
			}
			for (Component component : componentList) {
				int updatedComponentsNumber = component.getNumberComponents() - numberproducts;
				component.setNumberComponents(updatedComponentsNumber);
				int idComponent = component.getId();
				componentManager.give(dbManager.getLastId(), idComponent);
				product.setNumberProducts(numberproducts);
			}
			workerManager.printWorkers();
			Integer id_w = new Integer(0);
			boolean wrongtext3 = false;
			do {
				System.out.println("Enter your worker id: ");
				try {
					id_w = Integer.parseInt(reader.readLine());
					wrongtext3 = false;
				} catch (NumberFormatException ex) {
					wrongtext3 = true;
					System.out.println("It's not a int, please enter a int.");
				}
			} while (wrongtext3);
		} else {
			System.out.println("A product can't be created if there are no components at all.");
		}

	}

	private static void generateXML() throws Exception {
		List<Product> listProducts = productManager.showProducts();
		for (Product product : listProducts) {
			System.out.println(product);
		}
		// System.out.println("Introduce the id of the product you want to create the
		// XML from: ");
		Integer id = new Integer(0);
		boolean wrongtext = false;
		do {
			System.out.println("Introduce the id of the product you want to create the XML from: ");
			do {
				try {
					id = Integer.parseInt(reader.readLine());
					wrongtext = false;
				} catch (NumberFormatException ex) {
					wrongtext = true;
					System.out.println("It's not a int, please enter a int.");
				}
			} while (wrongtext);
		} while (productManager.getProduct(id) == null);
		Product product = productManager.getProduct(id);
		System.out.println(product);
		JAXBContext context = JAXBContext.newInstance(Product.class);
		Marshaller marshal = context.createMarshaller();
		marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		File file = new File("./xmls/Output-Product.xml");
		marshal.marshal(product, file);
		marshal.marshal(product, System.out);
	}

	private static void createProductXML() throws Exception {
		JAXBContext context = JAXBContext.newInstance(Product.class);
		Unmarshaller unmarshal = context.createUnmarshaller();
		File file = null;
		boolean incorrectProduct = false;
		do {
			System.out.println("Type the filename for the XML document (expected in the xmls folder):");
			String fileName = reader.readLine();
			file = new File("./xmls/" + fileName);
			try {
				DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
				dBF.setValidating(true);
				DocumentBuilder builder = dBF.newDocumentBuilder();
				CustomErrorHandler customErrorHandler = new xml.utils.CustomErrorHandler();
				builder.setErrorHandler(customErrorHandler);
				Document doc = builder.parse(file);
				if (!customErrorHandler.isValid()) {
					incorrectProduct = true;
				}
			} catch (ParserConfigurationException ex) {
				System.out.println(file + " error while parsing!");
				incorrectProduct = true;
			} catch (SAXException ex) {
				System.out.println(file + " was not well-formed!");
				incorrectProduct = true;
			} catch (IOException ex) {
				System.out.println(file + " was not accesible!");
				incorrectProduct = true;
			}

		} while (incorrectProduct);
		Product product = (Product) unmarshal.unmarshal(file);
		System.out.println("Added to the database: " + product);
		productManager.add(product);
		int productId = dbManager.getLastId();
		List<Component> components = product.getComponents();
		for (Component component : components) {
			componentManager.give(productId, component.getId());
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
			System.out.println("17. Change your user name");
			System.out.println("18. Change your password");
			System.out.println("0. Go back");
			int choice = 20;
			do {
				System.out.println("Introduce the number of the option you would like to choose: ");
				choice = Integer.parseInt(reader.readLine()); // We save the chosen option in an integer
			} while (choice < 0 || choice > 18);
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
				userManager.updateUserName(bossName);
				return;
			case 18:
				userManager.updatePassword(bossName);
				return;
			case 0:
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
		System.out.println("Now you will find the contracts: ");
		List<ContractWorker> contracts = contractWorkerManager.showContracts();
		for (ContractWorker contract : contracts) {
			System.out.println(contract);
		}
		// System.out.println("Choose the ID of the desired contract:");
		Integer contractId = new Integer(0);
		boolean wrongtext = false;
		do {
			System.out.println("Choose the ID of the desired contract:");
			try {
				contractId = Integer.parseInt(reader.readLine());
				List<Integer> listId = contractWorkerManager.getIds();
				if (listId.contains(contractId)) {
					wrongtext = false;
				} else {
					wrongtext = true;
				}
			} catch (NumberFormatException ex) {
				wrongtext = true;
				System.out.println("It's not a int, please enter a int.");
			}
		} while (wrongtext);

		Worker worker = new Worker(name, position, Date.valueOf(start_date), nationality, contractId);

		String username = "";
		boolean distictUser = false;
		do {
			System.out.println("Introduce a username for the worker: ");
			username = reader.readLine();
			List<String> existUsernames = workerManager.getUsernames();
			if (existUsernames.contains(username)) {
				distictUser = true;
			} else {
				distictUser = false;
			}
		} while (distictUser);

		String UserName = username;
		System.out.print("Password:");
		String password = getPassword();
		System.out.println("the default password for a worker is: password");
		// Create the password's hash
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] hash = md.digest();
		int roleId = 2;
		// Get the chosen role from the database
		Role chosenRole = userManager.getRole(roleId);
		// Create the user and store it
		User user = new User(UserName, hash, chosenRole);
		userManager.createUser(user);
		worker.setUsername(UserName);
		workerManager.add(worker);
	}

	private static void fireWorker() throws Exception {
		// BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		// System.out.println("Choose a worker to delete, type its ID: ");
		workerManager.printWorkers();
		Integer worker_id = new Integer(0);
		boolean wrongtext = false;
		do {
			System.out.println("Choose a worker to delete, type its ID: ");
			do {
				try {
					worker_id = Integer.parseInt(reader.readLine());
					wrongtext = false;
				} catch (NumberFormatException ex) {
					wrongtext = true;
					System.out.println("It's not a int, please enter a int.");
				}
			} while (wrongtext);
		} while (workerManager.getWorker(worker_id) == null);
		Worker worker = workerManager.getWorker(worker_id);

		String name = worker.getUsername();
		System.out.println(name);
		userManager.deleteWorker(name);
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

	private static void searchPharmacyByName(String pharmacyName) throws Exception {
		List<Pharmacy> pharmacyList = pharmacyManager.searchByName(pharmacyName);
		for (Pharmacy pharmacy : pharmacyList) {
			System.out.println(pharmacy);
		}
	}

	private static void purchaseComponent() throws Exception {
		System.out.println("Please, enter the following information: ");
		System.out.println("Name: ");
		String name = reader.readLine();
		// System.out.println("Price: ");
		Float price = new Float(0.0);
		boolean wrongtext = false;
		do {
			System.out.println("Price: ");
			try {
				price = Float.parseFloat(reader.readLine());
				wrongtext = false;
			} catch (NumberFormatException ex) {
				wrongtext = true;
				System.out.println("It's not a float, please enter a float.");
			}
		} while (wrongtext);
		System.out.println("Supplier: ");
		String supplier = reader.readLine();
		// System.out.println("Number of components: ");
		Integer numbercomponents = new Integer(0);
		boolean wrongtext1 = false;
		do {
			System.out.println("Number of components: ");
			try {
				numbercomponents = Integer.parseInt(reader.readLine());
				wrongtext1 = false;
			} catch (NumberFormatException ex) {
				wrongtext1 = true;
				System.out.println("It's not a int, please enter a int.");
			}
		} while (wrongtext1);
		Component component = new Component(name, price, supplier, numbercomponents);
		componentManager.add(component);
	}

	private static void addComponent() throws Exception {
		// System.out.println("Introduce the selected component's id");
		List<Component> list_components = componentManager.showComponents();
		for (Component components : list_components) {
			System.out.println(components);
		}
		Integer id = new Integer(0);
		boolean wrongtext = false;
		do {
			System.out.println("Introduce the selected component's id");
			do {
				try {
					id = Integer.parseInt(reader.readLine());
					wrongtext = false;
				} catch (NumberFormatException ex) {
					wrongtext = true;
					System.out.println("It's not a int, please enter a int.");
				}
			} while (wrongtext);
		} while (componentManager.getComponent(id) == null);
		Component toBeModified = componentManager.getComponent(id);
		System.out.println(toBeModified);
		int preexistingNumber = toBeModified.getNumberComponents();
		System.out.println("The number of components that are now avaiable are: " + preexistingNumber);
		// System.out.println("Introduce the number of components you want to add: ");
		Integer numbercomponents = new Integer(0);
		boolean wrongtext1 = false;
		do {
			System.out.println("Introduce the number of components you want to add: ");
			try {
				numbercomponents = Integer.parseInt(reader.readLine());
				wrongtext1 = false;
			} catch (NumberFormatException ex) {
				wrongtext1 = true;
				System.out.println("It's not a int, please enter a int.");
			}
		} while (wrongtext1);
		int updatedNumber = preexistingNumber + numbercomponents;
		// Component updateComponent=new Component(updatedNumber);
		toBeModified.setNumberComponents(updatedNumber);
		componentManager.update(toBeModified);
	}

	private static void addContractPharmacy() throws Exception {
		System.out.println("Please, enter the following information: ");
		System.out.println("Type: ");
		String type = reader.readLine();
		// System.out.println("Number of products ");
		Integer n_products = new Integer(0);
		boolean wrongtext = false;
		do {
			System.out.println("Number of products: ");
			try {
				n_products = Integer.parseInt(reader.readLine());
				wrongtext = false;
			} catch (NumberFormatException ex) {
				wrongtext = true;
				System.out.println("It's not a int, please enter a int.");
			}
		} while (wrongtext);
		// System.out.println("Expenditure ");
		Float expenditure = new Float(0.0);
		boolean wrongtext1 = false;
		do {
			System.out.println("Expenditure ");
			try {
				expenditure = Float.parseFloat(reader.readLine());
				wrongtext1 = false;
			} catch (NumberFormatException ex) {
				wrongtext1 = true;
				System.out.println("It's not a float, please enter a float.");
			}
		} while (wrongtext1);
		ContractPharmacy contract = new ContractPharmacy(type, expenditure, n_products);
		System.out.println(contract);
		contractPharmacyManager.add(contract);
	}

	private static void addContractWorker() throws Exception {
		System.out.println("Please, enter the following information: ");
		// System.out.println("Bonus: ");
		Float bonus = new Float(0.0);
		boolean wrongtext = false;
		do {
			System.out.println("Bonus: ");
			try {
				bonus = Float.parseFloat(reader.readLine());
				wrongtext = false;
			} catch (NumberFormatException ex) {
				wrongtext = true;
				System.out.println("It's not a float, please enter a float.");
			}
		} while (wrongtext);
		// System.out.println("Salary: ");
		Float salary = new Float(0.0);
		boolean wrongtext1 = false;
		do {
			System.out.println("Salary: ");
			try {
				salary = Float.parseFloat(reader.readLine());
				wrongtext1 = false;
			} catch (NumberFormatException ex) {
				wrongtext1 = true;
				System.out.println("It's not a float, please enter a float.");
			}
		} while (wrongtext1);
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
		// System.out.println("Contract: ");
		Integer contract_pid = new Integer(0);
		boolean wrongtext = false;
		do {
			System.out.println("Contract: ");
			try {
				contract_pid = Integer.parseInt(reader.readLine());
				wrongtext = false;
			} catch (NumberFormatException ex) {
				wrongtext = true;
				System.out.println("It's not a int, please enter a int.");
			}
		} while (wrongtext);
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
			System.out.println("5. Change your user name");
			System.out.println("6. Change your password");
			System.out.println("0. Go back");
			int choice = 8;
			do {
				System.out.println("Introduce the number of the option you would like to choose: ");
				choice = Integer.parseInt(reader.readLine()); // We save the chosen option in an integer
			} while (choice < 0 || choice > 6);

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
				userManager.updateUserName(pharmacyName);
				return;
			case 6:
				userManager.updatePassword(pharmacyName);
				return;
			case 0:
				return;
			}
		}
	}

	private static void buy() throws Exception {
		List<Product> productList = productManager.showProducts();
		int x = 5;
		while (x != 0) {
			for (Product product : productList) {
				System.out.println(product);
			}
			// System.out.println("Enter the selected product´s id");
			Integer id = new Integer(0);
			boolean wrongtext = false;
			do {
				System.out.println("Enter the selected product´s id");
				do {
					try {
						id = Integer.parseInt(reader.readLine());
						wrongtext = false;
					} catch (NumberFormatException ex) {
						wrongtext = true;
						System.out.println("It's not a int, please enter a int.");
					}
				} while (wrongtext);
			} while (productManager.getProduct(id) == null);
			Product toBeModified = productManager.getProduct(id);
			int preexistingNumber = toBeModified.getNumberProducts();
			System.out.println("The number of products that are now available are: " + preexistingNumber);
			System.out.println("Enter the number of products you want to buy: ");
			int numberproducts = Integer.parseInt(reader.readLine());
			int updatedNumber = preexistingNumber - numberproducts;
			toBeModified.setNumberProducts(updatedNumber);
			productManager.update(toBeModified);
			searchPharmacyByName(pharmacyName);
			// System.out.println("Enter your pharmacy's id: ");
			Integer id_p = new Integer(0);
			boolean wrongtext1 = false;
			do {
				System.out.println("Enter your pharmacy's id: ");
				try {
					id_p = Integer.parseInt(reader.readLine());
					wrongtext1 = false;
				} catch (NumberFormatException ex) {
					wrongtext1 = true;
					System.out.println("It's not a int, please enter a int.");
				}
			} while (wrongtext1);
			pharmacyManager.give(id_p, id);
			System.out.println(
					"If you want to buy another product enter 5, if you don't want to buy another product enter 0.");
			x = Integer.parseInt(reader.readLine());
		}
	}
	public static String getPassword(int length) {
		return getPassword(numbers + caps + low_case, length);
	}
 
	public static String getPassword(String key, int length) {
		String pswd = "";
 
		for (int i = 0; i < length; i++) {
			pswd+=(key.charAt((int)(Math.random() * key.length())));
		}
 
		return pswd;
	}
	public static String getPassword() {
		return getPassword(8);
	}
}