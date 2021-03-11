package com.lmx.module_project.rich;

import android.content.Context;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import androidx.appcompat.widget.AppCompatEditText;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by lmx on 2020/2/27
 * Describe:话题输入控件
 */
public class RichEditText extends AppCompatEditText {

    public List<RangBean> mRangeManager;
    private boolean mIsSelected;
    private boolean single = true;

    public RichEditText(Context context) {
        super(context);
        init();
    }

    public RichEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RichEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mRangeManager = new ArrayList<>();
    }


    // 如果是删除操作MentionInputConnection中的sendKeyEvent优先触发
    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        // 修复连续删除文字块会出现数组越界问题
        RangBean closestRange = getRangeOfClosestMentionString(selStart, selEnd);
        if (closestRange != null && closestRange.getTo() == selEnd) {
            mIsSelected = false;
        }
        RangBean nearbyRangBean = getRangeOfNearbyMentionString(selStart, selEnd);
        if (null != nearbyRangBean) {
            // 起始位置一样说明，没有选中任何字符
            if (selStart == selEnd) {
                // 如果光标是在文字块中 则设置光标在文字块两侧 --保证光标无法插入文字块
                int position = nearbyRangBean.getAnchorPosition(selStart);
                // 修复输入大量文字块标点符号特殊字符后，再连续删除所有 会出现数组越界的问题
                Editable editable = getText();
                if (editable != null) {
                    if (position <= editable.length())
                        setSelection(position);
                }
            } else {
                if (selEnd < nearbyRangBean.getTo()) {
                    setSelection(selStart, nearbyRangBean.getTo());
                }
                if (selStart > nearbyRangBean.getFrom()) {
                    setSelection(nearbyRangBean.getFrom(), selEnd);
                }
            }
        }

    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return new MentionInputConnection(super.onCreateInputConnection(outAttrs), true, this);
    }

    @Override
    public boolean isSelected() {
        return mIsSelected;
    }

    public RangBean getRangeOfNearbyMentionString(int selStart, int selEnd) {
        if (mRangeManager == null) {
            return null;
        }
        for (RangBean rangBean : mRangeManager) {
            if (rangBean.isWrappedBy(selStart, selEnd)) {
                return rangBean;
            }
        }
        return null;
    }

    public RangBean getRangeOfClosestMentionString(int selStart, int selEnd) {
        if (mRangeManager == null) {
            return null;
        }
        for (RangBean rangBean : mRangeManager) {
            if (rangBean.contains2(selStart, selEnd)) {
                return rangBean;
            }
        }
        return null;
    }

    /**
     * 每删除一个字符，都要遍历缓存队列，判断是否是删除了队列中的数据
     * 如果删除是文字块前面的文字，对于后面的文字块要往前移位
     * 如果是删除文字块，则把文字块在缓存列表删除后，对于后面的文字块要往前移位
     *
     * @param start
     * @param end
     * @param offset
     */
    public void whenDelText(int start, int end, int offset) {
        Iterator iterator = mRangeManager.iterator();
        while (iterator.hasNext()) {
            RangBean rangBean = (RangBean) iterator.next();
            // 判断起始位置是否包裹了文字块，如果包裹了，则把文字块相关信息在内存列表删除
            if (rangBean.isWrapped(start, end)) {
                iterator.remove();
                continue;
            }
            // 将end之后的span，挪动offset个位置
            if (rangBean.getFrom() >= end) {
                rangBean.setOffset(offset);
            }
        }
    }

    /**
     * 把选中用户或话题 插入输入框
     *
     * @param insertData
     * @param insertData
     */
    public void insertText(RichEditText richEditText, InsertData insertData) {
        if (insertData == null) return;
        try {
            String showText = insertData.showText();
            String uploadFormatText = insertData.uploadFormatText();
            int color = insertData.color();
            Editable editable = richEditText.getText();
            List<RangBean> mRangeManager = richEditText.mRangeManager;
            int start = isSingle() ? 0 : getSelectionStart();
            int end = start + showText.length();
            if (mRangeManager.size() > 0 && isSingle()) {
                String showTextEd = mRangeManager.get(0).getShowText();
                mRangeManager.clear();
                if (editable != null) {
                    String replaceAll = editable.toString().replace(showTextEd, showText);
                    SpannableStringBuilder style = new SpannableStringBuilder(replaceAll);
                    style.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    setText(style);
                    richEditText.setSelection(replaceAll.length());
                }
            } else {
                if (editable != null) {
                    // 插入到指定位置
                    editable.insert(start, showText);
                    // 设置对应颜色
                    editable.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }

            // 保存文字块的起始位置
            RangBean range = new RangBean(start, end);
            range.setShowText(showText);
            range.setUploadFormatText(uploadFormatText);
            mRangeManager.add(range);
        } catch (Exception ignored) {

        }
    }

    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }
}
