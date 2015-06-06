package cat.jordihernandez.cinecat;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class InfoFilmsXCinema extends Activity {
	private TextView txvNomCine, txvAdreca;
	private ListView lsvFilms;
	private Cinema cine;
	private List<Sessio> llista_sessions;
	private Context act;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_films_xcinema);
		Intent i = getIntent();
		String idcine = i.getStringExtra("idcine");
		act = this;
		
		txvNomCine = (TextView) findViewById(R.id.txvPCNomCinema);
		txvAdreca = (TextView) findViewById(R.id.txvPCAdreca);
		lsvFilms = (ListView) findViewById(R.id.lsvPCFilms);
		
		//Agafar de la Taula de cinemes el cinema passat per el Intent
		gestioDBCinemes gcine = new gestioDBCinemes(this);
		gcine.obrir();
		cine = gcine.getCinema(idcine);
		txvNomCine.setText(cine.getNom());
		txvAdreca.setText(cine.getAdreca());
		gcine.tancar();
		
		//Agafar les pel·lícules que fan en el cinema escollit
		gestioDBSessions gsessio = new gestioDBSessions(this);
		gsessio.obrir();
		llista_sessions = gsessio.getSessio(idcine);
		gsessio.tancar();
		
		ListSessionsAdpater adapter = new ListSessionsAdpater(llista_sessions, this);
		lsvFilms.setAdapter(adapter);
		
		lsvFilms.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int position,
					long arg3) {
				Sessio ses = (Sessio) adapter.getItemAtPosition(position);
				String id = ses.getIdfilm();
				Intent i = new Intent(act,InfoFilm.class);
				i.putExtra("idfilm", id);
				startActivity(i);
				finish();
			}
		});
	}
	
	public void goToMaps(View v) {
		Uri gmmIntentUri = Uri.parse("geo:0,0?q="+txvAdreca.getText().toString());
		Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
		mapIntent.setPackage("com.google.android.apps.maps");
		startActivity(mapIntent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.info_films_xcinema, menu);
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
