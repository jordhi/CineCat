package cat.jordihernandez.cinecat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;


public class Cinemes extends Activity {
	private List<Cinema> llista_cinemes = new ArrayList<Cinema>();
	private Activity act;
	private ListView lstView_cinemes;
	private Spinner spnComarca;
	private List<String> llista_comarques;
	private List<Cinema> llista_cinemes_filtre = new ArrayList<Cinema>();
	//private static final String URL = "http://gencat.cat/llengua/cinema/cinemes.xml";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cinemes);
		act = this;
		lstView_cinemes = (ListView) findViewById(R.id.lstCinemes);
		//new DownloadXmlTask().execute(URL);
		
		//Tots els cinemes
		gestioDBCinemes gcine = new gestioDBCinemes(this);
		gcine.obrir();
		llista_cinemes = gcine.getAllCinemes();
		gcine.tancar();
		
		crearFiltreComarques();
		//injectarListViewCinemes();
		
		lstView_cinemes.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int position,
					long arg3) {
				Cinema cine = (Cinema) adapter.getItemAtPosition(position);
				String id = cine.getId();
				Intent i = new Intent(act,InfoFilmsXCinema.class);
				i.putExtra("idcine", id);
				startActivity(i);
			}
		});
	}
	
	private void injectarListViewCinemes() {
		//Omplir el ListView
		ListCinemaAdapter adapter = new ListCinemaAdapter(llista_cinemes_filtre, act);
		lstView_cinemes.setAdapter(adapter);
	}
	
	
	private void crearFiltreComarques() {
		//cerca de les comarques uníques dins la llista de cinemes
		ListIterator<Cinema> it = llista_cinemes.listIterator();
		llista_comarques = new ArrayList<>();
		//Recórrer Cinemes i omplir amb el nom les comarques
		llista_comarques.add(" Tots"); //Afegim Totes amb l'espai pq aparegui en primera posició al ordernar
		while(it.hasNext()) {
			Cinema cine = (Cinema)it.next();
			llista_comarques.add(cine.getComarca());
		}
		//Agafar les úniques (que no es repeteixin) i ordenar
		Set<String> comarcaUnica = new HashSet<String>(llista_comarques);
		llista_comarques = new ArrayList<>(comarcaUnica);
		Collections.sort(llista_comarques);
		
		//omplir l'spinner de comarques
		spnComarca = (Spinner) findViewById(R.id.spnComarca);
		final ArrayAdapter<String> adapter_comarques = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, llista_comarques);
		adapter_comarques.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnComarca.setAdapter(adapter_comarques);
		
		
		//capturar el item seleccionat i filrar per comarca
		spnComarca.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			@Override
			public void onItemSelected(AdapterView<?> adapter, View view, int position,
					long id) {
				if(position == 0) llista_cinemes_filtre = llista_cinemes;
				else llista_cinemes_filtre = getCinemesComarca(llista_comarques.get(position));
				injectarListViewCinemes();
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapter) {
				
			}
			
		});
		
	}
	
	protected List<Cinema> getCinemesComarca(String comarca) {
		ListIterator<Cinema> it = llista_cinemes.listIterator();
		List<Cinema> filtre = new ArrayList<>();
		//Recórrer Cinemes i afegir a la llista els que coincideixen amb la comarca demanada
		while(it.hasNext()) {
			Cinema cine = (Cinema)it.next();
			if(cine.getComarca().equals(comarca)) 
				filtre.add(cine);
			
		}
		return filtre;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cinemes, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
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
	
	// Implementation of AsyncTask used to download XML feed from cinemes.xml.
/*	private class DownloadXmlTask extends AsyncTask<String, Void, Void> {
	    @Override
	    protected Void doInBackground(String... urls) {
	    	Log.d("CINEMA","URL: " + urls[0]);
	        try {
	            loadXmlFromNetwork(urls[0]);
	        } catch (IOException e) {
	        	e.getStackTrace();
	        }
			return null;
	    }

	    @Override
	    protected void onPostExecute(Void v) {  
	    	//Carregar el layout i innjectar el Listview de cinemes
	    	ListCinemaAdapter adapter = new ListCinemaAdapter(llista_cinemes, act);
			lstView_cinemes.setAdapter(adapter);
	    }
	}
	*/
	// Uploads XML from stackoverflow.com, parses it, and combines it with
	// HTML markup. Returns HTML string.
/*	private void loadXmlFromNetwork(String urlString) throws IOException {
	    InputStream stream = null;
	    // Instantiate the parser
	    CinemaParseXML cinemaParseXML = new CinemaParseXML();
  	    	    
	    try {
	        stream = downloadUrl(urlString);     
	        //get CINEMES from parser
	        llista_cinemes =  cinemaParseXML.parse(stream, this);
    
	    } catch (XmlPullParserException e) {
			e.printStackTrace();
		} finally {
	        if (stream != null) {
	            stream.close();
	        } 
	     }
	    
	   	    
	}*/

	// Given a string representation of a URL, sets up a connection and gets
	// an input stream.
/*	private InputStream downloadUrl(String urlString) throws IOException {
		 Log.d("CINEMA","downloadUrl " + urlString);
	    URL url = new URL(urlString);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setReadTimeout(10000);
	    conn.setConnectTimeout(15000);
	    conn.setRequestMethod("GET");
	    conn.setDoInput(true);
	    // Starts the query
	    conn.connect();
	   
	    return conn.getInputStream();
	}*/
	
	
}
