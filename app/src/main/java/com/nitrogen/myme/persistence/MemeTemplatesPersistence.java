package com.nitrogen.myme.persistence;

import com.nitrogen.myme.objects.TemplateMeme;

import java.util.List;

public interface MemeTemplatesPersistence {
    List<TemplateMeme> getTemplates();
}
