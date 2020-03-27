package ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Menu {
	private static BufferedReader reader;
	public static void main(String[] args) throws Exception {
		reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Welcome");
		System.out.println("Please type your kind of user:");
		System.out.println("1. Boss");
		System.out.println("2. Worker");
		System.out.println("3. Pharmacy");
		int choice = Integer.parseInt(reader.readLine());
		switch(choice) {
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		}
	}

}
