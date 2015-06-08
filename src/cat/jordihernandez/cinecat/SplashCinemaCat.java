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

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class SplashCinemaCat extends Activity {
	private String[] new_act = new String[4];
	private String[] act = new String[4];
	private SharedPreferences prefs;
	private boolean actualitzat = false;
	private Handler x;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_cinema_cat);
		final String DEFAULT_DATA = "2011-08-31T00:00:00";
		
		x = new Handler();
		String actualitzacio, actualitzacio_nova;
		prefs = getSharedPreferences("cinecat_prefs",0 /* MODE_PRIVATE */ );
		
		//desar dates d'actualitzacio que hi hagin a prefs
		act[0] = prefs.getString("data_cinemes", DEFAULT_DATA);
//		act[1] = prefs.getString("data_pelicules", DEFAULT_DATA);
//		act[2] = prefs.getString("data_cicles", DEFAULT_DATA);
//		act[3] = prefs.getString("data_sessions", DEFAULT_DATA);
		
		//Agafar dates d'actualització dels xml
		new DownloadXmlTask("data_cinemes").execute("http://gencat.cat/llengua/cinema/cinemes.xml");
		//new DownloadXmlTask("data_pelicules").execute("http://gencat.cat/llengua/cinema/provacin.xml");
		//new DownloadXmlTask("data_cicles").execute("http://gencat.cat/llengua/cinema/cicles.xml");
		//new DownloadXmlTask("data_sessions").execute("http://gencat.cat/llengua/cinema/film_sessions.xml");

		
		//x.postDelayed(new iniciarCinecat(), 2000);
		
		
    }
	
	// Implementation of AsyncTask used to download XML feed from cinemes.xml.
	private class DownloadXmlTask extends AsyncTask<String, Void, Void> {
		private String dada;
		public DownloadXmlTask(String d) {
			dada=d;
		}
	    @Override
	    protected Void doInBackground(String... urls) {
	    	//Log.d("SPLASH","URL: " + urls[0]);
	        try {
	            loadXmlFromNetwork(dada, urls[0]);
	        } catch (IOException | XmlPullParserException e) {
	        	e.getStackTrace();
	        }
			return null;
	    }

	    @Override
	    protected void onPostExecute(Void v) {  
	    	//desar data actualitzacio i descarregar dadaes si cal
	    	SharedPreferences.Editor editor = prefs.edit();
	    	switch(dada) {
	    		case "data_cinemes":
	    			//si les dates no són iguals
	    			if(!(act[0].equals(new_act[0]))) {
	    				//desar la nova data
	    				editor.putString(dada, new_act[0]); 
	    				//descarregar els nous valors
	    				actualitzat = false;
	    				//Log.d("SPLASH","cal nova actualitzacio:" + dada);
	    			}
	    			else actualitzat = true;
	    			break;
	    			
	    		case "data_pelicules": 
	    			//si les dates no són iguals
	    			if(!(act[1].equals(new_act[1]))) {
	    				//desar la nova data
	    				editor.putString(dada, new_act[1]); 
	    				//descarregar els nous valors
	    				//Log.d("SPLASH","nova actualitzacio:" + dada);
	    			}
	    			break;
	    			
	    		case "data_cicles": 
	    			//si les dates no són iguals
	    			if(!(act[2].equals(new_act[2]))) {
	    				//desar la nova data
	    				editor.putString(dada, new_act[2]); 
	    				//descarregar els nous valors
	    				//Log.d("SPLASH","nova actualitzacio:" + dada);
	    			}
	    			break;
	    		case "data_sessions": 
	    			//si les dates no són iguals
	    			if(!(act[3].equals(new_act[3]))) {
	    				//desar la nova data
	    				editor.putString(dada, new_act[3]); 
	    				//descarregar els nous valors
	    				//Log.d("SPLASH","nova actualitzacio:" + dada);
	    			}
	    			break;
	    	}
			editor.commit();
	    	
			//missatge actualitzat
	    	//Toast t = Toast.makeText(getApplicationContext(), dada + " " + actualitzat, 100);
	    	Toast t = Toast.makeText(getApplicationContext(), new String(actualitzat?"Actualitzat":"Actualitzant..."), 100);
	    	t.show();
	    	
	    	x.postDelayed(new iniciarCinecat(), 2000);
	    	
	    }
	}
	
	
	class iniciarCinecat implements Runnable {

		@Override
		public void run() {
			Intent i = new Intent(SplashCinemaCat.this, MainActivity.class);
			i.putExtra("act", actualitzat);
            startActivity(i);
            finish();
		}
		
	}
		
	
	private void loadXmlFromNetwork(String dada, String urlString) throws IOException, XmlPullParserException {
		InputStream stream = null;
		//Preparem el parser
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();   	
			
        //posem el fitxer xml en un stream
        stream = downloadUrl(urlString); 
        //Log.d("SPLASH","stream " + new String((stream==null)?"nul":"no nul"));
        //posem l'stream al parser
        xpp.setInput(stream, null);
	        
        //parsejar l'xml
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_DOCUMENT) {
            //	Log.d("SPLASH","start doc ");
            } else if(eventType == XmlPullParser.START_TAG) {
            	if((xpp.getName()).equals("dataroot")) {
            		switch(dada) {
            			case "data_cinemes": new_act[0] = xpp.getAttributeValue(null, "generated");break;
            			case "data_pelicules": new_act[1] = xpp.getAttributeValue(null, "generated");break;
            			case "data_cicles": new_act[2] = xpp.getAttributeValue(null, "generated");break;
            			case "data_sessions": new_act[3] = xpp.getAttributeValue(null, "generated");break;
            		}
            		
            	//	Log.d("SPLASH","dataroot " + dada);
            	}else {
            		skip(xpp);
            	}
            } 
            eventType = xpp.next();
         }
	}
		
	// Given a string representation of a URL, sets up a connection and gets
		// an input stream.
		private InputStream downloadUrl(String urlString) throws IOException {
		//	Log.d("SPLASH","downloadUrl " + urlString);
		    URL url = new URL(urlString);
		    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		 //   Log.d("SPLASH","connectant ");
		    conn.setReadTimeout(10000 /* milliseconds */);
		    conn.setConnectTimeout(15000 /* milliseconds */);
		    conn.setRequestMethod("GET");
		    conn.setDoInput(true);
		    // Starts the query
		    conn.connect();
		//    Log.d("SPLASH","connectat ");
		    return conn.getInputStream();
		}
		
		private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
	        if (parser.getEventType() != XmlPullParser.START_TAG) {
	            throw new IllegalStateException();
	        }
	        int depth = 1;
	        while (depth != 0) {
	            switch (parser.next()) {
	            case XmlPullParser.END_TAG:
	                depth--;
	                break;
	            case XmlPullParser.START_TAG:
	                depth++;
	                break;
	            }
	        }
	     }
	
}
