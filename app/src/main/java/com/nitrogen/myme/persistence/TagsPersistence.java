package com.nitrogen.myme.persistence;

import com.nitrogen.myme.objects.Tag;

import java.util.List;

public interface TagsPersistence {

    List<Tag> getTags();

    Boolean insertTag(Tag newTag);

    Boolean removeTag(Tag targetTag);
}
