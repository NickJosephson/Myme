package com.nitrogen.myme.tests;

import com.nitrogen.myme.tests.Business.AccessFavouritesIT;
import com.nitrogen.myme.tests.Business.AccessMemesIT;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccessFavouritesIT.class,
        AccessMemesIT.class
})
public class AllIntegrationTests
{
    /* empty */
}
