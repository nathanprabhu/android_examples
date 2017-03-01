package edu.uic.cs478.BroadcastReceiver2;

// import edu.uic.cs478.BroadcastReceiver1.R;
import android.app.Activity;
import android.widget.Button ;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent ;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View ;

public class MainActivity extends Activity
{
	private Button mButton ;
	
	private BroadcastReceiver mReceiver ;
	private IntentFilter mFilter ;
	
	private static final String TOAST_ACTION = 
			"edu.uic.cs478.BroadcastReceiver2.showToast"; 
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mButton = (Button) findViewById(R.id.button1) ;
        
        mButton.setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {
        			Intent aIntent = new Intent(TOAST_ACTION) ;
        			sendBroadcast(aIntent, null) ;
        		}
        }) ;
        
        mReceiver = new MyReceiver() ;
        mFilter = new IntentFilter(TOAST_ACTION) ;
        
        registerReceiver(mReceiver, mFilter) ;
        
    }
}
