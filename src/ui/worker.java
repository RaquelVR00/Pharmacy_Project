package ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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

public class worker {
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
		dbManager.connect();
		dbManager= new SQLiteManager();
		productManager=dbManager.getProductManager();
		workerManager=dbManager.getWorkerManager();
		componentManager=dbManager.getComponentManager();
		pharmacyManager=dbManager.getPharmacyManager();
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
			System.out.println("Introduce the selected dog´s id: ");
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
		System.out.println("Introduce the number of products you want to add: ");
		int numberproducts=Integer.parseInt(reader.readLine());
		Products toBeModified=ProductManager.getProduct(id);
		Products updateProduct=new Products(numberproducts);
		productManager.update(updateProduct);	
	}
	
	private static String searchProduct() throws Exception{
		System.out.println("Please, introduce the name of the product you want to add: ");
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
		productManager.addProduct(product);
	}
}


	
