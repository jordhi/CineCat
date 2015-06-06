package cat.jordihernandez.cinecat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class InfoCinecatDialog extends DialogFragment {
	

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		SharedPreferences prefs = getActivity().getSharedPreferences("cinecat_prefs", 0);
		LayoutInflater inflater = getActivity().getLayoutInflater();
		
		View v = inflater.inflate(R.layout.dialog_infocinecat, null);
		TextView txvdata = (TextView) v.findViewById(R.id.txvDataAct);
		
		String act = prefs.getString("data_cinemes", "no actualitzat");
		txvdata.setText(act);
		builder.setView(v)
				.setTitle(R.string.infoCinecat)
				.setPositiveButton("D'acord", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   dialog.cancel();
	                   }
	               });
		
		return builder.create();
	}

		
	
}
