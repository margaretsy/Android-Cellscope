package edu.berkeley.cellscope.cscore.celltracker;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import edu.berkeley.cellscope.cscore.CameraActivity;

public class TouchPanner implements View.OnTouchListener {
	PannableStage stage;
	double maxZoom, screenDiagonal;
	double pinchDist, touchX, touchY;
	double zZone;
	int lastZoom;
	int panState;
	
	private static final int firstTouchEvent = -1;
	public TouchPanner(PannableStage p, Activity activity) {
		stage = p;
		maxZoom = firstTouchEvent;
		screenDiagonal = stage.getDiagonal();
		zZone = CameraActivity.getScreenHeight(activity) * PannableStage.Z_CONTROL_ZONE;
	}
	

	public boolean onTouch(View v, MotionEvent event) {
		int pointers = event.getPointerCount();
		int action = event.getActionMasked();
		int newState = PannableStage.stopMotor;
		
		if (maxZoom == firstTouchEvent)
			maxZoom = stage.getMaxZoom();
		//Pinch zoom
		if (pointers == 2){
			double newDist = Math.hypot(event.getX(0) - event.getX(1), event.getY(0) - event.getY(1));
			if (action == MotionEvent.ACTION_MOVE) {
				if (pinchDist != firstTouchEvent) { //Prevents jumping
					int newZoom = (int)((newDist-pinchDist) / screenDiagonal * maxZoom * 2);
					stage.zoom(newZoom - lastZoom);
					lastZoom = newZoom;
				}
				else {
					pinchDist = newDist;
					lastZoom = 0;
				}
			}
			else {
				pinchDist = firstTouchEvent;
			}
		}
		
		else if (stage.panAvailable() && pointers == 1) {
			if (action == MotionEvent.ACTION_DOWN) {
				touchX = event.getX();
				touchY = event.getY();
			}
			else if (action == MotionEvent.ACTION_MOVE) {
				double x = event.getX() - touchX;
				double y = event.getY() - touchY;
				double absX = Math.abs(x);
				double absY = Math.abs(y);
				if (absX >= absY && absX >= PannableStage.PAN_THRESHOLD) {
					newState = x > 0 ? PannableStage.xRightMotor : PannableStage.xLeftMotor;
				}
				else if (absY > absX && absY > PannableStage.PAN_THRESHOLD) {
					if (touchX < zZone)
						newState = y > 0 ? PannableStage.zUpMotor : PannableStage.zDownMotor;
					else
						newState = y > 0 ? PannableStage.yForwardMotor : PannableStage.yBackMotor;
				}
			}
			else if (action == MotionEvent.ACTION_UP) {
				newState = PannableStage.stopMotor;
				touchX = touchY = firstTouchEvent;
			}
		}
		
		if (newState != panState) {
			panState = newState;
			stage.panStage(newState);
		}
		
		return true;
	}
	
	public int getState() {
		return panState;
	}

}
