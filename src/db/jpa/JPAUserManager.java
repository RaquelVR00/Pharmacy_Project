package db.jpa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import db.interfaces.UserManager;
import db.pojos.users.Role;
import db.pojos.users.User;

public class JPAUserManager implements UserManager {

private EntityManager em;
	
	@Override
	public void connect() {
		em = Persistence.createEntityManagerFactory("pharmaceutical-company").createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
		em.getTransaction().commit();
	}

	@Override
	public void disconnect() {
		em.close();
	}

	@Override
	public void createUser(User user) {
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
	}

	@Override
	public void createRole(Role role) {
		em.getTransaction().begin();
		em.persist(role);
		em.getTransaction().commit();
	}

	@Override
	public Role getRole(int id) {
		Query q = em.createNativeQuery("SELECT * FROM roles WHERE id = ?", Role.class);
		q.setParameter(1, id);
		Role role = (Role) q.getSingleResult();
		return role;
	}

	@Override
	public List<Role> getRoles() {
		Query q = em.createNativeQuery("SELECT * FROM roles", Role.class);
		List<Role> roles = (List<Role>) q.getResultList();
		return roles;
	}

	@Override
	public User checkPassword(String username, String password) {
		User user = null;
		try {
			// Create a MessageDigest
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte[] hash = md.digest();
			// Create the query
			Query q = em.createNativeQuery("SELECT * FROM users WHERE username = ? AND password = ?", User.class);
			q.setParameter(1, username);
			q.setParameter(2, hash);
			user = (User) q.getSingleResult();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoResultException nre) {
			// This is what happens when no result is retrieved
			return null;
		}
		return user;
	}
	
	public void updateUserName(String username) {
		//em.getTransaction().begin();
		//em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
		//em.getTransaction().commit();
		System.out.println(username);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		Query q1 = em.createNativeQuery("SELECT * FROM users WHERE USERNAME = ?", User.class);
		q1.setParameter(1, username);
		User user = (User) q1.getSingleResult();
		System.out.println(user);
		System.out.print("Type your new username:");
		String newName = "";
		try {
			newName = reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Begin transaction
		em.getTransaction().begin();
		// Make changes
		user.setUsername(newName);
		// End transaction
		em.getTransaction().commit();
	}
	
	public void updatePassword(String username){
		//em.getTransaction().begin();
		//em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
		//em.getTransaction().commit();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		Query q2 = em.createNativeQuery("SELECT * FROM users WHERE USERNAME = ?", User.class);
		q2.setParameter(1, username);
		User user = (User) q2.getSingleResult();
		System.out.print("Type your new password:");
		String newPassword = "";
		try {
			newPassword = reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		md.update(newPassword.getBytes());
		byte[] hash = md.digest();
		// Begin transaction
		em.getTransaction().begin();
		// Make changes
		user.setPassword(hash);
		// End transaction
		em.getTransaction().commit();
	}
	private void printWorker(String name) {
		Query q1 = em.createNativeQuery("SELECT * FROM users WHERE USERNAME = ?", User.class);
		q1.setParameter(1, name);
		List<User> users = (List<User>) q1.getResultList();
		// Print the employees
		for (User user : users) {
			System.out.println(user);
		}
	}
	
	public void deleteWorker(String name) {
		System.out.println("Company's workers with that name: ");
		printWorker(name);
		System.out.print("Choose a worker to fire. Type it's ID:");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int emp_id = 0;
		try {
			emp_id = Integer.parseInt(reader.readLine());
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Query q2 = em.createNativeQuery("SELECT * FROM users WHERE ID = ?", User.class);
		q2.setParameter(1, emp_id);
		User poorGuy = (User) q2.getSingleResult();
		// Begin transaction
		em.getTransaction().begin();
		// Store the object
		em.remove(poorGuy);
		// End transaction
		em.getTransaction().commit();
	}
}
