package org.codingweek.model;

import org.codingweek.db.*;
import org.codingweek.db.entity.*;
import java.util.*;
import java.util.stream.*;

public class MyQueriesModel {
    private static List<Query> myQueries;

    public static List<Query> getMyQueries(String email) {
        if (myQueries == null) {
            DatabaseManager db = DatabaseHandler.getInstance().getDbManager();
            myQueries = db.getAllEntity(Query.class);
        }
        return myQueries.stream()
                .filter(query -> query.getUser().getEmail().equals(email))
                .collect(Collectors.toList());
    }

}
