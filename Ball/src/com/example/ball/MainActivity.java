package com.example.ball;

import android.R.color;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

   
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout layoutTest  = (LinearLayout) this.findViewById(R.id.mainLayout);
        
        
        
        
        layoutTest.setLayoutParams(new FrameLayout.LayoutParams(
        		LinearLayout.LayoutParams.MATCH_PARENT,
        		LinearLayout.LayoutParams.MATCH_PARENT));
        
        DrawLine drawLine = new DrawLine(this);
        
        drawLine.setBackgroundColor(color.transparent);
        TextView t = new TextView(getApplicationContext());
        t.setText("Hello world");
        layoutTest.addView(t);
    //    setContentView(drawLine);
        
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
