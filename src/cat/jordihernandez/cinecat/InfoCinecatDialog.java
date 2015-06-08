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

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
