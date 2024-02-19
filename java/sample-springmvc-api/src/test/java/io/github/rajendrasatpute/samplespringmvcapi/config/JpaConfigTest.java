package io.github.rajendrasatpute.samplespringmvcapi.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {JpaConfig.class})
@TestPropertySource(locations = "classpath:application-test.properties")
class JpaConfigTest {
    @Autowired
    ApplicationContext context;

    @Test
    void shouldCreateDataSourceBean() throws SQLException {
        assertTrue(context.containsBean("dataSource"));
        DataSource dataSource = (DataSource) context.getBean("dataSource");
        assertEquals("TEST",dataSource.getConnection().getCatalog());
    }
}