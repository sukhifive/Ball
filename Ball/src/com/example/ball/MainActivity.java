package com.example.ball;

import android.R.color;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

   
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
      //  FrameLayout layoutTest  = (FrameLayout) this.findViewById(R.id.touchView);
        
        
        
//        layoutTest.setLayoutParams(new FrameLayout.LayoutParams(
//        		LinearLayout.LayoutParams.MATCH_PARENT,
//        		LinearLayout.LayoutParams.MATCH_PARENT));
        
//        DrawLine drawLine = new DrawLine(this);
//        
//        drawLine.setBackgroundColor(color.transparent);
//        TextView t = new TextView(getApplicationContext());
//        t.setText("Hello world");
//        layoutTest.addView(t);
//        
//        layoutTest.addView(drawLine);
//        
//        AnimationView aniView= new AnimationView(this);
//        layoutTest.addView(aniView);
        
    //    setContentView(drawLine);
        
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
