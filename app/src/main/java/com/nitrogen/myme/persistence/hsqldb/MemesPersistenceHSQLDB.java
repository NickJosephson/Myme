package com.nitrogen.myme.persistence.hsqldb;

import android.util.Log;


import com.nitrogen.myme.application.Main;
import com.nitrogen.myme.application.Services;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.objects.Tag;
import com.nitrogen.myme.persistence.MemesPersistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
    private List<Meme> currView = new ArrayList<Meme>();

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
        return new Meme(name, sourceRef);
    }

    private void createMemeMap () {
        try(Connection c = connect() ) {
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM MEME");
            while (rs.next()) {
                final Meme newMeme = fromResultSet(rs);
                newMeme.setTags(tagAssignment(rs.getString("name")));
                newMeme.setFavourite(rs.getInt("fav") == 1);
                memes.add(newMeme);
            }
            rs.close();
            st.close();
        }
        catch (final SQLException e) {
            Log.e("Connect SQL3",e.getMessage()+ e.getSQLState());
        }
    }

    private List<Tag> tagAssignment(String name){
        ArrayList<Tag> result = new ArrayList<>();
        try(Connection c = connect()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM MEMETAGS WHERE name=?");
            st.setString(1, name);
            final ResultSet rs = st.executeQuery();
            while (rs.next()){
                result.add(new Tag(rs.getString("tagname")));
            }
            rs.close();
            st.close();
        }
        catch (final SQLException e) {
            Log.e("Connect SQL3",e.getMessage()+ e.getSQLState());
        }
        return result;
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
        boolean memeAdded = false;
        try(Connection c = connect()){
            if(!memes.contains(meme)) {
                memeAdded = true;
            }
            if(memeAdded){
                final PreparedStatement in = c.prepareStatement("INSERT INTO meme VALUES(? , ?, ?)");

                in.setString(1, meme.getName());
                in.setString(2, meme.getImagePath());
                in.setInt(3, meme.isFavourite() ? 1 : 0);
                memes.add(meme);
                PreparedStatement inTag = null;
                for(Tag a : meme.getTags()){
                    inTag = c.prepareStatement("INSERT INTO memetags VALUES(? , ?)");
                    inTag.setString(1, meme.getName());
                    inTag.setString(2, a.getName());
                    inTag.execute();
                }

                in.executeUpdate();
                in.close();
                if(inTag !=null){
                    inTag.close();
                }
            }

        }
        catch (final SQLException e){
            Log.e("Connect SQL",e.getMessage()+ e.getSQLState());
        }
        return memeAdded;
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
    public void updateFav(Meme meme){
        try(Connection c = connect()){
            int fav =0;
            final PreparedStatement in = c.prepareStatement("UPDATE meme SET fav = ? WHERE name = ?");
            if(meme.isFavourite()){
                fav = 1;
            }
            in.setInt(1, fav);
            in.setString(2, meme.getName());

            in.executeUpdate();
            in.close();
        }
        catch (final SQLException e){
            Log.e("Connect SQL",e.getMessage()+ e.getSQLState());
        }
    }

    public void setCurrView(List<Meme> memes){ currView = memes; }

    public List<Meme> getCurrView() { return currView; }
}
