package university.alisawalter;

public class Config {

    public static final String DB_DRIVER = getProperty("database.driver");
    public static final String DB_URL = getProperty("database.url");
    public static final String DB_USERNAME = getProperty("database.username");
    public static final String DB_PASSWORD = getProperty("database.pass");

    private static String getProperty(String name) {
        String property = System
                .getProperties()
                .getProperty(name);
        if (property == null)
            throw new RuntimeException("Property " + property + " is not found");
        return property;
    }
}
