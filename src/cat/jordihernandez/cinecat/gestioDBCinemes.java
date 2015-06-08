/*
Copyright (C) <2015>  <Jordi Hernandez>
Twitter: @jordikarate 
Web: http://www.jordihernandez,cat

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package cat.jordihernandez.cinecat;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class gestioDBCinemes {
	private cinecatDBHelper dbHelper;
	private SQLiteDatabase  dbcine;
	
	public gestioDBCinemes(Context context) {
		dbHelper = new cinecatDBHelper(context);
	}
	
	public void obrir() throws SQLException {
		dbcine = dbHelper.getWritableDatabase();
	}
	
	public void tancar() {
		dbcine.close();
	}
	
	private ContentValues getValors(Cinema cine) {
		ContentValues values = new ContentValues();
		
		values.put(cinecatDB.T_Cinemes.COLUMN_NAME_ID, cine.getId());
		values.put(cinecatDB.T_Cinemes.COLUMN_NAME_CINENOM, cine.getNom());
		values.put(cinecatDB.T_Cinemes.COLUMN_NAME_CINEADRECA, cine.getAdreca());
		values.put(cinecatDB.T_Cinemes.COLUMN_NAME_LOCALITAT, cine.getLocalitat());
		values.put(cinecatDB.T_Cinemes.COLUMN_NAME_COMARCA, cine.getComarca());
		
		return values;
		
	}
	
	public void addCinema(Cinema cine) {
		ContentValues values = new ContentValues();
		values = getValors(cine);
		dbcine.insert(cinecatDB.T_Cinemes.TABLE_NAME, null, values);
	}
	
	public void deleteAll(String table) {
		dbcine.delete(table, null, null);
	}
	
	public List<Cinema> getAllCinemes() {
		List<Cinema> l_cines = new ArrayList<Cinema>();
		Cursor cursor = dbcine.query(cinecatDB.T_Cinemes.TABLE_NAME, null, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Cinema cine = cursorToCinemes(cursor);
			l_cines.add(cine);
			cursor.moveToNext();
		}
		cursor.close();
		return l_cines;
	}
	
	public Cinema getCinema(String id) {
		String[] idcine = {id};
		Cinema cine = new Cinema();
		Cursor cursor = dbcine.query(cinecatDB.T_Cinemes.TABLE_NAME, null, 
				cinecatDB.T_Cinemes.COLUMN_NAME_ID + "= ?" , idcine, null, null, null);
		cursor.moveToFirst();
		cine = cursorToCinemes(cursor);
		
		return cine;
	}
	
	private Cinema cursorToCinemes(Cursor cursor) {
		Cinema cine = new Cinema();
		cine.setId(cursor.getString(0));
		cine.setNom(cursor.getString(1));
		cine.setAdreca(cursor.getString(2));
		cine.setLocalitat(cursor.getString(3));
		cine.setComarca(cursor.getString(4));
		return cine;
	}
	
	public List<Cinema> getCinemaComarca(String comarca) {
		List<Cinema> llista = new ArrayList<Cinema>(); 
		String[] com = {comarca};
		Cursor cursor = dbcine.query(cinecatDB.T_Cinemes.TABLE_NAME, null, 
				cinecatDB.T_Cinemes.COLUMN_NAME_COMARCA + "= ?" , com, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Cinema cine = cursorToCinemes(cursor);
			llista.add(cine);
			cursor.moveToNext();
		}
		cursor.close();
		return llista;
		
	}
}
