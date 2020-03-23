package db.interfaces;
import db.pojos.Component;

public interface ComponentManager {
	public void add(Component component);
	public list searchByName(name String);
}
