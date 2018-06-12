package com.nitrogen.myme.persistence;

import java.util.List;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.objects.Tag;

public interface MemesPersistence {
    List<Meme> getMemeSequential();

    List<Meme> getMemeRandom(Meme currentMeme);

    boolean insertMeme(Meme currentMeme);

    Meme deleteMeme(Meme currentMeme);
}
