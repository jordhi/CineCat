package cat.jordihernandez.cinecat;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class InfoFilm extends Activity {
	List<Sessio> llista_sessions = new ArrayList<>();
	Context act;
	ImageLoader imgloader; 
	private Button txvSinopsi;
	private String strSinopsi;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info_film);
		
		Intent i = getIntent();
		String idfilm = i.getStringExtra("idfilm");
		act = this;
		
		
		//find views
		TextView txvTitol = (TextView) findViewById(R.id.txvIFTitol);
		TextView txvAny = (TextView) findViewById(R.id.txvIFAny);
		TextView txvVersio = (TextView) findViewById(R.id.txvIFversio);
		TextView txvDirector = (TextView) findViewById(R.id.txvIFDirector);
		TextView txvQualif = (TextView) findViewById(R.id.txvIFQualif);
		TextView txvInterprets = (TextView) findViewById(R.id.txvIFInterprets);
		//txvSinopsi = (Button) findViewById(R.id.txvIFSinopsi);
		ListView lsvSessions = (ListView) findViewById(R.id.lsvIFSessioCine);
		ImageView imgIFcartell = (ImageView) findViewById(R.id.imgIFcartell);
		
		//search film
		gestioDBFilms gfilm = new gestioDBFilms(this);
		gfilm.obrir();
		Film film = gfilm.getFilm(idfilm);
		gfilm.tancar();
		
		//print data film
		txvTitol.setText(film.getTitol());
		txvAny.setText(film.getAny());
		txvVersio.setText(film.getVersio());
		txvDirector.setText(film.getDireccio());
		txvQualif.setText(film.getQualificacio());
		txvInterprets.setText(film.getInterprets());
		//txvSinopsi.setText(film.getSinopsi());
		strSinopsi = film.getSinopsi();
		
		//Cartell
		
		imgloader = ImageLoader.getInstance();
		imgloader.init(ImageLoaderConfiguration.createDefault(this));
		String url_img = "http://gencat.cat/llengua/cinema/" + film.getCartell();
		
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
		.cacheInMemory(false)
		.cacheOnDisk(true)
        .showImageForEmptyUri(R.drawable.no_imatge)
        .showImageOnFail(R.drawable.no_imatge)
        .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
        .build();
		imgloader.displayImage(url_img, imgIFcartell, defaultOptions);
		
		
		//Carregar cinemes on fan la película
		gestioDBSessions gsessio = new gestioDBSessions(this);
		gsessio.obrir();
		llista_sessions = gsessio.getSessioFilm(idfilm);
		gsessio.tancar();
		
		ListSessionsAdpater adapter = new ListSessionsAdpater(llista_sessions, this);
		lsvSessions.setAdapter(adapter);
		
		lsvSessions.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int position,
					long arg3) {
				Sessio ses = (Sessio) adapter.getItemAtPosition(position);
				String id = ses.getCineid();
				Intent i = new Intent(act,InfoFilmsXCinema.class);
				i.putExtra("idcine", id);
				startActivity(i);
				imgloader.destroy();
				finish();
			}
		});
		
	}

	public void clickSinopsi(View v) {
		Bundle args = new Bundle();
		args.putString("sinopsi", strSinopsi);
		SinopsiDialog info = new SinopsiDialog();
		info.setArguments(args);
		info.show(getFragmentManager(), null);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.info_film, menu);
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
