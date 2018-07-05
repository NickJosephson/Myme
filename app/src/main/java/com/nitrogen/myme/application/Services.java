package com.nitrogen.myme.application;

import com.nitrogen.myme.persistence.MemeTemplatesPersistence;
import com.nitrogen.myme.persistence.MemesPersistence;
import com.nitrogen.myme.persistence.TagsPersistence;
import com.nitrogen.myme.persistence.hsqldb.MemeTemplatesPersistanceHSQLDB;
import com.nitrogen.myme.persistence.hsqldb.MemesPersistenceHSQLDB;
import com.nitrogen.myme.persistence.hsqldb.TagsPersistenceHSQLDB;

public class Services {
    private static MemesPersistence memesPersistence = null;
    private static MemeTemplatesPersistence memeTemplatePersistence = null;
    private static TagsPersistence tagsPersistence = null;

    public static synchronized MemesPersistence getMemesPersistence() {
        if (memesPersistence == null) {
            memesPersistence = new MemesPersistenceHSQLDB(Main.getDBPathName());
        }

        return memesPersistence;
    }

    public static synchronized MemeTemplatesPersistence getMemeTemplatePersistence() {
        if (memeTemplatesPersistence == null) {
            memeTemplatesPersistence = new MemeTemplatesPersistanceHSQLDB(Main.getDBPathName());
        }

        return memeTemplatesPersistence;
    }

    public static synchronized TagsPersistence getTagsPersistence() {
        if (tagsPersistence == null) {
            tagsPersistence = new TagsPersistenceHSQLDB(Main.getDBPathName());
        }

        return tagsPersistence;
    }

}
