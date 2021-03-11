package com.lmx.module_project.rich;


import androidx.annotation.NonNull;

/**
 * Created by lmx on 2020/2/27
 * Describe:
 */
public class RangBean implements Comparable {
    private int from;
    private int to;
    private String uploadFormatText;
    private String showText;

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public String getUploadFormatText() {
        return uploadFormatText;
    }

    public String getShowText() {
        return showText;
    }

    public void setUploadFormatText(String uploadFormatText) {
        this.uploadFormatText = uploadFormatText;
    }

    public void setShowText(String showText) {
        this.showText = showText;
    }

    public RangBean(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public boolean isWrapped(int start, int end) {
        return from >= start && to <= end;
    }

    public boolean isWrappedBy(int start, int end) {
        return (start > from && start < to) || (end > from && end < to);
    }

    public boolean contains(int start, int end) {
        return from <= start && to >= end;
    }

    /**
     * 修复在两个文字块中间 插入文字块后删除会闪退的问题
     *
     * @param start
     * @param end
     * @return
     */
    public boolean contains2(int start, int end) {
        return from < start && to >= end;
    }

    /**
     * 删除的时候，检测光标位置是否在文字的最后位置
     *
     * @param end
     * @return
     */
    public boolean isEndEquualTo(int end) {
        return to == end;
    }

    private boolean isEqual(int start, int end) {
        return (from == start && to == end) || (from == end && to == start);
    }

    public int getAnchorPosition(int value) {
        if ((value - from) - (to - value) >= 0) {
            return to;
        } else {
            return from;
        }
    }

    public void setOffset(int offset) {
        from += offset;
        to += offset;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return from - ((RangBean) o).from;
    }

}
