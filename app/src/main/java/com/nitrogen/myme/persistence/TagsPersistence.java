package com.nitrogen.myme.persistence;

import java.util.List;
import com.nitrogen.myme.objects.Tag;

public interface TagsPersistence {
    List<Tag> getTags();

    boolean insertTag(Tag tag);

    Tag deleteTag(Tag tag);
}
