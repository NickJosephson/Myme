package com.nitrogen.myme.tests.Business;

import com.nitrogen.myme.business.AccessMemeTemplates;
import com.nitrogen.myme.persistence.MemeTemplatesPersistence;
import com.nitrogen.myme.persistence.stubs.MemeTemplatesPersistenceStub;

import org.junit.Test;

import org.junit.After;
import org.junit.Before;

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

    /* Method: getTemplates() */

    @Test
    public void testGetTemplates() {
        // Retrieve all templates in the database
        System.out.println("...Testing getTemplates()");
        assertTrue(accessMemeTemplates.getTemplates().size() >= 0);
    }


    @After
    public void tearDown() {
        System.out.println("\nFinished tests.\n");
    }
}
