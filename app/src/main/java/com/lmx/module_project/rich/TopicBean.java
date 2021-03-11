package com.lmx.module_project.rich;

import android.graphics.Color;

import java.io.Serializable;

/**
 * Created by lmx on 2020/2/27
 * Describe: 话题
 */
public class TopicBean implements Serializable, InsertData {

    private int topicId;
    private String topic;

    public TopicBean(int topicId, String topic) {
        this.topicId = topicId;
        this.topic = topic;
    }


    @Override
    public String showText() {
        return "#" + topic + "#";
    }

    @Override
    public String uploadFormatText() {
        final String TOPIC_FORMANT = "{[%s, %s]}";
        return String.format(TOPIC_FORMANT, "#" + topic + "#", topicId);
    }

    @Override
    public int color() {
        return Color.BLUE;
    }


    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
