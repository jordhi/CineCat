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
