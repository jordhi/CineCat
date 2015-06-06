package cat.jordihernandez.cinecat;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class cinecatDBHelper extends SQLiteOpenHelper {
	public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "cinecat.db";

    private static final String SQL_CREATE_CINEMES =
    	    "CREATE TABLE IF NOT EXISTS " + cinecatDB.T_Cinemes.TABLE_NAME + " (" +
    	    cinecatDB.T_Cinemes.COLUMN_NAME_ID + " VARCHAR PRIMARY KEY," +
    	    cinecatDB.T_Cinemes.COLUMN_NAME_CINENOM + " VARCHAR" + "," +
    	    cinecatDB.T_Cinemes.COLUMN_NAME_CINEADRECA + " VARCHAR" + "," +
    	    cinecatDB.T_Cinemes.COLUMN_NAME_LOCALITAT + " VARCHAR" + "," + 
    	    cinecatDB.T_Cinemes.COLUMN_NAME_COMARCA + " VARCHAR" + " );";

    private static final String SQL_CREATE_FILMS =
    		"CREATE TABLE IF NOT EXISTS " + cinecatDB.T_Pelicules.TABLE_NAME + " (" +
    		cinecatDB.T_Pelicules.COLUMN_NAME_ID + " VARCHAR PRIMARY KEY," +
    		cinecatDB.T_Pelicules.COLUMN_NAME_PRIORITAT + " VARCHAR" + "," +
    		cinecatDB.T_Pelicules.COLUMN_NAME_TITOL + " VARCHAR" + "," +
    		cinecatDB.T_Pelicules.COLUMN_NAME_SITUACIO + " VARCHAR" + "," +
    		cinecatDB.T_Pelicules.COLUMN_NAME_ANY + " VARCHAR" + "," +
    		cinecatDB.T_Pelicules.COLUMN_NAME_CARTELL + " VARCHAR" + "," +
    		cinecatDB.T_Pelicules.COLUMN_NAME_ORIGINAL + " VARCHAR" + "," +
    		cinecatDB.T_Pelicules.COLUMN_NAME_DIRECCIO + " VARCHAR" + "," +
    		cinecatDB.T_Pelicules.COLUMN_NAME_INTERPRETS + " VARCHAR" + "," +
    		cinecatDB.T_Pelicules.COLUMN_NAME_SINOPSI + " VARCHAR" + "," +
    		cinecatDB.T_Pelicules.COLUMN_NAME_VERSIO + " VARCHAR" + "," +
    		cinecatDB.T_Pelicules.COLUMN_NAME_QUALIFICACIO + " VARCHAR" + "," +
    		cinecatDB.T_Pelicules.COLUMN_NAME_TRAILER + " VARCHAR" + "," +
    		cinecatDB.T_Pelicules.COLUMN_NAME_WEB + " VARCHAR" + "," +
    		cinecatDB.T_Pelicules.COLUMN_NAME_ESTRENA + " VARCHAR" + " );";
    
    private static final String SQL_CREATE_CICLES =
    		"CREATE TABLE IF NOT EXISTS " + cinecatDB.T_Cicles.TABLE_NAME + " (" +
    		cinecatDB.T_Cicles.COLUMN_NAME_ID + " VARCHAR PRIMARY KEY," +
    		cinecatDB.T_Cicles.COLUMN_NAME_CICLENOM + " VARCHAR" + "," +
    		cinecatDB.T_Cicles.COLUMN_NAME_CICLEINFO + " VARCHAR" + "," +
    		cinecatDB.T_Cicles.COLUMN_NAME_IMGCICLE + " VARCHAR" + " );";
    
    private static final String SQL_CREATE_SESSIONS =
    		"CREATE TABLE IF NOT EXISTS " + cinecatDB.T_Sessions.TABLE_NAME + " (" +
    		cinecatDB.T_Sessions.COLUMN_NAME_SESID + " VARCHAR" + "," +
    		cinecatDB.T_Sessions.COLUMN_NAME_TITOL + " VARCHAR" + "," +
    		cinecatDB.T_Sessions.COLUMN_NAME_SESDATA + " VARCHAR" + "," +
    		cinecatDB.T_Sessions.COLUMN_NAME_CINENOM + " VARCHAR" + "," +
    		cinecatDB.T_Sessions.COLUMN_NAME_LOCALITAT + " VARCHAR" + "," +
    		cinecatDB.T_Sessions.COLUMN_NAME_COMARCA + " VARCHAR" + "," +
    		cinecatDB.T_Sessions.COLUMN_NAME_VER + " VARCHAR" + "," +
    		cinecatDB.T_Sessions.COLUMN_NAME_CICLEID + " VARCHAR" + "," +
    		cinecatDB.T_Sessions.COLUMN_NAME_CINEID + " VARCHAR" + "," +
    		cinecatDB.T_Sessions.COLUMN_NAME_IDFILM + " VARCHAR" + "," +
    		" FOREIGN KEY (" + cinecatDB.T_Sessions.COLUMN_NAME_CICLEID + ")" + 
    		" REFERENCES " + cinecatDB.T_Cicles.TABLE_NAME + "(" + cinecatDB.T_Cicles.COLUMN_NAME_ID + ")," +
    		" FOREIGN KEY (" + cinecatDB.T_Sessions.COLUMN_NAME_CINEID + ")" + 
    		" REFERENCES " + cinecatDB.T_Cinemes.TABLE_NAME + "(" + cinecatDB.T_Cinemes.COLUMN_NAME_ID + ")," +
    		" FOREIGN KEY (" + cinecatDB.T_Sessions.COLUMN_NAME_IDFILM + ")" + 
    		" REFERENCES " + cinecatDB.T_Pelicules.TABLE_NAME + "(" + cinecatDB.T_Pelicules.COLUMN_NAME_ID + ")" +
    		" );";
    		
	public cinecatDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_CINEMES);
		db.execSQL(SQL_CREATE_FILMS);
		db.execSQL(SQL_CREATE_CICLES);
		db.execSQL(SQL_CREATE_SESSIONS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	

}
