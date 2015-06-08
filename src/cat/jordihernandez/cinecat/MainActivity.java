/* CINECAT */
/* http://dadesobertes.gencat.cat/ca/cercador/detall-cataleg/?id=7311 */

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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	private boolean act;
	private static final String URL_CINEMES = "http://gencat.cat/llengua/cinema/cinemes.xml";
	private static final String URL_FILMS = "http://gencat.cat/llengua/cinema/provacin.xml";
 	private static final String URL_IMGS = "http://gencat.cat/llengua/cinema/";
	private static final String URL_CICLES = "http://gencat.cat/llengua/cinema/cicles.xml";
	private static final String URL_SESSIONS = "http://gencat.cat/llengua/cinema/film_sessions.xml";

	Button btFilms, btCines, btCicles;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent i = getIntent();
		act = i.getBooleanExtra("act", false);
		
		if(act == false) {
			//esborrar dades antigues
			gestioDBCinemes gcine = new gestioDBCinemes(this);
			gestioDBFilms gfilm = new gestioDBFilms(this);
			gestioDBCicles gcicle = new gestioDBCicles(this);
			gestioDBSessions gses = new gestioDBSessions(this);
			gcine.obrir();
			gfilm.obrir();
			gcicle.obrir();
			gses.obrir();
			gcine.deleteAll(cinecatDB.T_Cinemes.TABLE_NAME);
			gcicle.deleteAll(cinecatDB.T_Cicles.TABLE_NAME);
			gfilm.deleteAll(cinecatDB.T_Pelicules.TABLE_NAME);
			gses.deleteAll(cinecatDB.T_Sessions.TABLE_NAME);
			gcine.tancar();
			gfilm.tancar();
			gcicle.tancar();
			gses.tancar();
			//Actualitzar
			new DownloadXmlTask().execute(URL_CINEMES);
			new DownloadXmlTask().execute(URL_FILMS);
			new DownloadXmlTask().execute(URL_CICLES);
			new DownloadXmlTask().execute(URL_SESSIONS);
		} else {
			btFilms = (Button) findViewById(R.id.btnPelicules);
			btCines = (Button) findViewById(R.id.btnCinemes);
			btCicles = (Button) findViewById(R.id.btnCicles);
			btFilms.setEnabled(true);
			btCines.setEnabled(true);
			btCicles.setEnabled(true);
		}
		
	}

	public void goToCinemes(View view) {
		Intent i = new Intent(this,Cinemes.class);
		startActivity(i);
	}
	
	public void goToFilms(View view) {
		Intent i = new Intent(this,Films.class);
		startActivity(i);
	}
	
	public void goToCicles(View view) {
		Intent i = new Intent(this,Cicles.class);
		startActivity(i);
	}
	
	public void InfCineCat(View v) {
		InfoCinecatDialog info = new InfoCinecatDialog();
		info.show(getFragmentManager(), null);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
			finish();
			break;

		default:
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	// Implementation of AsyncTask used to download XML feed from cinemes.xml.
		private class DownloadXmlTask extends AsyncTask<String, Void, Void> {
			String f;
		    @Override
		    protected Void doInBackground(String... urls) {
		    	//Log.d("MAIN","URL: " + urls[0]);
		    	f = urls[0];
		        try {
		            loadXmlFromNetwork(urls[0]);
		        } catch (IOException e) {
		        	e.getStackTrace();
		        }
				return null;
		    }

		    @Override
		    protected void onPostExecute(Void v) {  
		    	String msg;
		    	//Fer alguna cosa?????
		    	switch (f) {
					case URL_CICLES: msg = "cicles";
						btCicles = (Button) findViewById(R.id.btnCicles);
						btCicles.setEnabled(true);
						break;
					case URL_CINEMES: msg = "cinemes";
						btCines = (Button) findViewById(R.id.btnCinemes);
						btCines.setEnabled(true);
						break;
					case URL_FILMS: msg = "pel·lícules";
						btFilms = (Button) findViewById(R.id.btnPelicules);
						btFilms.setEnabled(true);
						break;
					case URL_SESSIONS: msg = "sessions";break;
				default: msg="res";	break;
				}
		    	Toast t = Toast.makeText(getApplicationContext(), msg, 100);
		    	t.show();
		    }
		}
		
		// Uploads XML from stackoverflow.com, parses it, and combines it with
		// HTML markup. Returns HTML string.
		private void loadXmlFromNetwork(String urlString) throws IOException {
		    InputStream stream = null;
		    // Instantiate the parser
		    
		    
	  	    	    
		    try {
		        stream = downloadUrl(urlString);     
		        //get CINEMES from parser
		        switch(urlString) {
		        	case URL_CINEMES:
		        		CinemaParseXML cinemaParseXML = new CinemaParseXML();
		        		cinemaParseXML.parse(stream, this);
		        		break;
		        	case URL_FILMS:	
		        		FilmParseXML filmParseXML = new FilmParseXML();
		        		filmParseXML.parse(stream, this); 
		        		break;
		        	case URL_CICLES: 
		        		CicleParseXML cicleParseXML = new CicleParseXML();
		        		cicleParseXML.parse(stream, this); 
		        		break;
		        	case URL_SESSIONS: 
		        		SessioParseXML sessioParseXML = new SessioParseXML();
		        		sessioParseXML.parse(stream, this); 
		        		break;
		        }
		       
	    
		    } catch (XmlPullParserException e) {
				e.printStackTrace();
			} finally {
		        if (stream != null) {
		            stream.close();
		        } 
		     }
		    
		   	    
		}

		// Given a string representation of a URL, sets up a connection and gets
		// an input stream.
		private InputStream downloadUrl(String urlString) throws IOException {
			//Log.d("MAIN","downloadUrl " + urlString);
		    URL url = new URL(urlString);
		    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		    conn.setReadTimeout(10000 /* milliseconds */);
		    conn.setConnectTimeout(15000 /* milliseconds */);
		    conn.setRequestMethod("GET");
		    conn.setDoInput(true);
		    // Starts the query
		    conn.connect();
		   
		    return conn.getInputStream();
		}
		
		
}
