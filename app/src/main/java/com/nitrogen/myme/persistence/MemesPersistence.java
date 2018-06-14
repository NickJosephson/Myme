package com.nitrogen.myme.persistence;

import java.util.List;
import com.nitrogen.myme.objects.Meme;

public interface MemesPersistence {
    List<Meme> getMemes();

    boolean insertMeme(Meme currentMeme);

    Meme deleteMeme(Meme currentMeme);
}
