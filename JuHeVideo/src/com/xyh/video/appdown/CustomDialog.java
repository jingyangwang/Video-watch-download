package com.xyh.video.appdown;

import com.videoplayer.bd.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;


public class CustomDialog extends Dialog {

	public CustomDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public CustomDialog(Context context, int theme) {
		super(context, theme);
	}

	public CustomDialog(Context context) {
		this(context,R.style.CustomDialog);
		this.setContentView(R.layout.dialog_toast);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
}
