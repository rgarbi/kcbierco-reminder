package com.kcbierco.db;

import com.kcbierco.fixture.ResourceFileFinder;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;


public class DBConnectionManagerTest {

    @Test
    public void testGetExtensionFromPath() throws IOException {
        DBConnectionManager.init(ResourceFileFinder.findFileInResources("database.properties"));
        OObjectDatabaseTx db = DBConnectionManager.getConnection();
        Assert.assertNotNull(db);
    }

}
