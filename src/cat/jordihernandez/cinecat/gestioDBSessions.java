package cat.jordihernandez.cinecat;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class gestioDBSessions {
	private cinecatDBHelper dbHelper;
	private SQLiteDatabase  dbcine;
	
	public gestioDBSessions(Context context) {
		dbHelper = new cinecatDBHelper(context);
	}
	
	public void obrir() throws SQLException {
		dbcine = dbHelper.getWritableDatabase();
	}
	
	public void tancar() {
		dbcine.close();
	}
	
	private ContentValues getValors(Sessio ses) {
		ContentValues values = new ContentValues();
		
		values.put(cinecatDB.T_Sessions.COLUMN_NAME_IDFILM, ses.getIdfilm());
		values.put(cinecatDB.T_Sessions.COLUMN_NAME_SESID, ses.getSesid());
		values.put(cinecatDB.T_Sessions.COLUMN_NAME_CINEID, ses.getCineid());
		values.put(cinecatDB.T_Sessions.COLUMN_NAME_TITOL, ses.getTitol());
		values.put(cinecatDB.T_Sessions.COLUMN_NAME_SESDATA, ses.getSesdata());
		values.put(cinecatDB.T_Sessions.COLUMN_NAME_CINENOM, ses.getCinenom());
		values.put(cinecatDB.T_Sessions.COLUMN_NAME_LOCALITAT, ses.getLocalitat());
		values.put(cinecatDB.T_Sessions.COLUMN_NAME_COMARCA, ses.getComarca());
		values.put(cinecatDB.T_Sessions.COLUMN_NAME_CICLEID, ses.getCicleid());
		values.put(cinecatDB.T_Sessions.COLUMN_NAME_VER, ses.getVer());
				
		return values;
		
	}
	
	public void addSessio(Sessio ses) {
		ContentValues values = new ContentValues();
		values = getValors(ses);
		dbcine.insert(cinecatDB.T_Sessions.TABLE_NAME, null, values);
	}
	
	public void deleteAll(String table) {
		dbcine.delete(table, null, null);
	}
	
	public List<Sessio> getAllSessions() {
		List<Sessio> l_Ses = new ArrayList<Sessio>();
		Cursor cursor = dbcine.query(cinecatDB.T_Sessions.TABLE_NAME, null, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Sessio ses = cursorToSessions(cursor);
			l_Ses.add(ses);
			cursor.moveToNext();
		}
		cursor.close();
		return l_Ses;
	}
	
	public List<Sessio> getSessio(String id){
		List<Sessio> l_ses = new ArrayList<Sessio>();
		String selection = cinecatDB.T_Sessions.COLUMN_NAME_CINEID + "=?";
		String[] selectionArgs = {id};
		
		Cursor cursor = dbcine.query(cinecatDB.T_Sessions.TABLE_NAME, null,
				selection, selectionArgs, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Sessio ses = cursorToSessions(cursor);
			l_ses.add(ses);
			cursor.moveToNext();
		}
		cursor.close();
		return l_ses;
		
	}
	
	public List<Sessio> getSessioFilm(String id){
		List<Sessio> l_ses = new ArrayList<Sessio>();
		String selection = cinecatDB.T_Sessions.COLUMN_NAME_IDFILM + "=?";
		String[] selectionArgs = {id};
		
		Cursor cursor = dbcine.query(cinecatDB.T_Sessions.TABLE_NAME, null,
				selection, selectionArgs, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Sessio ses = cursorToSessions(cursor);
			l_ses.add(ses);
			cursor.moveToNext();
		}
		cursor.close();
		return l_ses;
		
	}
	
	private Sessio cursorToSessions(Cursor cursor) {
		Sessio ses = new Sessio();
		ses.setSesid(cursor.getString(0));
		ses.setTitol(cursor.getString(1));
		ses.setSesdata(cursor.getString(2));
		ses.setCinenom(cursor.getString(3));
		ses.setLocalitat(cursor.getString(4));
		ses.setComarca(cursor.getString(5));
		ses.setVer(cursor.getString(6));
		ses.setCicleid(cursor.getString(7));
		ses.setCineid(cursor.getString(8));
		ses.setIdfilm(cursor.getString(9));
				
		return ses;
	}
	
	/* Donat un id de cicle retorna totes les id de les pelis que el conformen */
	public List<String> getFilmsCicle(String idcicle) {
		List<String> ids = new ArrayList<>();
		String selection = cinecatDB.T_Sessions.COLUMN_NAME_CICLEID + "=?";
		String[] selectionArgs = {idcicle};
		
		Cursor cursor = dbcine.query(cinecatDB.T_Sessions.TABLE_NAME, null,
				selection, selectionArgs, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Sessio ses = cursorToSessions(cursor);
			ids.add(ses.getIdfilm());
			cursor.moveToNext();
		}
		cursor.close();
		return ids;
	}
}
