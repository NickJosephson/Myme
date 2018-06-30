package com.nitrogen.myme.persistence;

import com.nitrogen.myme.objects.Meme;

import java.util.List;

public interface MemeTemplatePersistence {
    List<Meme> getMemes();

    boolean insertMeme(Meme currentMeme);

    Meme deleteMeme(Meme currentMeme);
}
