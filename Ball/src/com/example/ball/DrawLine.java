package com.example.ball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class DrawLine extends View implements OnTouchListener {
	Paint mPaint = new Paint();
	ImageView imageView;
	Bitmap bitmap;
	Canvas canvas;
	float downx = 0, downy = 0, upx = 0, upy = 0;
	private Path mPath;
	private boolean drawOn;
	
	private float startX;
	private float startY;
	private float endX;
	private float endY;

	public DrawLine(Context c, AttributeSet a) {
		super(c, a);
		System.out.println("Attribute set");
		drawOn = false;
		setLayerType(View.LAYER_TYPE_HARDWARE, null);
		mPath = new Path();
		
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setColor(Color.GREEN);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(25);
	}

	public DrawLine(Context context) {
		super(context);
		System.out.println("not Attribute set");
//		paint.setColor(Color.GREEN);
//		paint.setStrokeWidth(20);
		drawOn = false;
		setLayerType(View.LAYER_TYPE_HARDWARE, null);
		mPath = new Path();
		
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setColor(Color.GREEN);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(25);

	}

	@Override
	public void onDraw(Canvas canvas) {
		// System.out.println("ssssssssssssssssssssss");
		this.canvas = canvas;
		canvas.drawLine(0, 0, 100, 200, mPaint);
		canvas.drawLine(100, 0, 0, 200, mPaint);
		mPath.moveTo(200, 200);
		mPath.lineTo(300, 350);
		canvas.drawPath(mPath, mPaint);
		mPath.reset();
//		if(drawOn)
//		{
//			System.out.println("sratr : " + this.getStartX());
//			mPath.moveTo(this.getStartX(), this.getStartY());
//			mPath.lineTo(this.getEndX(), this.getEndY());
//			canvas.drawPath(mPath, mPaint);
//			mPath.reset();
//			//drawOn = false;
//			canvas.drawLine(100, 100, 100, 200, mPaint);
//			canvas.drawLine(100, 0, 0, 200, mPaint);
//			canvas.drawLine(downx, downy, upx, upy, mPaint);
//		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			// System.out.println("down");
			downx = event.getX();
			downy = event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			upx = event.getX();
			upy = event.getY();
			// canvas.drawLine(downx, downy, upx, upy, paint);
			invalidate();
			break;
		case MotionEvent.ACTION_CANCEL:
			break;
		default:
			break;
		}
		return true;
	}

	public void drawLine() {
		
	}

	public Path getmPath() {
		return mPath;
	}

	public void setmPath(Path mPath) {
		this.mPath = mPath;
	}

	public boolean isDrawOn() {
		return drawOn;
	}

	public void setDrawOn(boolean drawOn) {
		this.drawOn = drawOn;
	}

	public float getStartX() {
		return startX;
	}

	public void setStartX(float startX) {
		this.startX = startX;
	}

	public float getStartY() {
		return startY;
	}

	public void setStartY(float startY) {
		this.startY = startY;
	}

	public float getEndX() {
		return endX;
	}

	public void setEndX(float endX) {
		this.endX = endX;
	}

	public float getEndY() {
		return endY;
	}

	public void setEndY(float endY) {
		this.endY = endY;
	}

}
