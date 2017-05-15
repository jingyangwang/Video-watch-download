package com.videoplayer.bd;

import com.shipin.player.gui.Filefragment;
import com.shipin.player.gui.network.MRLPanelFragment;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MRL_Activity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mrl);
		FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
		trans.add(R.id.mrl_frag, new MRLPanelFragment());
		//trans.add(R.id.fragment_video, new VideoGridFragment());
		trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		trans.addToBackStack(null);
		trans.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mrl_, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}