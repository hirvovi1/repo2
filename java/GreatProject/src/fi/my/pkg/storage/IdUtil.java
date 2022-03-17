package fi.my.pkg.storage;

public enum IdUtil {
	INSTANCE;
	
	private int id = 0;
	private Storage storage;
	final int batchSize = 100;
	
	private IdUtil(){
	}
	
	int next() {
		if (id == 0) {
			id = load();
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
		storage.saveId(i);
	}

	void set(Storage storage) {
		this.storage = storage;
	}

}

