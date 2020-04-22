package db.interfaces;
import java.util.List;

import db.pojos.Component;

public interface ComponentManager {
	
	public void give(int produtcId,int componentId);
	public void add(Component component);
	public Component getComponent(int componentId);
	public List<Component>showComponents();
	public List<Component> searchByName(String name);
	public List<Component> searchBySupplier(String supplier);
}
