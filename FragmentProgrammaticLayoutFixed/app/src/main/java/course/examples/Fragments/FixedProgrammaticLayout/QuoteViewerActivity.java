package course.examples.Fragments.FixedProgrammaticLayout;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import course.examples.Fragments.FixedProgrammaticLayout.TitlesFragment.ListSelectionListener;

//Several Activity lifecycle methods are instrumented to emit LogCat output
//so you can follow this class' lifecycle
public class QuoteViewerActivity extends Activity implements ListSelectionListener {

	public static String[] mTitleArray;
	public static String[] mQuoteArray;

	// UB: Keep shown index in activity to save and restore state
	int mShownIndex = -1 ;
	static String OLD_ITEM = "old_item" ;

	// Get a reference to the QuotesFragment
	private final QuotesFragment mQuoteFragment = new QuotesFragment();
	private TitlesFragment mTitleFragment = null ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Get the string arrays with the titles and qutoes
		mTitleArray = getResources().getStringArray(R.array.Titles);
		mQuoteArray = getResources().getStringArray(R.array.Quotes);
		
		setContentView(R.layout.main);

		// Get a reference to the FragmentManager
		FragmentManager fragmentManager = getFragmentManager();
		
		// Begin a new FragmentTransaction
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		
		// Add the TitleFragment
		// UB - 9/28/2016: Changed add() with replace() operations to avoid
		// overlapping of fragments on same activity
		mTitleFragment = new TitlesFragment() ;
		fragmentTransaction.replace(R.id.title_frame, mTitleFragment) ;

		// Add the QuoteFragment
		fragmentTransaction.replace(R.id.quote_frame, mQuoteFragment);
		
		// Commit the FragmentTransaction
		fragmentTransaction.commit();

		// Retrieve old state if present
		if (savedInstanceState != null) {
			mShownIndex = savedInstanceState.getInt(OLD_ITEM) ;
		}
	}

	// UB: Activity about to be visible: Retrieve previous quote, if saved
	//     reset state of titles fragment
	public void onStart() {
		super.onStart() ;
		if (mShownIndex >= 0) {
			mQuoteFragment.showQuoteAtIndex(mShownIndex);
			mTitleFragment.setSelection(mShownIndex);
			mTitleFragment.getListView().setItemChecked(mShownIndex,true);
		}
	}
	// Called when the user selects an item in the TitlesFragment
	@Override
	public void onListSelection(int index) {
		// if (mQuoteFragment.getShownIndex() != index) {
		if (mShownIndex != index) {
			// Tell the QuoteFragment to show the quote string at position index
			mQuoteFragment.showQuoteAtIndex(index);
			// and update the shownIndex
			mShownIndex = index ;
		}
	}

	// UB: Save currently shown quote for later retrieval
	public void onSaveInstanceState(Bundle outState) {
		if (mShownIndex >= 0) {
			outState.putInt(OLD_ITEM, mShownIndex) ;
		}
	}
}