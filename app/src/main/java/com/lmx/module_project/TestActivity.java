package com.lmx.module_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

import androidx.databinding.ViewDataBinding;

import com.lmx.common.base.BaseActivity;
import com.lmx.common.base.NoViewModel;
import com.lmx.module_project.rich.RichEditText;
import com.lmx.module_project.rich.RangBean;
import com.lmx.module_project.rich.TopicBean;

import org.jetbrains.annotations.Nullable;

import java.util.List;


/**
 * Created by lmx on 2021/2/27
 * Describe:
 */
public class TestActivity extends BaseActivity<NoViewModel, ViewDataBinding> {

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, TestActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private RichEditText richEditText;

    @Override
    public int layoutId() {
        return R.layout.app_activity_test;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        richEditText = findViewById(R.id.richEditText);
        findViewById(R.id.tvContent).setOnClickListener(v -> {
            // 设置话题
            richEditText.setSingle(false);
            setTopic();
        });
        findViewById(R.id.tvContentSingle).setOnClickListener(v -> {
            // 设置话题
            richEditText.setSingle(true);
            setTopic();
        });
        TextView tvGetContent = findViewById(R.id.tvGetContent);
        tvGetContent.setOnClickListener(v -> {
            String content = richEditText.getText().toString().trim();
            List<RangBean> mRangeManager = richEditText.mRangeManager;
            if (mRangeManager.size() > 0) {
                String showText = mRangeManager.get(0).getShowText();
                String replaceAll = content.replace(showText, "");
                tvGetContent.setText(String.format("%s", replaceAll));
            }
        });

    }

    /**
     * 添加设置话题
     */
    private void setTopic() {
        int topicId = (int) (Math.random() * 100);
        String topic = String.format("双 NO.%s 狂欢", topicId);
        TopicBean topicBean = new TopicBean(topicId, topic);
        richEditText.insertText(richEditText, topicBean);
    }

    @Override
    public void initData() {
        richEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Editable editable = richEditText.getText();
                if (editable != null) {
                    if (start < editable.length()) {
                        int end = start + count;
                        int offset = after - count;
                        richEditText.whenDelText(start, end, offset);
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


}
