package com.tzhy.imageswitcher;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;


public class MainActivity extends Activity {

	private final String TAG = this.getClass().getSimpleName();

	protected int mImageIndex;
	protected int[] mImageIDs;
	protected ImageSwitcher mImageSwitcher;
	protected LinearLayout mImageIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// //
		mImageIndex = 0;

		mImageIDs = new int[] { R.drawable.car1, R.drawable.car2,
				R.drawable.car3, R.drawable.car4 };

		// //
		mImageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);

		mImageSwitcher.setFactory(new ViewFactory() {

			@Override
			public View makeView() {

				Log.d(TAG, "{makeView}");

				ImageView imageView = new ImageView(MainActivity.this);
				imageView.setLayoutParams(new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
				imageView.setScaleType(ScaleType.FIT_XY);

				return imageView;
			}
		});

		mImageSwitcher.setOnTouchListener(new OnTouchListener() {

			private float startX;
			private boolean bTouchStart;

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				Log.d(TAG, "{onTouch}" + event.getAction());

				switch (event.getAction()) {

				case MotionEvent.ACTION_DOWN: {

					startX = event.getX();

					bTouchStart = true;
				}
					break;

				case MotionEvent.ACTION_MOVE: {

					if (bTouchStart) {

						float stopX = event.getX();

						if (stopX < startX - 25) {

							showNext();

							bTouchStart = false;

						} else if (stopX > startX + 25) {

							showPre();

							bTouchStart = false;
						}
					}
				}
					break;

				case MotionEvent.ACTION_UP: {

					v.performClick();
				}
					break;

				}

				return true;
			}
		});

		// //
		mImageIndicator = (LinearLayout) findViewById(R.id.imageIndicator);

		for (int i = 0; i < mImageIDs.length; i++) {

			TextView textView = new TextView(this);

			textView.setLayoutParams(new LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT));

			if (i == 0) {

				textView.setBackgroundResource(R.drawable.indicator_s);

			} else {

				textView.setBackgroundResource(R.drawable.indicator_n);
			}

			mImageIndicator.addView(textView);
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {

		mImageSwitcher.setImageResource(mImageIDs[mImageIndex]);

		super.onPostCreate(savedInstanceState);
	}

	protected void showNext() {

		Log.d(TAG, "{showNext}");

		if (mImageIndex < mImageIDs.length - 1) {

			mImageIndicator.getChildAt(mImageIndex).setBackgroundResource(
					R.drawable.indicator_n);
			mImageIndicator.getChildAt(++mImageIndex).setBackgroundResource(
					R.drawable.indicator_s);

			mImageSwitcher.setInAnimation(this, R.anim.slide_in_left);
			mImageSwitcher.setOutAnimation(this, R.anim.slide_out_left);

			mImageSwitcher.setImageResource(mImageIDs[mImageIndex]);
		}
	}

	protected void showPre() {

		Log.d(TAG, "{showPre}");

		if (mImageIndex > 0) {

			mImageIndicator.getChildAt(mImageIndex).setBackgroundResource(
					R.drawable.indicator_n);
			mImageIndicator.getChildAt(--mImageIndex).setBackgroundResource(
					R.drawable.indicator_s);

			mImageSwitcher.setInAnimation(this, R.anim.slide_in_right);
			mImageSwitcher.setOutAnimation(this, R.anim.slide_out_right);
			mImageSwitcher.setImageResource(mImageIDs[mImageIndex]);
		}
	}
}
