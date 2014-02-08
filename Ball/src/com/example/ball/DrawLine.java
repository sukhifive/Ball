package com.example.ball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class DrawLine extends View{
	Paint paint = new Paint();

	public DrawLine(Context context) {
        super(context);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(5);
    }

    @Override
    public void onDraw(Canvas canvas) {
            canvas.drawLine(0, 0, 100, 20, paint);
            canvas.drawLine(100, 0, 0, 20, paint);
    }

}
