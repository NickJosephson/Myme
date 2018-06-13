package com.nitrogen.myme.tests;

import com.nitrogen.myme.tests.Business.AccessMemesTest;
import com.nitrogen.myme.tests.Business.AccessTagsTest;
import com.nitrogen.myme.tests.Business.SearchMemesTest;
import com.nitrogen.myme.tests.Business.SearchTagsTest;
import com.nitrogen.myme.tests.Business.UpdateMemesTest;
import com.nitrogen.myme.tests.Business.UpdateTagsTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccessMemesTest.class,
        AccessTagsTest.class,
        SearchMemesTest.class,
        SearchTagsTest.class,
        UpdateMemesTest.class,
        UpdateTagsTest.class
})
public class AllTests
{
    /* empty */
}
