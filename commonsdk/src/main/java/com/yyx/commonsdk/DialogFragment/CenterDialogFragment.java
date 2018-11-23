package com.yyx.commonsdk.DialogFragment;

import android.view.Gravity;

import com.yyx.commonsdk.R;


/**
 * 中间弹窗基础类
 */
public class CenterDialogFragment extends BottomDialogFragment {

	protected float getWidthRatio() {
		return 0.8f;
	}

	protected int getWindowGravity() {
		return Gravity.CENTER;
	}

	protected int getDialogTheme() {
		return R.style.BaseDialog;
	}
}
