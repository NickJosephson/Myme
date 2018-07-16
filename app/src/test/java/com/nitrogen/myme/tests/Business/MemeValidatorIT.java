package com.nitrogen.myme.tests.Business;

import com.nitrogen.myme.application.Services;
import com.nitrogen.myme.business.AccessMemes;
import com.nitrogen.myme.business.Exceptions.InvalidMemeException;
import com.nitrogen.myme.business.MemeValidator;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.objects.Tag;
import com.nitrogen.myme.tests.utils.TestUtils;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class MemeValidatorIT {

    private File tempDB;
    private AccessMemes accessMemes;
    private MemeValidator memeValidator;
    private Meme goodMeme;// nice
    private Meme badMeme;
    private List<Tag> goodTags;

    @Before
    public void setUp() throws IOException {
        System.out.println("Starting tests for MemeValidator.\n");

        // build database
        tempDB = TestUtils.copyDB();

        memeValidator = new MemeValidator();
        assertNotNull(memeValidator);

        goodMeme = new Meme("good name");
        badMeme = new Meme("");

        goodTags = new ArrayList<>();
        goodTags.add(new Tag("dank"));
        goodTags.add(new Tag("wholesome"));

        accessMemes = new AccessMemes();
        Assert.assertNotNull(accessMemes);
    }

    // helper method
    private String generateStringOfLen (int len) {
        String result = "";

        for(int i = 0 ; i < len ; i++)
            result += " ";

        return  result;
    }

    @Test
    public void testValidateName_validName() {
        System.out.println("Testing validateName() with a name which we know is valid");

        goodMeme.setName("good name");
        assert (memeValidator.validateName(goodMeme));
        goodMeme.setName(generateStringOfLen(memeValidator.MAX_NAME_LEN));// edge case
        assert (memeValidator.validateName(goodMeme));
        goodMeme.setName(generateStringOfLen(1));// edge case
        assert (memeValidator.validateName(goodMeme));
    }

    @Test(expected = InvalidMemeException.class)
    public void testValidateName_invalidNameAlreadyExists() {
        System.out.println("Testing validateName() with a name which we know is invalid, because name is not unique");

        badMeme.setName(accessMemes.getMemes().get(0).getName());

        // should throw expected exception
        memeValidator.validateName(badMeme);
    }

    @Test
    public void testValidateTags_validTags() {
        System.out.println("Testing validateTags() with tags we know are valid");

        goodMeme.setTags(goodTags);

        assert(memeValidator.validateTags(goodMeme));
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
