package com.capstone.fxteam.metal.service.chat;

import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {
    private final ChatgptService chatgptService;

    public String chatResponse(String prompt) {
        List<String> features = new ArrayList<>(List.of(
                "강도", "연성", "내식성", "내열성", "생체적합성", "전기전도성", "열전도성", "자성", "비용절약성"
        ));

        String message = String.format(
                "I'm going to ask you a question in the future, " +
                        "but please answer only with the name of the characteristic which of the following 9 characteristics corresponds to. " +
                        "The nine characteristics are as follows. %s." +
                        "The answer must be one of the nine characteristics.",
                features
        );

        message = message + "\nQuestion is " + prompt;
        return chatgptService.sendMessage(message).trim();
    }
}
