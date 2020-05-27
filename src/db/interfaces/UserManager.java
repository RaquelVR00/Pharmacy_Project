package db.interfaces;

import java.util.List;

import db.pojos.users.Role;
import db.pojos.users.User;

public interface UserManager {
	
	public void connect();
	public void disconnect();
	public void createUser(User user);
	public void createRole(Role role);
	public Role getRole(int id);
	public List<Role> getRoles();
	public User checkPassword(String username, String password);
	public void updateUserName(String username);
	public void updatePassword(String username);
	public void deleteWorker(String name);
}
