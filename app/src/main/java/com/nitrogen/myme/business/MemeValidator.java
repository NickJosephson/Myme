package com.nitrogen.myme.business;

import com.nitrogen.myme.application.Services;
import com.nitrogen.myme.business.Exceptions.InvalidMemeException;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.objects.Tag;
import com.nitrogen.myme.persistence.TagsPersistence;

public class MemeValidator {
    private final int MAX_NAME_LEN = 32;
    private TagsPersistence tagsPersistence;

    public MemeValidator () {
        tagsPersistence = Services.getTagsPersistence();
    }

    /**hasValidName
     *
     * purpose: decide if a meme's name is valid. valid length is >= 1 and <= 32
     */
    public void validateName(final Meme memeGiven) {
        if(memeGiven == null)
            throw new InvalidMemeException("memeGiven cannot be null");
        else if(memeGiven.getName() == null)
            throw new InvalidMemeException("memeGiven name cannot be null");
        else if(memeGiven.getName().length() <= 0)
            throw new InvalidMemeException("memeGiven name length must be > 0");
        else if(memeGiven.getName().length() > MAX_NAME_LEN)
            throw new InvalidMemeException("memeGiven name length must be <= " + MAX_NAME_LEN);
    }

    /**hasValidTags
     *
     * purpose: decide if a meme's tags are valid. valid length is >= 1 and <= 32
     */
    public void validateTags(final Meme memeGiven) {
        if(memeGiven == null)
            throw new InvalidMemeException("Meme cannot be null");
        else if(memeGiven.getTags() == null)
            throw new InvalidMemeException("Tags cannot be null");
        else if(memeGiven.getTags().size() <= 0)
            throw new InvalidMemeException("Number of tags must be > 0");
        else if(memeGiven.getTags().size() > tagsPersistence.getTags().size())
            throw new InvalidMemeException("Number of tags must be <= " + tagsPersistence.getTags().size());
        else {
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
                    throw new InvalidMemeException("some tags in Meme do not exist in app");
                }
            }
        }

    }
}
