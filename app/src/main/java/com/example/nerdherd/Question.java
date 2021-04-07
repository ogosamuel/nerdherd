package com.example.nerdherd;

import java.util.ArrayList;

/**
 * Object for questions, each question holds a list of its replies
 */
public class Question {

    private String content;
    private ArrayList<Reply> replies = new ArrayList<Reply>();

    /**
     * Constructor for Question
     * @param content The question as a String
     */
    public Question(String content) {
        this.content = content;
    }

    public ArrayList<Reply> getReplies() {
        return replies;
    }

    /**
     * Adds a reply to the reply list
     * @param reply Reply to add
     */
    public void addReply(Reply reply) {
        this.replies.add(reply);
    }

    public String getContent() {
        return content;
    }
}
