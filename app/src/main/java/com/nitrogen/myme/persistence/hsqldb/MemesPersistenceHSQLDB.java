package com.nitrogen.myme.persistence.hsqldb;

import android.util.Log;


import com.nitrogen.myme.application.Services;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.objects.Tag;
import com.nitrogen.myme.persistence.MemesPersistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MemesPersistenceHSQLDB  implements MemesPersistence{

    private List<Meme> memes = new ArrayList<Meme>();
    private List<Tag> tags;
    private final String dbPath;

    public MemesPersistenceHSQLDB(String dbPath) {
        this.dbPath = dbPath;
        tags = Services.getTagsPersistence().getTags();
        createMemeMap();

    }

    private Connection connect() throws SQLException{
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath +";shutdown=true", "SA","");
    }

    private Meme fromResultSet(final ResultSet rs) throws SQLException{
        final String name = rs.getString("name");
        final String sourceRef = rs.getString("source");
        return new Meme(name,"android.resource://com.nitrogen.myme/" + sourceRef);
    }

    private void createMemeMap () {
        try(Connection c = connect() ) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM MEME");
            while (rs.next()) {
                final Meme newMeme = fromResultSet(rs);
                newMeme.setTags(randomTagsAssignment());
                memes.add(newMeme);
            }
            rs.close();
            st.close();
        }
        catch (final SQLException e) {
            Log.e("Connect SQL3",e.getMessage()+ e.getSQLState());
        }
    }

    private List<Tag> randomTagsAssignment(){
        ArrayList<Tag> result = new ArrayList<>();
        final int MIN_TAG_NUM =  1;
        int numOfTag = new Random().nextInt(5) + MIN_TAG_NUM;
        for(int i =0; i < numOfTag; i++){
            result.add(tags.get(new Random().nextInt(tags.size()-1) + MIN_TAG_NUM));
        }
        return  result;
    }

    public List<Meme> getMemes() {
        return Collections.unmodifiableList(memes);
    }

    /* insertMeme
     *
     * purpose: Insert a meme into the database.
     *          Returns True if the meme was added and False otherwise.
     */
    @Override
    public boolean insertMeme(Meme meme) {
        boolean memeInserted = false;

        // don't add duplicates
        if(!memes.contains(meme)) {
            memes.add(meme);
            memeInserted = true;
        }

        return memeInserted;
    }

    /* deleteMeme
     *
     * purpose: Delete a meme from the database.
     */
    @Override
    public Meme deleteMeme(Meme meme) {
        int index;

        index = memes.indexOf(meme);
        if (index >= 0) {
            memes.remove(index);
        }

        return meme;
    }


}
