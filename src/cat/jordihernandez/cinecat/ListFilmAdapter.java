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

public class ListFilmAdapter extends BaseAdapter {
	private List<Film> llista_films;
	private Context activity;
	private ImageLoader imgloader = ImageLoader.getInstance();
	
	public ListFilmAdapter(List<Film> llista_films, Context activity) {
		this.llista_films = llista_films;
		this.activity = activity;
		ImageLoaderConfiguration imgconfig = new ImageLoaderConfiguration.Builder(activity)
			.memoryCacheExtraOptions(240,400)
			.build();
		//imgloader.init(ImageLoaderConfiguration.createDefault(activity));
		imgloader.init(imgconfig);

	}
	
	@Override
	public int getCount() {
		return llista_films.size();
	}

	@Override
	public Object getItem(int position) {
		return llista_films.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	class ViewHolder {
		public ImageView imgCartell;
		public TextView txvTitolFilm;
		public TextView txvDireccio;
		public TextView txvAny;
		public TextView txvVersio;
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder = null;
		View v = view;
		Film film = llista_films.get(position);
				
		if(v == null) {
			LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.list_item_film, null);
			
			holder = new ViewHolder();
			holder.txvTitolFilm = (TextView) v.findViewById(R.id.txvTitolFilm);
			holder.imgCartell = (ImageView) v.findViewById(R.id.imgCartell);
			holder.txvDireccio = (TextView) v.findViewById(R.id.txvDireccio);
			holder.txvAny = (TextView) v.findViewById(R.id.txvAny);
			holder.txvVersio = (TextView) v.findViewById(R.id.txvVersio);
			
			v.setTag(holder);
			
		}else {
			holder = (ViewHolder) v.getTag();
		}
		
		//Log.d("FILM", String.format("Get view %d", position));
			
		//new DownloadImgCartell(imgCartell).execute("http://gencat.cat/llengua/cinema/" + film.getCartell());
			
		holder.txvTitolFilm.setText(film.getTitol());
		holder.txvDireccio.setText(film.getDireccio());
		holder.txvAny.setText(film.getAny());
		holder.txvVersio.setText(film.getVersio());
		//holder.imgCartell.setImageDrawable(activity.getResources().getDrawable(R.drawable.no_imatge));
		//imgCartell.setImageBitmap(llista_bmp.get(position));
		
		String url_img = "http://gencat.cat/llengua/cinema/" + film.getCartell();
		//Log.d("FILM", "Cartell:" + url_img);
		
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
		.cacheInMemory(false)
		.cacheOnDisk(true)
        .showImageForEmptyUri(R.drawable.no_imatge)
        .showImageOnFail(R.drawable.no_imatge)
        .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
        .build();
		imgloader.displayImage(url_img, holder.imgCartell, defaultOptions);
		
		//Picasso.with(activity).load(url_img).placeholder(R.drawable.no_imatge).resize(65, 75).tag(activity).centerInside().into(holder.imgCartell);
		
		return v;
	}

}
