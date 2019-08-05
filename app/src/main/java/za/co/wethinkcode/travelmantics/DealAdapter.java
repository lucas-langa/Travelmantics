package za.co.wethinkcode.travelmantics;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static za.co.wethinkcode.travelmantics.FirebaseUtil.openFbReference;

public class DealAdapter extends  RecyclerView.Adapter<DealAdapter.DealViewHolder>{
	ArrayList<TravelDeal> deals;
	private FirebaseDatabase mFirebaseDatabase;
	private DatabaseReference mDatabaseReference;
	private ChildEventListener mChildListener;

	public DealAdapter() {
		openFbReference("traveldeals");
		mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
		mDatabaseReference = FirebaseUtil.mDatabaseReference;
		this.deals = FirebaseUtil.mDeals;

		mChildListener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String s) {

				TravelDeal thing = dataSnapshot.getValue(TravelDeal.class);
				Log.d("Deal: ", thing.getTitle());
				thing.setId(dataSnapshot.getKey());
				deals.add(thing);
				notifyItemInserted(deals.size() - 1);
			}

			@Override
			public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

			}

			@Override
			public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

			}

			@Override
			public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

			}

			@Override
			public void onCancelled(@NonNull DatabaseError databaseError) {

			}


		};
		mDatabaseReference.addChildEventListener(mChildListener);
	}
		@Override
		public DealViewHolder onCreateViewHolder (@NonNull ViewGroup parent,int viewType){
			Context context = parent.getContext();
			View itemView = LayoutInflater.from(context).inflate(R.layout.rv_row, parent,
					false);
			return new DealViewHolder(itemView);
		}

		@Override
		public void onBindViewHolder (@NonNull DealViewHolder holder,int position){
			TravelDeal deal = deals.get(position);
			holder.bind(deal);
		}

		@Override
		public int getItemCount () {
			return deals.size();
		}

		public class DealViewHolder extends RecyclerView.ViewHolder {
			TextView tvTitle;

			public DealViewHolder(View itemView) {
				super(itemView);
				tvTitle = itemView.findViewById(R.id.tvTitle);
			}

			public void bind(TravelDeal deal) {
				tvTitle.setText(deal.getTitle());
			}
		}
	}
