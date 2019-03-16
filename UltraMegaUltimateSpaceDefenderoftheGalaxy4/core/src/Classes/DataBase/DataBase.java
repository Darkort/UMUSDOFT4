package Classes.DataBase;

public abstract class DataBase {
    private static  int databaseVersion;
    private static  String databaseName;
    private static  String SCORE_TABLENAME;
    private static  String SCORE_FIELD ;
    private static  String databaseCreationQuery;
    private static  String databaseUpdateQuery;


    public abstract Score getHigherScore();
    public abstract void savenewHighScore(int Score);


    public DataBase() {
        databaseVersion = 1;
        databaseName = "umusdotg4";
        SCORE_TABLENAME = "Score";
        SCORE_FIELD = "score";
        databaseCreationQuery="CREATE TABLE "+SCORE_TABLENAME+" (" +
                SCORE_FIELD + " INT DEFAULT 0);";
        databaseUpdateQuery="";
    }

    public static int getDatabaseVersion() {
        return databaseVersion;
    }

    public static String getDatabaseName() {
        return databaseName;
    }

    public static String getScoreTablename() {
        return SCORE_TABLENAME;
    }

    public static String getScoreField() {
        return SCORE_FIELD;
    }

    public static String getDatabaseCreationQuery() {
        return databaseCreationQuery;
    }

    public static String getDatabaseUpdateQuery() {
        return databaseUpdateQuery;
    }
}
