package com.nitrogen.myme.application;

import com.nitrogen.myme.persistence.MemeTemplatePersistence;
import com.nitrogen.myme.persistence.MemesPersistence;
import com.nitrogen.myme.persistence.TagsPersistence;
import com.nitrogen.myme.persistence.stubs.MemeTemplatePersistenceStub;
import com.nitrogen.myme.persistence.stubs.MemesPersistenceStub;
import com.nitrogen.myme.persistence.stubs.TagsPersistenceStub;

public class Services {
    private static MemesPersistence memesPersistence = null;
    private static MemeTemplatePersistence memeTemplatePersistence = null;
    private static TagsPersistence tagsPersistence = null;

    public static synchronized MemesPersistence getMemesPersistence() {
        if (memesPersistence == null) {
            memesPersistence = new MemesPersistenceStub();
        }

        return memesPersistence;
    }

    public static synchronized MemeTemplatePersistence getMemeTemplatePersistence() {
        if (memeTemplatePersistence == null) {
            memeTemplatePersistence = new MemeTemplatePersistenceStub();
        }

        return memeTemplatePersistence;
    }

    public static synchronized TagsPersistence getTagsPersistence() {
        if (tagsPersistence == null) {
            tagsPersistence = new TagsPersistenceStub();
        }

        return tagsPersistence;
    }

}
