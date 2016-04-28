package com.fawns.app.bean;

/**
 * Project Fawns
 * Package com.fawns.app.bean
 * Author Mengjiaxin
 * Date 2016/4/22 18:18
 * Desc 请用一句话来描述作用
 */
public class MessageEntity {

    private String head;
    private String content;
    private String time;
    private boolean isLeft;

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isLeft() {
        return isLeft;
    }

    public void setIsLeft(boolean isLeft) {
        this.isLeft = isLeft;
    }
}
