package com.example.ball;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class AnimationView extends ImageView {

	private Context mContext;
	int x = -1;
	int y = -1;
	private int xVelocity = 8;
	private int yVelocity = 4;
	private Handler h;
	private final int FRAME_RATE = 2;
	BitmapDrawable ball;
	private float downx;
	private float downy;
	private float upx;
	private float upy;
	private Paint paint = new Paint();

	public int width;
	public int height;
	private Bitmap mBitmap;
	private Canvas mCanvas;
	private Path mPath;
	private Paint mBitmapPaint;
	Context context;
	private Paint circlePaint;
	private Path circlePath;
	private Paint mPaint;
	private List<Line> lines;
	
	private float mX, mY;
	private static final float TOUCH_TOLERANCE = 4;
	private float sX, sY;



	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);

	}

	public AnimationView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		h = new Handler();
		ball = (BitmapDrawable) mContext.getResources().getDrawable(
				R.drawable.ball3);
		this.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(5);

		mPath = new Path();
		mBitmapPaint = new Paint(Paint.DITHER_FLAG);
		circlePaint = new Paint();
		circlePath = new Path();
		circlePaint.setAntiAlias(true);
		circlePaint.setColor(Color.BLUE);
		circlePaint.setStyle(Paint.Style.STROKE);
		circlePaint.setStrokeJoin(Paint.Join.MITER);
		circlePaint.setStrokeWidth(4f);

		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setColor(Color.GREEN);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(25);
		
		lines = new ArrayList<Line>();
		
	}

	private Runnable r = new Runnable() {
		@Override
		public void run() {
			invalidate();
		}
	};

	protected void onDraw(Canvas c) {
		
		if (x < 0 && y < 0) {
			x = this.getWidth() / 2;
			y = this.getHeight() / 2;
		} else {
			x += xVelocity;
			y += yVelocity;
			//Boundary logic
			if ((x > this.getWidth() - ball.getBitmap().getWidth()) || (x < 0)) {
				xVelocity = xVelocity * -1;
			}
			if ((y > this.getHeight() - ball.getBitmap().getHeight())
					|| (y < 0)) {
				yVelocity = yVelocity * -1;
			}
		}
		this.collision();
		c.drawBitmap(ball.getBitmap(), x, y, null);
	//	System.out.println("width: " + ball.getBounds().contains(r));
		

		c.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
		
		//c.drawPath(mPath, mPaint);

		 h.postDelayed(r, FRAME_RATE);
		
		//this.invalidate();
	}

	

	private void touch_start(float x, float y) {
		mPath.reset();
		mPath.moveTo(x, y);
		mX = x;
		mY = y;
		sX = x;
		sY = y;
	}

	private void touch_move(float x, float y) {
//		float dx = Math.abs(x - mX);
//		float dy = Math.abs(y - mY);
//		code for tolerance
//		if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
//			mPath.moveTo(mX, mY);
////			mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
//			mX = x;
//			mY = y;
//
//			circlePath.reset();
//			circlePath.addCircle(mX, mY, 30, Path.Direction.CW);
//			mCanvas.drawPath(mPath, mPaint);
//			// kill this so we don't double draw
//			mPath.reset();
//		}
		mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
		mX = x;
		mY = y;
		mPath.moveTo(sX, sY);
		mPath.lineTo(mX, mY);
		
	//	circlePath.reset();
		// commit the path to our offscreen
		mCanvas.drawPath(mPath, mPaint);		
		// kill this so we don't double draw
		mPath.reset();
		mPath.moveTo(sX, sY);
		//
		//drawLines();
	}

	private void touch_up() {
		mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
		Line line = new Line();
		
		line.setStartPointX(sX);
		line.setStartPointY(sY);
		line.setEndPointX(mX);
		line.setEndPointY(mY);
		
		lines.add(line);
//		mPath.lineTo(mX, mY);
//		circlePath.reset();
//		// commit the path to our offscreen
//		mCanvas.drawPath(mPath, mPaint);
//		// kill this so we don't double draw
//		mPath.reset();
		
		drawLines();
	}
	
	private void drawLines()
	{
		View test = (View)this.getParent();
		DrawLine layoutTest  = (DrawLine) test.findViewById(R.id.draw_line);
	
		layoutTest.setStartX(sX);
		layoutTest.setStartY(sY);
		layoutTest.setEndX(mX);
		layoutTest.setEndY(mY);
		layoutTest.drawLN();	
		layoutTest.invalidate();
		System.out.println("Count: " + lines.size());
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		float x = event.getX();
		float y = event.getY();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			// System.out.println("down");
			downx = event.getX();
			downy = event.getY();
			touch_start(x, y);
			break;
		case MotionEvent.ACTION_MOVE:
			upx = event.getX();
			upy = event.getY();
			touch_move(x, y);
			// canvas.drawLine(downx, downy, upx, upy, paint);
			// invalidate();
			break;
		case MotionEvent.ACTION_UP:
			upx = event.getX();
			upy = event.getY();
			touch_up();
			break;
		case MotionEvent.ACTION_CANCEL:
			break;
		default:
			break;
		}
		return true;
	}
	
	private void collision()
	{
		for(Line l: lines)
		{
			if(l.getStartPointX() <= x && x <= l.getEndPointX()){
				System.out.println("xxxxxxx");
				System.out.println(" width: " + ball.getIntrinsicWidth());
			}
		}
	}

}
