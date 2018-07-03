package com.nitrogen.myme.persistence.hsqldb;

import android.util.Log;


import com.nitrogen.myme.objects.Meme;
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

public class MemesPersistenceHSQLDB  implements MemesPersistence{

    private List<Meme> memes = new ArrayList<Meme>() ;
    private Map<String,Integer> memeMap = new HashMap<String,Integer>();
    private Connection c ;

    public MemesPersistenceHSQLDB(String dbPath) {

        try{
            this.c = DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath , "SA","");
            createMemeMap ();
        }
        catch (final SQLException e){
            Log.e("Connect SQL1",e.getMessage()+ e.getSQLState());

        }

    }

    private Meme fromResultSet(final ResultSet rs) throws SQLException{
        final String name = rs.getString("name");
        final String sourceRef = rs.getString("source");
        return new Meme(name,"android.resource://com.nitrogen.myme/" + sourceRef);
    }

    private void createMemeMap () {
        try
        {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM MEME");
            while (rs.next())
            {
                final Meme newMeme = fromResultSet(rs);
                memes.add(newMeme);
            }
            rs.close();
            st.close();


        }
        catch (final SQLException e)
        {

        }
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
