package com.boarbeard.widget;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.boarbeard.R;

public class SeekBarPreference extends DialogPreference implements OnSeekBarChangeListener {
	private static final String PREFERENCE_NS = "http://schemas.android.com/apk/res/com.boarbeard";
	private static final String ANDROID_NS = "http://schemas.android.com/apk/res/android";

	private static final String ATTR_DEFAULT_VALUE = "defaultValue";
	private static final String ATTR_MIN_VALUE = "minValue";
	private static final String ATTR_MAX_VALUE = "maxValue";
	
	private static final int DEFAULT_MIN_VALUE = 0;
	private static final int DEFAULT_MAX_VALUE = 100;
	private static final int DEFAULT_CURRENT_VALUE = 50;
	
	private int mMinValue;
	private int mMaxValue;
	private int mDefaultValue;
	private int mCurrentValue;
	
	private SeekBar mSeekBar;
	private TextView mValueText;
	
	public SeekBarPreference(Context context, AttributeSet attrs) {
		super(context, attrs);

		mMinValue = attrs.getAttributeIntValue
			(PREFERENCE_NS, ATTR_MIN_VALUE, DEFAULT_MIN_VALUE);
		mMaxValue = attrs.getAttributeIntValue
			(PREFERENCE_NS, ATTR_MAX_VALUE, DEFAULT_MAX_VALUE);
		mDefaultValue = attrs.getAttributeIntValue
			(ANDROID_NS, ATTR_DEFAULT_VALUE, DEFAULT_CURRENT_VALUE);
	}
	
	@Override
	protected View onCreateDialogView() {

	    // Get current value from settings
	    mCurrentValue = getPersistedInt(mDefaultValue);

	    // Inflate layout
	    LayoutInflater inflater = 
		(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View view = inflater.inflate(R.layout.dialog_slider, null);

	    // Put minimum and maximum
	    ((TextView) view.findViewById(R.id.min_value)).setText(Integer.toString(mMinValue));
	    ((TextView) view.findViewById(R.id.max_value)).setText(Integer.toString(mMaxValue));

	    // Setup SeekBar
	    mSeekBar = (SeekBar) view.findViewById(R.id.seek_bar);
	    mSeekBar.setMax(mMaxValue - mMinValue);
	    mSeekBar.setProgress(mCurrentValue - mMinValue);
	    mSeekBar.setOnSeekBarChangeListener(this);

	    // Put current value
	    mValueText = (TextView) view.findViewById(R.id.current_value);
	    mValueText.setText(Integer.toString(mCurrentValue));

	    return view;
	}
	
	@Override
	protected void onDialogClosed(boolean positiveResult) {
	    super.onDialogClosed(positiveResult);

	    if (!positiveResult) {
	        return;
	    }
	    if (shouldPersist()) {
	        persistInt(mCurrentValue);
	    }

	    notifyChanged();
	}
	
	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// not used		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// not used		
	}
	
	@Override
	public void onProgressChanged(SeekBar seek, int value, boolean fromTouch) {
	    mCurrentValue = value + mMinValue;
	    mValueText.setText(Integer.toString(mCurrentValue));
	}
	
	@Override
	public CharSequence getTitle() {
	    String title = super.getTitle().toString();
	    int value = getPersistedInt(mDefaultValue);
	    return String.format(title, value);
	}

}
