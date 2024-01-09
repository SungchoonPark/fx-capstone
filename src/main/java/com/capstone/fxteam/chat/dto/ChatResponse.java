package com.capstone.fxteam.chat.dto;

import com.capstone.fxteam.chat.model.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatResponse {
    private List<Choice> choices;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Choice {

        private int index;
        private Message message;
    }
}
