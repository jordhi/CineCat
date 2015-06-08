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

import android.provider.BaseColumns;

public class cinecatDB {

	private cinecatDB() {}
	
	//Taula CINEMES
	public static abstract class T_Cinemes implements BaseColumns {
		public static final String TABLE_NAME = "CINEMES";
		public static final String COLUMN_NAME_ID = "cineid";
		public static final String COLUMN_NAME_CINENOM = "cinenom";
		public static final String COLUMN_NAME_CINEADRECA = "cineadreca";
		public static final String COLUMN_NAME_LOCALITAT = "localitat";
		public static final String COLUMN_NAME_COMARCA = "comarca";
    }
	//Taula PEL·LÍCULES (FILMS)
	public static abstract class T_Pelicules implements BaseColumns {
		public static final String TABLE_NAME = "FILMS";
		public static final String COLUMN_NAME_ID = "idfilm";
		public static final String COLUMN_NAME_PRIORITAT = "prioritat";
		public static final String COLUMN_NAME_TITOL = "titol";
		public static final String COLUMN_NAME_SITUACIO = "situacio";
		public static final String COLUMN_NAME_ANY= "any";
		public static final String COLUMN_NAME_CARTELL = "cartell";
		public static final String COLUMN_NAME_ORIGINAL = "original";
		public static final String COLUMN_NAME_DIRECCIO = "direccio";
		public static final String COLUMN_NAME_INTERPRETS = "interprets";
		public static final String COLUMN_NAME_SINOPSI = "sinopsi";
		public static final String COLUMN_NAME_VERSIO = "versio";
		public static final String COLUMN_NAME_QUALIFICACIO = "qualificacio";
		public static final String COLUMN_NAME_TRAILER = "trailer";
		public static final String COLUMN_NAME_WEB = "web";
		public static final String COLUMN_NAME_ESTRENA = "estrena";
	}
	
	//Taula CICLES
	public static abstract class T_Cicles implements BaseColumns {
		public static final String TABLE_NAME = "CICLES";
		public static final String COLUMN_NAME_ID = "cicleid";
		public static final String COLUMN_NAME_CICLENOM = "ciclenom";
		public static final String COLUMN_NAME_CICLEINFO = "cicleinfo";
		public static final String COLUMN_NAME_IMGCICLE = "imgcicle";
	}
	
	//Taula SESSIONS
	public static abstract class T_Sessions implements BaseColumns {
		public static final String TABLE_NAME = "SESSIONS";
		public static final String COLUMN_NAME_IDFILM = "idfilm";
		public static final String COLUMN_NAME_SESID = "sesid";
		public static final String COLUMN_NAME_CINEID = "cineid";
		public static final String COLUMN_NAME_TITOL = "titol";
		public static final String COLUMN_NAME_SESDATA= "sesdata";
		public static final String COLUMN_NAME_CINENOM = "cinenom";
		public static final String COLUMN_NAME_LOCALITAT = "localitat";
		public static final String COLUMN_NAME_COMARCA = "comarca";
		public static final String COLUMN_NAME_CICLEID = "cicleid";
		public static final String COLUMN_NAME_VER = "ver";
	}
}
