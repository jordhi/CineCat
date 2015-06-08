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

public class gestioDBCicles {
	private cinecatDBHelper dbHelper;
	private SQLiteDatabase  dbcine;
	
	public gestioDBCicles(Context context) {
		dbHelper = new cinecatDBHelper(context);
	}
	
	public void obrir() throws SQLException {
		dbcine = dbHelper.getWritableDatabase();
	}
	
	public void tancar() {
		dbcine.close();
	}
	
	private ContentValues getValors(Cicle cicle) {
		ContentValues values = new ContentValues();
		
		values.put(cinecatDB.T_Cicles.COLUMN_NAME_ID, cicle.getId());
		values.put(cinecatDB.T_Cicles.COLUMN_NAME_CICLENOM, cicle.getNom());
		values.put(cinecatDB.T_Cicles.COLUMN_NAME_CICLEINFO, cicle.getInfo());
		values.put(cinecatDB.T_Cicles.COLUMN_NAME_IMGCICLE, cicle.getImg());
		
		return values;
		
	}
	
	public void addCicle(Cicle cicle) {
		ContentValues values = new ContentValues();
		values = getValors(cicle);
		dbcine.insert(cinecatDB.T_Cicles.TABLE_NAME, null, values);
	}
	
	public void deleteAll(String table) {
		dbcine.delete(table, null, null);
	}
	
	public List<Cicle> getAllCicles() {
		List<Cicle> l_cicles = new ArrayList<Cicle>();
		Cursor cursor = dbcine.query(cinecatDB.T_Cicles.TABLE_NAME, null, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Cicle cicle = cursorToCicles(cursor);
			l_cicles.add(cicle);
			cursor.moveToNext();
		}
		cursor.close();
		return l_cicles;
	}
	
	private Cicle cursorToCicles(Cursor cursor) {
		Cicle cicle = new Cicle();
		cicle.setId(cursor.getString(0));
		cicle.setNom(cursor.getString(1));
		cicle.setInfo(cursor.getString(2));
		cicle.setImg(cursor.getString(3));
		return cicle;
	}
}
