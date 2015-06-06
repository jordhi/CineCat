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
/*
	private class DownloadImgCartell extends AsyncTask<String, Void, Bitmap> {
		private final WeakReference imageViewReference;
		
	
		public DownloadImgCartell(ImageView imageView) {
			imageViewReference = new WeakReference(imageView);
		}
		
	    @Override
	    protected Bitmap doInBackground(String... urls) {
   	
	    	Log.d("FILM","descarrega imatge " + urls[0]);
	    	
	       return loadImgCartell(urls[0]);
	        
	        
	    }
*/
	/*
	    @Override
	    protected void onPostExecute(Bitmap result) {  
	    	if (isCancelled()) {
				result = null;
			}

			if (imageViewReference != null) {
				ImageView imageView = (ImageView) imageViewReference.get();
				if (imageView != null) {

					if (result != null) {
						imageView.setImageBitmap(result);
					} else {
						imageView.setImageDrawable(imageView.getContext().getResources()
								.getDrawable(R.drawable.ic_launcher));
					}
				}

			}
	    }
	}
	*/
/*	private Bitmap loadImgCartell(String imageHttpAddress) {
        URL imageUrl = null;
        try {
            imageUrl = new URL(imageHttpAddress);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.setReadTimeout(20000);
            conn.setConnectTimeout(15000);
    	    conn.setRequestMethod("GET");
    	    conn.setDoInput(true);
            conn.connect();
            return BitmapFactory.decodeStream(conn.getInputStream());
        } catch (IOException e) {
            Toast.makeText(activity, "Error cargando la imagen: "+e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return null;
    }
*/
}
