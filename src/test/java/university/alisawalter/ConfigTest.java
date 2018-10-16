package university.alisawalter;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class ConfigTest {

    @Test
    public void testGetDatabaseParameter() {
        assertNotNull(Config.DB_DRIVER);
        assertNotNull(Config.DB_PASSWORD);
        assertNotNull(Config.DB_URL);
        assertNotNull(Config.DB_USERNAME);
    }
}