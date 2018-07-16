package com.nitrogen.myme.persistence.hsqldb;

import android.util.Log;

import com.nitrogen.myme.objects.Tag;
import com.nitrogen.myme.persistence.TagsPersistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.sql.PreparedStatement;

public class TagsPersistenceHSQLDB implements TagsPersistence {

    private List<Tag> tags;
    private final String dbPath;

    //**************************************************
    // Constructor
    //**************************************************

    public TagsPersistenceHSQLDB(String dbPath) {
        this.dbPath = dbPath;
        this.tags = new ArrayList<>();
        defaultTags(dbPath);
    }

    private Connection connect() throws SQLException{
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath +";shutdown=true", "SA","");
    }

    private void defaultTags(String dbPath){
        try(Connection c = connect()){
            final Statement st = c.createStatement();
            final ResultSet rs = st.executeQuery("SELECT * FROM TAG");
            while (rs.next()) {
                tags.add(new Tag(rs.getString("tagname")));
            }
            rs.close();
            st.close();

        }
        catch (final SQLException e){
            Log.e("Connect SQL",e.getMessage()+ e.getSQLState());

        }
    }


    //**************************************************
    // Methods
    //**************************************************

    @Override
    public List<Tag> getTags() {
        return Collections.unmodifiableList(tags);
    }

    /* insertTag
     *
     * purpose: Insert a tag into the database.
     *          Returns True if the tag was added and False otherwise.
     */
    @Override
    public boolean insertTag(Tag tag) {
        boolean tagAdded = false;
        try(Connection c = connect()){

            if(!tags.contains(tag)) {
                tagAdded = true;
            }
            if(tagAdded){
                final PreparedStatement in = c.prepareStatement("INSERT INTO tag VALUES(?)");
                in.setString(1,tag.getName());
                in.executeUpdate();
                tags.add(tag);
            }

        }
        catch (final SQLException e){
            Log.e("Connect SQL",e.getMessage()+ e.getSQLState());
        }

        return tagAdded;
    }

    /* deleteTag
     *
     * purpose: Delete a tag from the database.
     */
    @Override
    public Tag deleteTag(Tag tag) {
        int index;

        index = tags.indexOf(tag);
        if (index >= 0) {
            tags.remove(index);
        }

        return tag;
    }
}
