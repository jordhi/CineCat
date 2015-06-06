package cat.jordihernandez.cinecat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class SinopsiDialog extends DialogFragment {
	private String strSinopsi;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	
		LayoutInflater inflater = getActivity().getLayoutInflater();
		
		View v = inflater.inflate(R.layout.dialog_sinopsi, null);
		TextView txvdata = (TextView) v.findViewById(R.id.txvDSinopsi);
		strSinopsi = getArguments().getString("sinopsi");
		txvdata.setText(strSinopsi);
		builder.setView(v)
				.setTitle("Sinopsi")
				.setPositiveButton("D'acord", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   dialog.cancel();
	                   }
	               });
		
		return builder.create();
	}
	
	

}
