package com.nitrogen.myme.persistence;

import com.nitrogen.myme.objects.Tag;

import java.util.List;

public interface TagsPersistence {

    List<Tag> getTags();

    boolean insertTag(Tag tag);

    Tag deleteTag(Tag tag);
}
