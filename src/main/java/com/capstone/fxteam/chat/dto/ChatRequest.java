package com.capstone.fxteam.chat.dto;



import com.capstone.fxteam.chat.model.Message;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChatRequest {
    private String model;
    private List<Message> messages;
    private final int n = 1;
    private double temperature;

    public ChatRequest(String model, String prompt) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new Message("user", prompt));
    }
}
