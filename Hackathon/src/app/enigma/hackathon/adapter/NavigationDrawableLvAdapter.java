package app.enigma.hackathon.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import app.enigma.hackathon.R;
import app.enigma.hackathon.object.NavigationDrawerItem;

public class NavigationDrawableLvAdapter extends ArrayAdapter<NavigationDrawerItem> {
	Context context;
	static int layoutResourceId = R.layout.lv_item_navigation_drawer;
	static ArrayList<NavigationDrawerItem> al = new NavigationDrawerItem().getItem();

	public NavigationDrawableLvAdapter(Context context) {
		super(context, layoutResourceId, al);
		this.context = context;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;

		if(row == null) {
			LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = li.inflate(layoutResourceId, null);
		}

		NavigationDrawerItem ali = al.get(position);

		if(ali != null) {
			TextView tvTitle = (TextView) row.findViewById(R.id.tvTitle);

			if (tvTitle != null) {
				tvTitle.setText(ali.getTitle());
				tvTitle.setCompoundDrawablesWithIntrinsicBounds(ali.getDrawableResource(), 0, 0, 0);
			}
		}

		return row;
	}
}
