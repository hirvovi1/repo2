package fi.my.pkg;

public abstract class Robot {

	protected Archive archive;
	
	public void saveArchive() {
		archive.saveAll();
	}

	protected void loadArchive() {
		archive.loadAll();
	}

}