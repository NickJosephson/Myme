package com.nitrogen.myme.tests.Business;

import com.nitrogen.myme.business.AccessMemeTemplates;
import com.nitrogen.myme.business.AccessMemes;
import com.nitrogen.myme.business.Exceptions.TemplateNotFoundException;
import com.nitrogen.myme.persistence.MemeTemplatesPersistence;
import com.nitrogen.myme.persistence.stubs.MemeTemplatesPersistenceStub;

import org.junit.Test;

import org.junit.After;
import org.junit.Before;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class AccessMemeTemplatesTest {
    private AccessMemeTemplates accessMemeTemplates;

    @Before
    public void setUp() {
        System.out.println("Starting tests for AccessMemeTemplates...");
        // stub database
        MemeTemplatesPersistence templatesStub = new MemeTemplatesPersistenceStub();

        accessMemeTemplates = new AccessMemeTemplates(templatesStub);
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
            accessMemeTemplates.getTemplateByName("Two Buttons Template");
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
        System.out.println("\nFinished tests.\n");
    }
}
