package cat.jordihernandez.cinecat;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListCinemaAdapter extends BaseAdapter {

	private List<Cinema> llista_cinemes;
	private Activity activity;
	
	public ListCinemaAdapter(List<Cinema> llista_cinemes, Activity activity) {
		
		this.llista_cinemes = llista_cinemes;
		this.activity = activity;
	}

	@Override
	public int getCount() {
		return llista_cinemes.size();
	}

	@Override
	public Object getItem(int position) {
		return llista_cinemes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		if(view == null) {
			LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.list_item_cinema, null);
		}
		Cinema cine = llista_cinemes.get(position);
		
		TextView txvNom = (TextView) view.findViewById(R.id.txvPCNomCinema);
		TextView txvLocalitat = (TextView) view.findViewById(R.id.txvLocalitat);
		TextView txvComarca = (TextView) view.findViewById(R.id.txvComarca);
		
		txvNom.setText(cine.getNom());
		txvLocalitat.setText(cine.getLocalitat());
		txvComarca.setText(cine.getComarca());
		
		return view;
	}
	
	
	
	

}
