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
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class AnimationView extends ImageView {

	private Context mContext;
	int x = -1;
	int y = -1;
	private int xVelocity = 10;
	private int yVelocity = 8;
	private Handler h;
	private final int FRAME_RATE = 5;
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
	private List<Rect> storedRect;

	private float mX, mY;
	private static final float TOUCH_TOLERANCE = 4;
	private float sX, sY;

	private static final String ABOVE = "ABOVE";
	private static final String BOTTOM = "BOTTOM";
	private static final String LEFT = "LEFT";
	private static final String RIGHT = "RIGHT";
	
	
	private boolean lineStarted = false;

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
		storedRect = new ArrayList<Rect>();
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

			this.collision();

		}

		c.drawBitmap(ball.getBitmap(), x, y, null);
		// System.out.println("x: " + x + " y: " + y);

		c.drawBitmap(mBitmap, 0, 0, mBitmapPaint);

		// c.drawPath(mPath, mPaint);

		h.postDelayed(r, FRAME_RATE);

		// this.invalidate();
	}

	private void touch_start(float x, float y) {
		
		if(isStartedCloseToBoundry(x, y))
		{
			System.out.println("close to it");
			lineStarted = true;
			mPath.reset();
			mPath.moveTo(x, y);
			mX = x;
			mY = y;
			sX = x;
			sY = y;
		}
		
		
	}

	private void touch_move(float x, float y) {
		// float dx = Math.abs(x - mX);
		// float dy = Math.abs(y - mY);
		// code for tolerance
		// if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
		// mPath.moveTo(mX, mY);
		// // mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
		// mX = x;
		// mY = y;
		//
		// circlePath.reset();
		// circlePath.addCircle(mX, mY, 30, Path.Direction.CW);
		// mCanvas.drawPath(mPath, mPaint);
		// // kill this so we don't double draw
		// mPath.reset();
		// }
		if(lineStarted) {
			mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
			mX = x;
			mY = y;
			mPath.moveTo(sX, sY);
			mPath.lineTo(mX, mY);
	
			// circlePath.reset();
			// commit the path to our offscreen
			mCanvas.drawPath(mPath, mPaint);
			// kill this so we don't double draw
			mPath.reset();
			mPath.moveTo(sX, sY);
			//
			// drawLines();
		}
	}

	private void touch_up() {
		if(lineStarted){
				
			if(isStartedCloseToBoundry(mX, mY)){
				
				System.out.println("colllll");
				mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
				Line line = new Line();
		
				line.setStartPointX(sX);
				line.setStartPointY(sY);
				line.setEndPointX(mX);
				if (mY < 0) {
					line.setEndPointY(0);
				} else {
					line.setEndPointY(mY);
				}
		
				lines.add(line);
				this.addRect(line);
				// mPath.lineTo(mX, mY);
				// circlePath.reset();
				// // commit the path to our offscreen
				// mCanvas.drawPath(mPath, mPaint);
				// // kill this so we don't double draw
				// mPath.reset();
		
				drawLines();
				lineStarted = false;
			}
		}
	}

	private void drawLines() {
		View test = (View) this.getParent();
		DrawLine layoutTest = (DrawLine) test.findViewById(R.id.draw_line);

		layoutTest.setStartX(sX);
		layoutTest.setStartY(sY);
		layoutTest.setEndX(mX);
		layoutTest.setEndY(mY);
		layoutTest.drawLN(x, y);
//		this.drawRect(this.determineBallLoc(), this.lines.get(lines.size() - 1));
//		layoutTest.drawRectBelowForRightToLeftLine();
		layoutTest.invalidate();

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
			System.out.println("Cancel");
			break;
		default:
			break;
		}
		return true;
	}

	private void drawRect(String ballLoc, Line l) {
		if (Math.abs(l.getStartPointX() - l.getEndPointX()) > 13) {

			if (this.ABOVE.equalsIgnoreCase(ballLoc)) {
				if (l.getStartPointX() > l.getEndPointX()) {
					// line right to left

				}
			}

		}
	}

	private String determineBallLoc() {
		String returnVal = "";
		if (Math.abs(lines.get(lines.size() - 1).getStartPointX()
				- lines.get(lines.size() - 1).getEndPointX()) > 13) {
			if (lines.get(lines.size() - 1).getStartPointY() > y) {
				returnVal = this.ABOVE;
			} else {
				returnVal = this.BOTTOM;
			}
		} else {
			if (lines.get(lines.size() - 1).getStartPointX() < x) {
				returnVal = this.LEFT;
			} else {
				returnVal = this.RIGHT;
			}
		}
		return returnVal;
	}

	private void collision() {
		x += xVelocity;
		y += yVelocity;
		// Boundary logic
		if ((x > this.getWidth() - ball.getBitmap().getWidth()) || (x < 0)) {
			xVelocity = xVelocity * -1;
		}
		if ((y > this.getHeight() - ball.getBitmap().getHeight()) || (y < 0)) {
			yVelocity = yVelocity * -1;
		}
		
		
//		for (Line l : lines) {
//			Rect rect = new Rect();
//			
//			if (Math.abs(l.getStartPointX() - l.getEndPointX()) > 13) {
//				// horizontal line
//				if (l.getStartPointX() > l.getEndPointX()) {
//					// line right to left
//					rect.set((int) l.getEndPointX(),
//							(int) (l.getEndPointY() - 13),
//							(int) l.getStartPointX(),
//							(int) (l.getStartPointY() + 13));
//				} else {
//					// line left to right
//					rect.set((int) l.getStartPointX(),
//							(int) (l.getStartPointY() - 13),
//							(int) l.getEndPointX(),
//							(int) (l.getEndPointY() + 13));
//				}
//				// System.out.println(" H");
//
//			} else {// virtical line
//
//				// System.out.println(" V");
//				if (l.getEndPointY() < l.getStartPointY()) {
//					// line bottom to up
//					rect.set((int) l.getEndPointX() - 13,
//							(int) l.getEndPointY(),
//							(int) l.getStartPointX() + 13,
//							(int) l.getStartPointY());
//				} else {
//					// line up to down
//					rect.set((int) l.getStartPointX() - 13,
//							(int) l.getStartPointY(),
//							(int) l.getEndPointX() + 13, (int) l.getEndPointY());
//				}
//
//			}
//			// collision logic
//			if (rect.contains(x, y)
//					|| rect.contains(x + ball.getBitmap().getWidth(), y)
//					|| rect.contains(x, y + ball.getBitmap().getHeight())
//					|| rect.contains(x + ball.getBitmap().getWidth(), y
//							+ ball.getBitmap().getHeight())) {
//
//				if (Math.abs(l.getStartPointX() - l.getEndPointX()) > 13) {
//					yVelocity = yVelocity * -1;
//				} else {
//					xVelocity = xVelocity * -1;
//				}
//
//			}
//
//		
//		}
		
		for(Rect rect: storedRect)
		{
			if (rect.contains(x, y)
					|| rect.contains(x + ball.getBitmap().getWidth(), y)
					|| rect.contains(x, y + ball.getBitmap().getHeight())
					|| rect.contains(x + ball.getBitmap().getWidth(), y
							+ ball.getBitmap().getHeight())) {
				System.out.println("h: " + rect.height());
				if(Math.abs(rect.height() ) < 50){
					yVelocity = yVelocity * -1;
					System.out.println("hit 1");
				}
				else{
					xVelocity = xVelocity * -1;
					System.out.println("hit 2");
				}
			}
		}
	}
	
	private boolean isStartedCloseToBoundry(float x, float y)
	{
		if(x <= 13 || x >= (mCanvas.getWidth() - 13)){
			return true;
		}else
		{
			if( y <= 13 || y >= (mCanvas.getHeight() - 13)){
				return true;
			}
		}
		return false;
	}
	
	private void addRect(Line l)
	{
		Rect rect = new Rect();
		
		if (Math.abs(l.getStartPointX() - l.getEndPointX()) > 13) {
			// horizontal line
			if (l.getStartPointX() > l.getEndPointX()) {
				// line right to left
				rect.set((int) l.getEndPointX(),
						(int) (l.getEndPointY() - 13),
						(int) l.getStartPointX(),
						(int) (l.getStartPointY() + 13));
			} else {
				// line left to right
				rect.set((int) l.getStartPointX(),
						(int) (l.getStartPointY() - 13),
						(int) l.getEndPointX(),
						(int) (l.getEndPointY() + 13));
			}
			// System.out.println(" H");

		} else {// virtical line

			// System.out.println(" V");
			if (l.getEndPointY() < l.getStartPointY()) {
				// line bottom to up
				rect.set((int) l.getEndPointX() - 13,
						(int) l.getEndPointY(),
						(int) l.getStartPointX() + 13,
						(int) l.getStartPointY());
			} else {
				// line up to down
				rect.set((int) l.getStartPointX() - 13,
						(int) l.getStartPointY(),
						(int) l.getEndPointX() + 13, (int) l.getEndPointY());
			}

		}
		storedRect.add(rect);
	}

}
