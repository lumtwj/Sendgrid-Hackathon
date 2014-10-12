package app.enigma.hackathon;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.MenuItem;
import android.widget.ListView;
import app.enigma.hackathon.adapter.BlocksLvAdapter;
import app.enigma.hackathon.api.Blocks;
import app.enigma.hackathon.object.Block;

public class BlockActivity extends Activity {
	SwipeRefreshLayout swipeLayout;
	ListView lvBlock;
	ArrayList<Block> alBlock;
	BlocksLvAdapter aaBlock;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_block);

		ActionBar ab = this.getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);

		swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		lvBlock = (ListView) findViewById(R.id.list);

		alBlock = new ArrayList<Block> ();
		aaBlock = new BlocksLvAdapter(this, alBlock);
		lvBlock.setAdapter(aaBlock);

		swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright, 
				android.R.color.holo_green_light, 
				android.R.color.holo_orange_light, 
				android.R.color.holo_red_light);

		swipeLayout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				new Blocks(BlockActivity.this, swipeLayout, alBlock, aaBlock).execute();
			}
		});

		new Blocks(this, swipeLayout, alBlock, aaBlock).execute();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}

		return true;
	}
}
