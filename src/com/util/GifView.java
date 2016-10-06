package com.util;

import java.io.InputStream;

import com.fxgizmob.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;

public class GifView extends View {

	private InputStream gifInputStream;
	private Movie gifMovie;
	private int movieWidth, movieHeight;
	private long movieDuration;
	private long movieStart;
	
	public GifView(Context context) {
		super(context);
		init(context);
	}
	public GifView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);//, attrs);
	}
	public GifView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);//, attrs, defStyleAttr);
	}
	
	private void init (Context context){
		setFocusable(true);
		gifInputStream = context.getResources().openRawResource(R.drawable.app_splash);
		
		gifMovie = Movie.decodeStream(gifInputStream);
		movieWidth = gifMovie.width();
		movieHeight = gifMovie.height();
		movieDuration = gifMovie.duration();
	}
	private void init (Context context, AttributeSet attrs){
		
	}
	private void init (Context context, AttributeSet attrs, int defStyleAttr){
	
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		setMeasuredDimension(movieWidth, movieHeight);
	}
	public int getMovieWidth(){
		return movieWidth;
	}
	public int getMovieHeight(){
		return movieHeight;
	}
	public long getMovieDuration(){
		return movieDuration;
	}
	@Override
	protected void onDraw(Canvas canvas){
		long now = SystemClock.uptimeMillis();
		if (movieStart == 0) {
			movieStart = now;
		}
		if (gifMovie != null) {
			int dur = gifMovie.duration();
			if (dur == 0) {
				dur = 1000;
			}
			
			int relTime = (int) ((now - movieStart) % dur);
			gifMovie.setTime(relTime);
			gifMovie.draw(canvas, 0, 0);
			invalidate();
		}
	}
	

}
