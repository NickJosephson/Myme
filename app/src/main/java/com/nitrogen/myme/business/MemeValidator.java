package com.nitrogen.myme.business;

import com.nitrogen.myme.application.Services;
import com.nitrogen.myme.business.Exceptions.InvalidMemeException;
import com.nitrogen.myme.business.Exceptions.MemeHasDuplicateNameException;
import com.nitrogen.myme.business.Exceptions.MemeHasDuplicateTagsException;
import com.nitrogen.myme.business.Exceptions.MemeHasNoTagsException;
import com.nitrogen.myme.business.Exceptions.MemeHasNonexistentTagsException;
import com.nitrogen.myme.business.Exceptions.MemeNameTooLongException;
import com.nitrogen.myme.business.Exceptions.NamelessMemeException;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.objects.Tag;
import com.nitrogen.myme.persistence.MemesPersistence;
import com.nitrogen.myme.persistence.TagsPersistence;

import java.util.List;

public class MemeValidator {
    public final int MAX_NAME_LEN = 32;
    private TagsPersistence tagsPersistence;
    private MemesPersistence memesPersistence;

    public MemeValidator () {
        tagsPersistence = Services.getTagsPersistence();
        memesPersistence = Services.getMemesPersistence();
    }
    public MemeValidator (MemesPersistence memesPersistenceGiven,
                          TagsPersistence tagsPersistenceGiven) {
        tagsPersistence = tagsPersistenceGiven;
        memesPersistence = memesPersistenceGiven;
    }

    /* hasValidName
     *
     * purpose: decide if a meme's name is valid. valid length is >= 1 and <= 32
     */
    public boolean validateName(final Meme memeGiven) {
        if(memeGiven == null)
            throw new InvalidMemeException();
        else if(memeGiven.getName() == null)
            throw new InvalidMemeException();
        else if(memeGiven.getName().length() <= 0)
            throw new NamelessMemeException();
        else if(memeGiven.getName().length() > MAX_NAME_LEN)
            throw new MemeNameTooLongException();
        else if(!originalMemeName(memeGiven.getName()))
            throw new MemeHasDuplicateNameException();

        return true;
    }

    private boolean originalMemeName (final String name) {
        boolean isOriginal = true;
        List<Meme> memeList = memesPersistence.getMemes();

        for(int i = 0 ; i < memeList.size() && isOriginal ; i++) {
            if(memeList.get(i).getName().equals( name ))
                isOriginal = false;
        }

        return isOriginal;
    }

    /* hasValidTags
     *
     * purpose: decide if a meme's tags are valid. valid length is >= 1 and <= 32
     */
    public boolean validateTags(final Meme memeGiven) {
        if(memeGiven == null)
            throw new InvalidMemeException();
        else if(memeGiven.getTags() == null)
            throw new InvalidMemeException();
        else if(memeGiven.getTags().size() <= 0)
            throw new MemeHasNoTagsException();
        else if(containsNonExistentTag(memeGiven))
            throw new MemeHasNonexistentTagsException();
        else if(containsDuplicateTags(memeGiven))
            throw new MemeHasDuplicateTagsException();

        return true;
    }

    private boolean containsDuplicateTags (Meme memeGiven) {
        boolean hasDuplicate = false;
        List<Tag> tagList = memeGiven.getTags();

        // loops through memeGiven.getTags()
        for(int indSlow = 0 ; indSlow < tagList.size() ; indSlow++) {
            // for each tag, check if it exists later
            for(int indFast = indSlow +1 ; indFast < tagList.size() ; indFast++) {
                if(tagList.get(indSlow).equals(tagList.get(indFast))) {
                    hasDuplicate = true;
                    break;
                }
            }

            if(hasDuplicate)
                break;
        }

        return hasDuplicate;
    }

    private boolean containsNonExistentTag(Meme memeGiven) {
        boolean result = false;

        // check if ANY tags within memeGiven is not in tagsPersistence
        for(Tag currMemeTag : memeGiven.getTags()) {
            boolean contains = false;

            // check if currMemeTag is not in tagsPersistence
            for(Tag currPersTag : tagsPersistence.getTags()) {

                // contains = true, if both tags have the same name
                if(currMemeTag.toString().equals(currPersTag.toString())) {
                    contains = true;
                    break;
                }
            }

            if(!contains) {
                result = true;
                break;
            }
        }

        return result;
    }
}
