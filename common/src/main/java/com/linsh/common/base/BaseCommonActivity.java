package com.linsh.common.base;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.linsh.base.mvp.BaseMvpActivity;
import com.linsh.base.mvp.Contract;
import com.linsh.common.activity.ActivityResultSubscriber;
import com.linsh.dialog.DialogComponents;
import com.linsh.dialog.IDialog;
import com.linsh.dialog.text.ITextDialog;
import com.linsh.utilseverywhere.HandlerUtils;
import com.linsh.utilseverywhere.ToastUtils;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/02/28
 *    desc   :
 * </pre>
 */
public class BaseCommonActivity<P extends Contract.Presenter> extends BaseMvpActivity<P> implements CommonContract.View {

    private static final String TAG = "BaseCommonActivity";
    private IDialog dialogHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscribe(ActivityResultSubscriber.class);
        addMvpCallAdapter(new MvpAnnotationEnhancer());
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showTextDialog(String content) {
        showTextDialog(null, content, null, null, null, null);
    }

    @Override
    public void showTextDialog(String title, String content) {
        showTextDialog(title, content, null, null, null, null);
    }

    @Override
    public void showTextDialog(String content, IDialog.OnClickListener onPositiveListener) {
        showTextDialog(null, content, "确认", onPositiveListener, null, null);
    }

    @Override
    public void showTextDialog(String content, String positiveBtn, IDialog.OnClickListener onPositiveListener) {
        showTextDialog(null, content, positiveBtn, onPositiveListener, null, null);
    }

    @Override
    public void showTextDialog(String content, IDialog.OnClickListener onPositiveListener, IDialog.OnClickListener onNegativeListener) {
        showTextDialog(null, content, "确认", onPositiveListener, "取消", onNegativeListener);
    }

    @Override
    public void showTextDialog(String content, String positiveBtn, IDialog.OnClickListener onPositiveListener, String negativeBtn, IDialog.OnClickListener onNegativeListener) {
        showTextDialog(null, content, positiveBtn, onPositiveListener, negativeBtn, onNegativeListener);
    }

    private void showTextDialog(String title, String content,
                                String positiveBtn, IDialog.OnClickListener onPositiveListener,
                                String negativeBtn, IDialog.OnClickListener onNegativeListener) {
        dialogHelper = DialogComponents.create(this, ITextDialog.class)
                .setText(content)
                .setTitle(title)
                .setPositiveButton(positiveBtn, onPositiveListener)
                .setNegativeButton(negativeBtn, onNegativeListener)
                .show();
    }

    @Override
    public void dismissTextDialog() {
        HandlerUtils.postRunnable(() -> {
            dialogHelper.dismiss();
        });
    }

    @Override
    public void showLoadingDialog() {
        showLoadingDialog("加载中...");
    }

    @Override
    public void showLoadingDialog(String content) {
        if (dialogHelper != null) {
            dialogHelper.dismiss();
        }
        dialogHelper = DialogComponents.create(this, ITextDialog.class)
                .setText(content)
                .show();
    }

    @Override
    public void dismissLoadingDialog() {
        if (dialogHelper != null) {
            dialogHelper.dismiss();
        }
    }

    @Override
    public void showToast(String content) {
        ToastUtils.showLong(content);
    }

    @Override
    public void finishActivity() {
        finish();
    }
}
