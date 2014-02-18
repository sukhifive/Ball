package com.example.ball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class DrawLine extends View implements OnTouchListener {
	Paint paint = new Paint();
	ImageView imageView;
	  Bitmap bitmap;
	  Canvas canvas;
	  float downx = 0, downy = 0, upx = 0, upy = 0;

	public DrawLine(Context c, AttributeSet a)
	{
		super(c, a);
		System.out.println("Attribute set");
	}
	  public DrawLine(Context context) {
        super(context);
        System.out.println("not Attribute set");
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(5);
    }

    @Override
    public void onDraw(Canvas canvas) {
    		//System.out.println("ssssssssssssssssssssss");
            canvas.drawLine(0, 0, 100, 200, paint);
            canvas.drawLine(100, 0, 0, 200, paint);
            canvas.drawLine(downx, downy, upx, upy, paint);
            System.out.printf("downx: " + downx, " downy: " + downy);
    }

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		int action = event.getAction();
	    switch (action) {
	    case MotionEvent.ACTION_DOWN:
	    //  System.out.println("down");
	      downx = event.getX();
	      downy = event.getY();
	      break;
	    case MotionEvent.ACTION_MOVE:
	      break;
	    case MotionEvent.ACTION_UP:
	      upx = event.getX();
	      upy = event.getY();
	      //canvas.drawLine(downx, downy, upx, upy, paint);
	      invalidate();
	      break;
	    case MotionEvent.ACTION_CANCEL:
	      break;
	    default:
	      break;
	    }
	    return true;
	}

}
