package com.nitrogen.myme.persistence.hsqldb;

import android.util.Log;

import com.nitrogen.myme.application.Services;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.objects.Tag;
import com.nitrogen.myme.objects.TemplateMeme;
import com.nitrogen.myme.persistence.MemeTemplatePersistence;
import com.nitrogen.myme.persistence.MemesPersistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemeTemplatePersistanceHSQLDB  implements MemeTemplatePersistence {
    private final String dbPath;

    public MemeTemplatePersistanceHSQLDB(String dbPath) {
        this.dbPath = dbPath;
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath +";shutdown=true", "SA","");
    }

    public List<TemplateMeme> getTemplates() {
        ArrayList<TemplateMeme> result = new ArrayList<>();
        try(Connection c = connect()) {
            final PreparedStatement st = c.prepareStatement("SELECT * FROM TEMPLATE");
            final ResultSet rs = st.executeQuery();
            while (rs.next()){
                result.add(new TemplateMeme(rs.getString("name"),rs.getString("source")));
            }
            rs.close();
            st.close();
        }
        catch (final SQLException e) {
            Log.e("Connect SQL3",e.getMessage()+ e.getSQLState());
        }
        return result;
    }

}
