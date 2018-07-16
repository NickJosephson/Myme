package com.nitrogen.myme;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SearchForMemeTest.class,
        ViewMemeTest.class,
        RecommendationTest.class,
        ExportingTest.class,
        FavouritingTest.class,
        CreatingMemeTest.class,
        SavingInvalidMemeTest.class,
        MemeEditingButtonsTest.class,
})
public class AllAcceptanceTests {
}
