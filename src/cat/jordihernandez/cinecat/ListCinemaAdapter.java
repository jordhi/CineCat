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
