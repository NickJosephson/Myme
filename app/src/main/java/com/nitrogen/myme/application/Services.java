package com.nitrogen.myme.application;

import com.nitrogen.myme.persistence.MemesPersistence;
import com.nitrogen.myme.persistence.stubs.MemesPersistenceStub;

public class Services
{
    private static MemesPersistence memesPersistence = null;

    public static synchronized MemesPersistence getMemesPersistence()
    {
        if (memesPersistence == null)
        {
            memesPersistence = new MemesPersistenceStub();
        }

        return memesPersistence;
    }

}
