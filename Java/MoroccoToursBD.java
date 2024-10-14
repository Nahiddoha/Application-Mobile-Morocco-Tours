package mastersidi.fste.umi.ac.moroccotours;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MoroccoToursBD {
    private static final String BD_Nom = "MoroccoToursBD";
    public static final String BD_Table_User = "TableUtilisateur";
    public static final String BD_Table_Plans = "TablePlan";
    private static final int BD_Version = 1;
    public static final String COL_ID = "_id";
    private static final String COL_USERNAME = "_Username";
    private static final String COL_EMAIL = "_email";
    private static final String COL_PASSWORD = "_password";
    public static final String COL_TITLE = "_title";
    public static final String COL_DESCRIPTION = "_description";

    private DBHelper helper;
    private final Context BDcontext;
    private SQLiteDatabase BD;

    public MoroccoToursBD(Context context) {
        BDcontext = context;
        helper = new DBHelper(context);
        BD = helper.getWritableDatabase();
    }

    private class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, BD_Nom, null, BD_Version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqlitedb) {
            // Créer la table utilisateur
            sqlitedb.execSQL("CREATE TABLE " + BD_Table_User + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_USERNAME + " TEXT NOT NULL, " +
                    COL_EMAIL + " TEXT NOT NULL, " +
                    COL_PASSWORD + " TEXT NOT NULL);");

            // Créer la table des plans
            sqlitedb.execSQL("CREATE TABLE " + BD_Table_Plans + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_TITLE + " TEXT NOT NULL, " +
                    COL_DESCRIPTION + " TEXT NOT NULL);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqlitedb, int AncienneVersion, int NouvelleVersion) {
            sqlitedb.execSQL("DROP TABLE IF EXISTS " + BD_Table_User);
            sqlitedb.execSQL("DROP TABLE IF EXISTS " + BD_Table_Plans);
            onCreate(sqlitedb);
        }
    }

    public MoroccoToursBD OuvrirBD() throws SQLException {
        helper = new DBHelper(BDcontext);
        BD = helper.getWritableDatabase();
        return this;
    }

    public void FermerBD() {
        helper.close();
    }

    // Méthodes pour la gestion des utilisateurs
    public int AjouterUser(String username, String email, String password) {
        ContentValues cv = new ContentValues();
        cv.put(COL_USERNAME, username);
        cv.put(COL_EMAIL, email);
        cv.put(COL_PASSWORD, password);
        return (int) BD.insert(BD_Table_User, null, cv);
    }

    public boolean VerifieEmailPassword(String email, String password) {
        String[] columns = {COL_ID};
        String selection = COL_EMAIL + " = ? AND " + COL_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};
        Cursor cursor = BD.query(BD_Table_User, columns, selection, selectionArgs, null, null, null);
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    // Méthode pour vérifier si un email existe dans la base de données
    public boolean verifieEmail(String email) {
        String[] columns = {COL_ID};
        String selection = COL_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = BD.query(BD_Table_User, columns, selection, selectionArgs, null, null, null);
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    // Méthodes pour la gestion des plans
    public void clearAllPlans() {
        BD.delete(BD_Table_Plans, null, null);
    }

    public long insertPlans(String title, String description) {
        ContentValues cv = new ContentValues();
        cv.put(COL_TITLE, title);
        cv.put(COL_DESCRIPTION, description);
        return BD.insert(BD_Table_Plans, null, cv);
    }

    public Cursor showPlans() {
        String[] columns = {COL_ID, COL_TITLE, COL_DESCRIPTION};
        return BD.query(BD_Table_Plans, columns, null, null, null, null, null);
    }
}



