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
import android.widget.ListView;

public class Cicles extends Activity {
	private Context c;
	private ListView lstView_cicles;
	private List<Cicle> llista_cicles = new ArrayList<Cicle>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cicles);
		c = this;
		lstView_cicles =  (ListView) findViewById(R.id.lsvCicles);
		
		//omplir llista de cicles
		gestioDBCicles gcine = new gestioDBCicles(this);
		gcine.obrir();
		llista_cicles = gcine.getAllCicles();
		gcine.tancar();
		
		//injectar llistview
		ListCiclesAdapter adapter = new ListCiclesAdapter(llista_cicles,c);
		lstView_cicles.setAdapter(adapter);
		
		lstView_cicles.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int position,
					long arg3) {
				Cicle ses = (Cicle) adapter.getItemAtPosition(position);
				String id = ses.getId();
				Intent i = new Intent(c,Films.class);
				i.putExtra("idCicle", id);
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cicles, menu);
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
