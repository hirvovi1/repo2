package fi.my.pkg.dependents;

import java.io.File;

public class Fixture {
    public static final String PATH = "." +
            File.separator + "build" +
            File.separator + "resources" +
            File.separator + "test" + File.separator;
    public static final String IMPORT_PATH = PATH +
            File.separator + "import" + File.separator;
}
