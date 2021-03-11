package com.lmx.module_project.rich;

import android.view.KeyEvent;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;

import java.util.List;


/**
 * Created by lmx on 2020/2/27
 * Describe:
 */
public class MentionInputConnection extends InputConnectionWrapper {
    private final RichEditText mEditText;
    private final List<RangBean> mRangeManager;

    public MentionInputConnection(InputConnection target, boolean mutable, RichEditText editText) {
        super(target, mutable);
        this.mEditText = editText;
        this.mRangeManager = editText.mRangeManager;
    }

    @Override
    public boolean sendKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
            if (null != mRangeManager) {
                int selectionStart = mEditText.getSelectionStart();
                int selectionEnd = mEditText.getSelectionEnd();
                RangBean closestRangBean = mEditText.getRangeOfClosestMentionString(selectionStart, selectionEnd);
                if (closestRangBean == null) {
                    mEditText.setSelected(false);
                    return super.sendKeyEvent(event);
                }
                if (mEditText.isSelected() || selectionStart == closestRangBean.getFrom()) {
                    mEditText.setSelected(false);
                    return super.sendKeyEvent(event);
                } else {
                    // select the mention string
                    mEditText.setSelected(true);
                    // 如果光标所在位置是文字块，直接删除文字块
                    // 连续删除 出现replace (71 ... 77) ends beyond length 76，应该是某一次插入后，文字块的结束位置多偏移了一位导致的
                    if(closestRangBean.getTo()> mEditText.getText().length() && mEditText.getText().length()>0){
                        closestRangBean.setOffset(-1);
                    }
                    mEditText.getText().delete(closestRangBean.getFrom(), closestRangBean.getTo());
                }
                return true;
            }
        }
        return super.sendKeyEvent(event);
    }

    @Override
    public boolean deleteSurroundingText(int beforeLength, int afterLength) {
        if (beforeLength == 1 && afterLength == 0) {
            return sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL)) && sendKeyEvent(
                    new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL));
        }
        return super.deleteSurroundingText(beforeLength, afterLength);
    }
}
