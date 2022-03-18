package fi.my.pkg.storage;

public enum IdUtil {
	INSTANCE;
	
	private int id = 0;
	private Storage storage;
	private final int batchSize = 100;
	
	private IdUtil(){
	}
	
	synchronized int next() {
		if (id == 0) {
			id = load();
			System.out.println("load " + id);
		}
		if (id % batchSize == 0) {
			save(id + batchSize);
		}
		return ++id;
	}

	private int load() {
		return storage.getId();
	}
	
	private void save(int i) {
		System.out.println("save " + i);
		storage.saveId(i);
	}

	void set(Storage storage) {
		this.storage = storage;
	}
	
	void reset() {
		id = 0;
	}

}

