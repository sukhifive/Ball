package com.example.ball;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class DrawLine extends View {
	Paint mPaint = new Paint();
	Paint rPaint = new Paint();
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

	private Bitmap mBitmap;
	private Canvas mCanvas;
	Paint test = new Paint();

	public DrawLine(Context c, AttributeSet a) {
		super(c, a);
		System.out.println("Attribute set");
		drawOn = false;
		setLayerType(View.LAYER_TYPE_HARDWARE, null);
		mPath = new Path();

		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setColor(Color.GREEN);
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.SQUARE);
		mPaint.setStrokeWidth(26);
		
		

//		rPaint.setAntiAlias(true);
//		rPaint.setDither(true);
		rPaint.setColor(Color.RED);
		rPaint.setStyle(Paint.Style.FILL);
//		rPaint.setStrokeJoin(Paint.Join.ROUND);
		rPaint.setStrokeCap(Paint.Cap.SQUARE);


	}

	public DrawLine(Context context) {
		super(context);
//		System.out.println("not Attribute set");
//		// paint.setColor(Color.GREEN);
//		// paint.setStrokeWidth(20);
//		drawOn = false;
//		setLayerType(View.LAYER_TYPE_HARDWARE, null);
//		mPath = new Path();
//
//		mPaint.setAntiAlias(true);
//		mPaint.setDither(true);
//		mPaint.setColor(Color.GREEN);
//		mPaint.setStyle(Paint.Style.STROKE);
//		mPaint.setStrokeJoin(Paint.Join.ROUND);
//		mPaint.setStrokeCap(Paint.Cap.SQUARE);
//		mPaint.setStrokeWidth(26);
//		
//		rPaint.setAntiAlias(true);
//		rPaint.setDither(true);
//		rPaint.setColor(Color.GREEN);
//		rPaint.setStyle(Paint.Style.FILL);
//		rPaint.setStrokeJoin(Paint.Join.ROUND);
//		rPaint.setStrokeCap(Paint.Cap.SQUARE);
//		rPaint.setStrokeWidth(0);
//
//		test.setStrokeWidth(1);
//		test.setColor(Color.RED);
//		test.setStyle(Paint.Style.STROKE);

	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);

	}

	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawBitmap(mBitmap, 0, 0, mPaint);	
	}

	public Rect drawLN(int ballX, int ballY) {
		Rect box = drawBox(ballX, ballY);
		mPath.moveTo(this.getStartX(), this.getStartY());
		mPath.lineTo(this.getEndX(), this.getEndY());
//		mCanvas.drawPath(mPath, mPaint);	
		mCanvas.drawLine(this.getStartX(), this.getStartY(), this.getEndX(), this.getEndY(), mPaint);
		this.invalidate();
		return box;
	
	}
	
	
	public Rect drawBox(int ballX, int ballY)
	{
		Rect rect = new Rect();
		if (Math.abs(this.getStartX() - this.getEndX()) > 13) {
			//Horizontal line
			if (this.getStartX() > this.getEndX()) {
				// line right to left
				
				//find ball location above or bottom
				if(ballY < (int) this.getEndY()){
					//ball is above draw box below the line
					rect.set((int) this.getEndX(), (int) this.getEndY(),
							mCanvas.getWidth(), mCanvas.getHeight());
					
					
				}
				else
				{
					//ball is below so draw the box above
					rect.set(0, 0, (int)this.getStartX(),(int) this.getStartY());
				}
				mCanvas.drawRect(rect, rPaint);
			}
			else{
				//line left to right
				
				
				
				if(ballY < (int) this.getEndY()){
					//ball is above the the line
					rect.set((int) this.getStartX(), (int) this.getStartY(),
							mCanvas.getWidth(), mCanvas.getHeight());
					System.out.println("h: " + mCanvas.getHeight());
				}
				else
				{
					//ball is below so draw the box above
					rect.set(0, 0, (int)this.getEndX(),(int) this.getEndY());
				}
				
				mCanvas.drawRect(rect, rPaint);
			}
			
		}else {// virtical line

			// System.out.println(" V");
			if (this.getEndY() < this.getStartY()) {
				// line bottom to up
				
				
				if(ballX < this.getStartX())
				{
					//ball is on left
					rect.set((int) this.getEndX(), (int) this.getEndY(),
							mCanvas.getWidth(), mCanvas.getHeight());
				}
				else 
				{
					//ball is on right
					rect.set(0, 0,
							(int)this.getStartX(), (int)this.getStartY());
				}
				
				mCanvas.drawRect(rect, rPaint);
			}
			else{
				//line top to down
				if(ballX < this.getStartX())
				{
					//ball is on left
					rect.set((int) this.getStartX(), (int) this.getStartY(),
							mCanvas.getWidth(), mCanvas.getHeight());
				}
				else
				{
					//ball is on right
					rect.set(0, 0,
							(int)this.getEndX(), (int) this.getEndY());
				}
				mCanvas.drawRect(rect, rPaint);
			}
		}
		return rect;
	}

	public void drawRectBelowForRightToLeftLine() {
		Rect rect = new Rect();
		rect.set((int) this.getEndX(), (int) this.getEndY(),
				mCanvas.getWidth(), mCanvas.getHeight());
		mCanvas.drawRect(rect, rPaint);
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
