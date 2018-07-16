package com.nitrogen.myme.tests.Business;

import com.nitrogen.myme.application.Services;
import com.nitrogen.myme.business.AccessMemeTemplates;
import com.nitrogen.myme.business.Exceptions.TemplateNotFoundException;
import com.nitrogen.myme.tests.utils.TestUtils;

import org.junit.Test;

import org.junit.After;
import org.junit.Before;

import java.io.File;
import java.io.IOException;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class AccessMemeTemplatesIT {
    private File tempDB;
    private AccessMemeTemplates accessMemeTemplates;

    @Before
    public void setUp() throws IOException {
        System.out.println("Starting tests for AccessMemeTemplates...");

        // build database
        tempDB = TestUtils.copyDB();

        accessMemeTemplates = new AccessMemeTemplates();
        assertNotNull(accessMemeTemplates);
    }

    @Test
    public void testInstanceNotNull() {
        AccessMemeTemplates newInstance = new AccessMemeTemplates();
        assertNotNull(newInstance);
    }

    /* Method: getTemplates() */

    @Test
    public void testGetTemplates() {
        // Retrieve all templates in the database
        System.out.println("...Testing getTemplates()");
        assertTrue(accessMemeTemplates.getTemplates().size() >= 0);
    }

    /* Method: getTemplateByName() */

    @Test
    public void testGetTemplateByName_validName() {
        // Retrieve a template with a name we know exists in the database
        System.out.println("...Testing getTemplateByID() with a name we know is in the database");

        boolean success = true;

        try{
            accessMemeTemplates.getTemplateByName("Two buttons");
        } catch (TemplateNotFoundException e) {
            success = false;
        }
        assertTrue(success);
    }

    @Test
    public void testGetTemplateByName_invalidName() {
        // Retrieve a template with a name we know doesn't exist in the database
        System.out.println("...Testing getTemplateByName() with an name we know is not in the database");

        boolean success = true;

        try{
            accessMemeTemplates.getTemplateByName("");
        } catch (TemplateNotFoundException e) {
            success = false;
        }
        assertFalse(success);
    }

    @After
    public void tearDown() {
        // delete file
        tempDB.delete();
        // forget DB
        Services.clean();

        System.out.println("\nFinished tests.\n");
    }
}
