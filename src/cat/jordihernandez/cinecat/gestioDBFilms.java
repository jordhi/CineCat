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

public class gestioDBFilms {
	private cinecatDBHelper dbHelper;
	private SQLiteDatabase  dbcine;
	
	public gestioDBFilms(Context context) {
		dbHelper = new cinecatDBHelper(context);
	}
	
	public void obrir() throws SQLException {
		dbcine = dbHelper.getWritableDatabase();
	}
	
	public void tancar() {
		dbcine.close();
	}
	
	private ContentValues getValors(Film film) {
		ContentValues values = new ContentValues();
		
		values.put(cinecatDB.T_Pelicules.COLUMN_NAME_ID, film.getId());
		values.put(cinecatDB.T_Pelicules.COLUMN_NAME_PRIORITAT, film.getPrioritat());
		values.put(cinecatDB.T_Pelicules.COLUMN_NAME_TITOL, film.getTitol());
		values.put(cinecatDB.T_Pelicules.COLUMN_NAME_SITUACIO, film.getSituacio());
		values.put(cinecatDB.T_Pelicules.COLUMN_NAME_ANY, film.getAny());
		values.put(cinecatDB.T_Pelicules.COLUMN_NAME_CARTELL, film.getCartell());
		values.put(cinecatDB.T_Pelicules.COLUMN_NAME_ORIGINAL, film.getOriginal());
		values.put(cinecatDB.T_Pelicules.COLUMN_NAME_DIRECCIO, film.getDireccio());
		values.put(cinecatDB.T_Pelicules.COLUMN_NAME_INTERPRETS, film.getInterprets());
		values.put(cinecatDB.T_Pelicules.COLUMN_NAME_SINOPSI, film.getSinopsi());
		values.put(cinecatDB.T_Pelicules.COLUMN_NAME_VERSIO, film.getVersio());
		values.put(cinecatDB.T_Pelicules.COLUMN_NAME_QUALIFICACIO, film.getQualificacio());
		values.put(cinecatDB.T_Pelicules.COLUMN_NAME_TRAILER, film.getTrailer());
		values.put(cinecatDB.T_Pelicules.COLUMN_NAME_WEB, film.getWeb());
		values.put(cinecatDB.T_Pelicules.COLUMN_NAME_ESTRENA, film.getEstrena());
		
		return values;
		
	}
	
	public void addFilm(Film film) {
		ContentValues values = new ContentValues();
		values = getValors(film);
		dbcine.insert(cinecatDB.T_Pelicules.TABLE_NAME, null, values);
	}
	
	public void deleteAll(String table) {
		dbcine.delete(table, null, null);
	}
	
	public List<Film> getAllFilms() {
		List<Film> l_Film = new ArrayList<Film>();
		Cursor cursor = dbcine.query(cinecatDB.T_Pelicules.TABLE_NAME, null, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Film film = cursorToFilms(cursor);
			l_Film.add(film);
			cursor.moveToNext();
		}
		cursor.close();
		return l_Film;
	}
	
	public Film getFilm(String id){
		Film film = new Film();
		String selection = cinecatDB.T_Pelicules.COLUMN_NAME_ID + "=?";
		String[] selectionArgs = {id};
		
		Cursor cursor = dbcine.query(cinecatDB.T_Pelicules.TABLE_NAME, null,
				selection, selectionArgs, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			film = cursorToFilms(cursor);
			cursor.moveToNext();
		}
		cursor.close();
		return film;
		
	}
	
	public List<Film> getFilms(List<String>  ids){
		List<Film> films = new ArrayList<>();
		String selection = cinecatDB.T_Pelicules.COLUMN_NAME_ID + " IN (" + makePlaceholders(ids.size()) + ")";
		String[] selectionArgs = ids.toArray( new String[ids.size()] );

		Cursor cursor = dbcine.query(cinecatDB.T_Pelicules.TABLE_NAME, null,
				selection, selectionArgs, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Film film = cursorToFilms(cursor);
			films.add(film);
			cursor.moveToNext();
		}
		cursor.close();
		return films;
		
	}
	
	//crear el comodins necessaris per a una consulta amb comodins variables
	String makePlaceholders(int len) {
	    if (len < 1) {
	        // It will lead to an invalid query anyway ..
	        throw new RuntimeException("No placeholders");
	    } else {
	        StringBuilder sb = new StringBuilder(len * 2 - 1);
	        sb.append("?");
	        for (int i = 1; i < len; i++) {
	            sb.append(",?");
	        }
	        return sb.toString();
	    }
	}
	
	private Film cursorToFilms(Cursor cursor) {
		Film film = new Film();
		
		film.setId(cursor.getString(0));
		film.setPrioritat(cursor.getString(1));
		film.setTitol(cursor.getString(2));
		film.setSituacio(cursor.getString(3));
		film.setAny(cursor.getString(4));
		film.setCartell(cursor.getString(5));
		film.setOriginal(cursor.getString(6));
		film.setDireccio(cursor.getString(7));
		film.setInterprets(cursor.getString(8));
		film.setSinopsi(cursor.getString(9));
		film.setVersio(cursor.getString(10));
		film.setQualificacio(cursor.getString(11));
		film.setTrailer(cursor.getString(12));
		film.setWeb(cursor.getString(13));
		film.setEstrena(cursor.getString(14));
				
		return film;
	}
}
