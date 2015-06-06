package cat.jordihernandez.cinecat;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListSessionsAdpater extends BaseAdapter {
	private List<Sessio> llista_sessions = new ArrayList<Sessio>();
	private Context context;
	
	
	public ListSessionsAdpater(List<Sessio> llista_sessions, Context context) {
		this.llista_sessions = llista_sessions;
		this.context = context;
	}

	@Override
	public int getCount() {
		return llista_sessions.size();
	}

	@Override
	public Sessio getItem(int position) {
		return llista_sessions.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	class ViewHolder {
		public TextView txvDataSes;
		public TextView txvTitol;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder = null;
		View v = view;
		Sessio ses = llista_sessions.get(position);
				
		if(v == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.list_item_sessiocine, null);
			
			holder = new ViewHolder();
			holder.txvDataSes= (TextView) v.findViewById(R.id.txvSCDataSessio);
			holder.txvTitol= (TextView) v.findViewById(R.id.txvSCNomPeli);
			v.setTag(holder);
			
		}else {
			holder = (ViewHolder) v.getTag();
		}
		//Log.d("SESSIO", "Context: " + context.getClass().getSimpleName());
		holder.txvDataSes.setText(ses.getSesdata());
		if(context.getClass().getSimpleName().equals("InfoFilm"))
			holder.txvTitol.setText(ses.getCinenom());
		else holder.txvTitol.setText(ses.getTitol());
	
	
		return v;
	}

}
