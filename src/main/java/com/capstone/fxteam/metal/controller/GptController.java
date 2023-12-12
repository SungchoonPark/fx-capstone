package com.capstone.fxteam.metal.controller;

import com.capstone.fxteam.constant.dto.ApiResponse;
import com.capstone.fxteam.metal.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class GptController {
    private final ChatService chatService;

    @PostMapping("/chat")
    public String chatWithGpt(@RequestBody String question) {
        log.info("question : " + question);
        return chatService.chatResponse(question);
    }
}
