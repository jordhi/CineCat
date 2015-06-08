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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Films extends Activity {
	private List<Film> llista_films = new ArrayList<Film>();
	private Context act;
	//private static final String URL = "http://gencat.cat/llengua/cinema/provacin.xml";
	//private static final String URL_IMG = "http://gencat.cat/llengua/cinema/";
   	private ListView lstView_films;
	
   	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_films);
		act = this;
		String idcicle = null;
		
		lstView_films = (ListView) findViewById(R.id.lsvFilms);
		//new DownloadXmlTask().execute(URL);
		
		Intent i = getIntent();
		idcicle = i.getStringExtra("idCicle");
		
		
		List<String> llista_idfilms = new ArrayList<>();
		gestioDBFilms gcine = new gestioDBFilms(this);
		gcine.obrir();
		//Agafar totes els id de les pel·lícules que tenen el idCicle
		if(idcicle != null) {
			gestioDBSessions gses = new gestioDBSessions(this);
			gses.obrir();
			/* CAL AGAFAR FILM UNIC SINO REPETEIX EL FILM I DONA ERROR */
			llista_idfilms = gses.getFilmsCicle(idcicle);
			
			//Agafar només una pelicula
			Set<String> filmUnic = new HashSet<String>(llista_idfilms);
			llista_idfilms = new ArrayList<>(filmUnic);
			Collections.sort(llista_idfilms);
			
			llista_films = gcine.getFilms(llista_idfilms);
		} else { //Sino agafem totes les pel·lícules
			
			llista_films =  gcine.getAllFilms();
			
		}
		gcine.tancar();
		
		ListFilmAdapter adapter = new ListFilmAdapter(llista_films, act);
    	lstView_films.setAdapter(adapter);
    	
    	lstView_films.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int position,
					long arg3) {
				Film film = (Film) adapter.getItemAtPosition(position);
				String id = film.getId();
				Intent i = new Intent(act,InfoFilm.class);
				i.putExtra("idfilm", id);
				startActivity(i);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.films, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case  R.id.InfoCineCat:
			InfoCinecatDialog info = new InfoCinecatDialog();
			info.show(getFragmentManager(), null);
			break;
		case R.id.SortirApp:
			System.exit(0);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	

	
	
}
