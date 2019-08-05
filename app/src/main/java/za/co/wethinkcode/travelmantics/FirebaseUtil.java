package za.co.wethinkcode.travelmantics;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FirebaseUtil {
	public static FirebaseDatabase mFireDatabase;
	public static DatabaseReference mDatabaseReference;
	private static FirebaseUtil firebaseUtil;
	public static ArrayList<TravelDeal> mDeals;

	private FirebaseUtil(){};

	public static void openFbReference( String  ref ) {
		if ( firebaseUtil == null ) {
			firebaseUtil = new FirebaseUtil();
			mFireDatabase = mFireDatabase.getInstance();
			mDeals = new ArrayList<TravelDeal>();
		}
		mDatabaseReference = mFireDatabase.getReference().child(ref);
	}
}
