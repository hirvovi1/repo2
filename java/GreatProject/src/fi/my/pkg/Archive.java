package fi.my.pkg;

import java.util.Collection;
import java.util.LinkedList;

public class Archive {
	
	private final LinkedList<Item> items = new LinkedList<Item>();
	protected final Storage storage;
	
	public Archive(Storage storage) {
		this.storage = storage;
	}

	public void push(Item item) {
		if (item == null) throw new NullPointerException();
		items.addLast(item);
	}
	
	public Item pop() {
		if (items.isEmpty()) return null;
		return items.removeLast();
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
