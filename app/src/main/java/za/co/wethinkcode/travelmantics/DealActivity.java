package za.co.wethinkcode.travelmantics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import static za.co.wethinkcode.travelmantics.FirebaseUtil.openFbReference;

public class DealActivity extends AppCompatActivity {

	private FirebaseDatabase mFirebaseDatabase;
	private DatabaseReference mDatabaseReference;
	EditText txtTitle;
	EditText txtDescription;
	EditText txtPrice;
	private TravelDeal deal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		openFbReference("traveldeals", this);
		mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
		mDatabaseReference = FirebaseUtil.mDatabaseReference;
		txtTitle = (EditText)findViewById( R.id.txtTitle);
		txtDescription = (EditText)findViewById( R.id.txtDescription );
		txtPrice = (EditText)findViewById( R.id.txtPrice );

		Intent intent = getIntent();
		TravelDeal deal = (TravelDeal) intent.getSerializableExtra("Deal");
		if(deal == null) {
			deal = new TravelDeal();
		}
		this.deal = deal;
		txtTitle.setText(deal.getTitle());
		txtDescription.setText(deal.getDescription());
		txtPrice.setText(deal.getPrice());
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {
		switch ( item.getItemId() ) {
			case R.id.save_menu:
				saveDeal();
				Toast.makeText( this, "Deal Saved", Toast.LENGTH_LONG).show();
				clean();
				backToList();
				return true;
			case R.id.delete_menu:
				deleteDeal();
				Toast.makeText(this, "Deal Deleted", Toast.LENGTH_LONG).show();
				backToList();
				return true;
			default:
				return  super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean	onCreateOptionsMenu( Menu menu ) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate( R.menu.save_menu, menu );
		if (FirebaseUtil.isAdmin) {
			menu.findItem(R.id.delete_menu).setVisible(true);
			menu.findItem(R.id.save_menu).setVisible(true);
			enableEditText(true);
		} else {
			menu.findItem(R.id.delete_menu).setVisible(false);
			menu.findItem(R.id.save_menu).setVisible(false);
			enableEditText(false);
		}
		return ( true );
	}

	private void saveDeal() {
		deal.setTitle(txtTitle.getText().toString());
		deal.setDescription(txtDescription.getText().toString());
		deal.setPrice(txtPrice.getText().toString());
		if ( deal.getId() == null) {
			mDatabaseReference.push().setValue( deal );
		} else {
			mDatabaseReference.child(deal.getId()).setValue( deal );
		}
	}

	private void deleteDeal() {
		if (deal == null) {
			Toast.makeText(this, "please save the deal before deleting", Toast.LENGTH_SHORT).show();
			return;
		}
		mDatabaseReference.child(deal.getId()).removeValue();
	}

	private void backToList() {
		Intent intent = new Intent(this, ListActivity.class);
		startActivity(intent);
	}

	private void clean() {
		txtTitle.setText("");
		txtPrice.setText("");
		txtDescription.setText("");
		txtTitle.requestFocus();
	}

	private void enableEditText(boolean isEnabled) {
		txtTitle.setEnabled(isEnabled);
		txtDescription.setEnabled(isEnabled);
		txtPrice.setEnabled(isEnabled);
	}

}
