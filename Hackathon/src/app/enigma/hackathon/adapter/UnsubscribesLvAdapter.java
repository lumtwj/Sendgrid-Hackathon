package app.enigma.hackathon.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import app.enigma.hackathon.R;
import app.enigma.hackathon.object.Unsubscribe;

public class UnsubscribesLvAdapter extends ArrayAdapter<Unsubscribe> {
	Context context;
	static int layoutResourceId = R.layout.lv_item_unsubscribe;
	List<Unsubscribe> al;

	public UnsubscribesLvAdapter(Context context, List<Unsubscribe> al) {
		super(context, layoutResourceId, al);
		this.context = context;
		this.al = al;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;

		if(row == null) {
			LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = li.inflate(layoutResourceId, null);
		}

		Unsubscribe ali = al.get(position);

		if (ali != null) {
			TextView tvEmail = (TextView) row.findViewById(R.id.tvEmail);

			if (tvEmail != null) {
				tvEmail.setText(ali.getEmail());
			}
		}

		return row;
	}
}
