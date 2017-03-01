package edu.uic.cs478.BroadcastReceiver3Companion;

// import edu.uic.cs478.BroadcastReceiver1.R;
// import edu.uic.cs478.BroadcastReceiver3.R;
import android.app.Activity;
import android.widget.Button ;
import android.content.BroadcastReceiver;
import android.content.Intent ;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View ;
import android.support.v7.app.AppCompatActivity;

public class CompanionActivity extends AppCompatActivity
{
	// private Button mButton ;
	private static final String TOAST_INTENT = 
			"edu.uic.cs478.BroadcastReceiver3.showToast";
	
	BroadcastReceiver mReceiver = new ForeignReceiver() ;
	IntentFilter mFilter = new IntentFilter(TOAST_INTENT) ;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mFilter.setPriority(100);
        
        registerReceiver(mReceiver,mFilter) ;
        
        
        
        // mButton = (Button) findViewById(R.id.button1) ;
        
        // mButton.setOnClickListener(new View.OnClickListener() {
        	// 	public void onClick(View v) {
        	//		Intent aIntent = new Intent(TOAST_INTENT) ;
        	//		sendOrderedBroadcast(aIntent, null) ;
        	//	}
        // }) ;

        
    }
}
