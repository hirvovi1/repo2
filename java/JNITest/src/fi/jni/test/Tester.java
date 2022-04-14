package fi.jni.test;

import java.io.File;

public class Tester {
    
	static {
        System.loadLibrary("native");
    }
    
	public Tester() {
		File f = new File(".");
		System.out.println(f.getAbsolutePath());
	}

	public static void main(String[] args) {
		Tester t = new Tester();
		t.sayHello();
	}

	private native void sayHello();

}
