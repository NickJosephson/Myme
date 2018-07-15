package com.nitrogen.myme.tests;

import com.nitrogen.myme.tests.Business.AccessFavouritesIT;
import com.nitrogen.myme.tests.Business.AccessMemeTemplatesIT;
import com.nitrogen.myme.tests.Business.AccessMemesIT;
import com.nitrogen.myme.tests.Business.AccessTagsIT;
import com.nitrogen.myme.tests.Business.MemeValidatorIT;
import com.nitrogen.myme.tests.Business.SearchMemesIT;
import com.nitrogen.myme.tests.Business.SearchTagsIT;
import com.nitrogen.myme.tests.Business.UpdateMemesIT;
import com.nitrogen.myme.tests.Business.UpdateTagsIT;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccessFavouritesIT.class,
        AccessMemesIT.class,
        AccessMemeTemplatesIT.class,
        AccessTagsIT.class,
        MemeValidatorIT.class,
        SearchMemesIT.class,
        SearchTagsIT.class,
        UpdateMemesIT.class,
        UpdateTagsIT.class
})
public class AllIntegrationTests
{
    /* empty */
}
