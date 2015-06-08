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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
//import com.nostra13.universalimageloader.core.ImageLoader;

public class ListCiclesAdapter extends BaseAdapter {
	private List<Cicle> llista_cicles;
	private Context c;
	private ImageLoader imgloader = ImageLoader.getInstance();
	
	
	
	public ListCiclesAdapter(List<Cicle> llista_cicles, Context c) {
		this.llista_cicles = llista_cicles;
		this.c = c;
		imgloader.init(ImageLoaderConfiguration.createDefault(c));
	}

	@Override
	public int getCount() {
		return llista_cicles.size();
	}

	@Override
	public Object getItem(int position) {
		return llista_cicles.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	class ViewHolder {
		public ImageView imgCicle;
		public TextView txvNomCicle;
		public TextView txvInfoCicle;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder = null;
		View v = view;
		Cicle cicle = llista_cicles.get(position);
		
		if(v == null) {
			LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.list_item_cicle, null);
			
			holder = new ViewHolder();
			holder.imgCicle = (ImageView) v.findViewById(R.id.imgCicle);
			holder.txvNomCicle = (TextView) v.findViewById(R.id.txvNomCicle);
			holder.txvInfoCicle = (TextView) v.findViewById(R.id.txvInfo);
			v.setTag(holder);
			
		}else {
			holder = (ViewHolder) v.getTag();
		}
		
		holder.txvNomCicle.setText(cicle.getNom());
		holder.txvInfoCicle.setText(cicle.getInfo());
		
		String url_img = "http://gencat.cat/llengua/cinema/" + cicle.getImg();
		//Log.d("CICLES", "Cartell:" + "(" + position + "):" + url_img);
		
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
		.cacheInMemory(true)
        .showImageForEmptyUri(R.drawable.no_imatge)
        .showImageOnFail(R.drawable.no_imatge)
        .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
        .build();
		imgloader.displayImage(url_img, holder.imgCicle, defaultOptions);
		//Picasso.with(c).load("http://gencat.cat/llengua/cinema/"+cicle.getImg()).placeholder(R.drawable.no_imatge).resize(75, 85).onlyScaleDown().tag(c).centerInside().into(holder.imgCicle);

		return v;
	}
	

}
