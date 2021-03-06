package com.nitrogen.myme.application;

import com.nitrogen.myme.persistence.MemeTemplatesPersistence;
import com.nitrogen.myme.persistence.MemesPersistence;
import com.nitrogen.myme.persistence.TagsPersistence;
import com.nitrogen.myme.persistence.hsqldb.MemeTemplatesPersistenceHSQLDB;
import com.nitrogen.myme.persistence.hsqldb.MemesPersistenceHSQLDB;
import com.nitrogen.myme.persistence.hsqldb.TagsPersistenceHSQLDB;

public class Services {
    private static MemesPersistence memesPersistence = null;
    private static MemeTemplatesPersistence memeTemplatesPersistence = null;
    private static TagsPersistence tagsPersistence = null;

    public static synchronized MemesPersistence getMemesPersistence() {
        if (memesPersistence == null) {
            memesPersistence = new MemesPersistenceHSQLDB(Main.getDBPathName());
        }

        return memesPersistence;
    }

    public static synchronized MemeTemplatesPersistence getMemeTemplatePersistence() {
        if (memeTemplatesPersistence == null) {
            memeTemplatesPersistence = new MemeTemplatesPersistenceHSQLDB(Main.getDBPathName());
        }

        return memeTemplatesPersistence;
    }

    public static synchronized TagsPersistence getTagsPersistence() {
        if (tagsPersistence == null) {
            tagsPersistence = new TagsPersistenceHSQLDB(Main.getDBPathName());
        }

        return tagsPersistence;
    }

    public static synchronized void clean() {
        memesPersistence = null;
        memeTemplatesPersistence = null;
        tagsPersistence = null;
    }
}
