package com.nitrogen.myme.SystemTestUtils;

import com.nitrogen.myme.business.AccessFavourites;
import com.nitrogen.myme.business.AccessMemeTemplates;
import com.nitrogen.myme.business.AccessMemes;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.objects.Tag;

import java.util.List;

public class RVHelper {

    private AccessMemes accessMemes;
    private AccessMemeTemplates accessMemeTemplates;
    private AccessFavourites accessFavourites;

    public RVHelper(){
        accessMemes = new AccessMemes();
        accessMemeTemplates = new AccessMemeTemplates();
        accessFavourites = new AccessFavourites();
    }

    /* getPositionExplore
     *
     * purpose: Return the index of the meme with the given name in the full meme list.
     *          The index is equal to the position in the recycler view.
     *
     *          Returns -1 if no meme is found with the given name.
     *
     */
    public int getPositionExplore(String memeName) {
        return getItemPosition(accessMemes.getMemes(), memeName);
    }

    /* getPositionFavourites
     *
     * purpose: Return the index of the meme with the given name in the favourites list.
     *          The index is equal to the position in the recycler view.
     *
     *          Returns -1 if no meme is found with the given name.
     *
     */
    public int getPositionFavourites(String memeName) {
        return getItemPosition(accessFavourites.getMemes(), memeName);
    }

    /* getItemPosition
     *
     * purpose: Return the index of the meme with the given name in the given list.
     *          The index in the list is the position in the recycler view.
     *
     *          Returns -1 if no meme is found with the given name.
     *
     */
    private int getItemPosition(List<Meme> memes, String memeName) {
        int pos = -1;

        for(int i = 0 ; i < memes.size() && pos == -1; i++) {
            if(accessFavourites.getMemes().get(i).getName().equals(memeName)){
                pos = i;
            }
        }
        return pos;
    }

    /* getItemCountExplore
     *
     * purpose: Return the number of items in the recycler view in the Explore activity.
     *
     */
    public int getItemCountExplore() {
        return accessMemes.getMemes().size();
    }

    /* getItemCountTemplates
     *
     * purpose: Return the number of items in the recycler view in the Favourites activity.
     *
     */
    public int getItemCountFavourites() {
        return accessFavourites.getMemes().size();
    }

    /* getItemCountExplore
     *
     * purpose: Return the number of items in the recycler view when selecting a Template Meme.
     *
     */
    public int getItemCountTemplates() {
        return accessMemeTemplates.getTemplates().size();
    }


    /* getNameAtPosExplore
     *
     * purpose: Return the name of a meme at a given position in the Explore activity.
     *
     */
    public String getNameAtPosExplore(int pos) {
        return accessMemes.getMemes().get(pos).getName();
    }

    /* getNameAtPosFavourites
     *
     * purpose: Return the name of a meme at a given position in the Favourites activity.
     *
     */
    public String getNameAtPosFavourites(int pos) {
        return accessFavourites.getMemes().get(pos).getName();
    }

    public boolean confirmTags(String tag){
        boolean contains= false;
        boolean contains1= false;

        for (int i = 0; i < 2; i++){
            for (Tag a: accessMemes.getCurrView().get(i).getTags()) {
                if (a.getName().equals(tag)){
                    if (i == 0)
                        contains = true;
                    if (i == 1)
                        contains1 = true;
                }

            }
        }


         return contains && contains1;
    }
}
