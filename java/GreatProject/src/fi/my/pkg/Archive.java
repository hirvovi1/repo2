package fi.my.pkg;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

public class Archive {
	
	private final LinkedList<Item> items = new LinkedList<Item>();
	private HashMap<Id, Item> map = new HashMap<Id, Item>();
	protected final Storage storage;
	
	public Archive(Storage storage) {
		this.storage = storage;
	}

	public void push(Item item) {
		if (item == null) throw new NullPointerException();
		items.addLast(item);
		map.put(item.getId(), item);
	}
	
	public Item pop() {
		if (items.isEmpty()) return null;
		Item temp = items.removeLast();
		map.remove(temp.getId());
		return temp;
	}
	
	public Item get(Id id) {
		return map.get(id);
	}
	
	public void loadAll() {}
	
	public void saveAll() {}
	
	protected void addAll(Collection<Item> itemsToAdd) {
		items.addAll(itemsToAdd);
	}
	
	protected Iterable<Item> getAll(){
		return items;
	}

}
