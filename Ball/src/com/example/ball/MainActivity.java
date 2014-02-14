package com.example.ball;

import android.R.color;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout layout  = (FrameLayout) this.findViewById(R.layout.activity_main);
        
       // setContentView(R.layout.activity_main);
//        DrawLine drawLine = new DrawLine(this);
//        drawLine.setBackgroundColor(color.transparent);
//        layout.addView(drawLine);
    //    setContentView(drawLine);
        setContentView(R.layout.activity_main);
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
