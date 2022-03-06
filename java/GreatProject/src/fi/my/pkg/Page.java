package fi.my.pkg;

import java.util.List;

public class Page {
	
	private final int number;
	private final List<String> lines;

	public Page(int number, List<String> lines) {
		this.number = number;
		this.lines = lines;
	}

	public int getNumber() {
		return number;
	}

	public List<String> getLines() {
		return lines;
	}

	public void print() {
		for (String line : lines) {
			System.out.println(line);
		}
	}

}
