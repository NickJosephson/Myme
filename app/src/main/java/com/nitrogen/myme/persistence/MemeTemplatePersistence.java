package com.nitrogen.myme.persistence;

import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.objects.TemplateMeme;

import java.util.List;

public interface MemeTemplatePersistence {
    List<TemplateMeme> getTemplates();

    boolean insertTemplate(TemplateMeme template);

    TemplateMeme deleteTemplate(TemplateMeme template);
}
