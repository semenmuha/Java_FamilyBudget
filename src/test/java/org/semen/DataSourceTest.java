package org.semen;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class DataSourceTest {

    @Autowired
    private DataSource dataSource;

    @Test
    void testDataSource() throws SQLException {
        assertNotNull(dataSource);
        try (Connection connection = dataSource.getConnection()) {
            assertTrue(connection.isValid(1));
        }
    }
}