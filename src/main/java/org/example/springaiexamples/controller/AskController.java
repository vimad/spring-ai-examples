package org.example.springaiexamples.controller;

import jakarta.validation.Valid;
import org.example.springaiexamples.service.BoardGameService;
import org.example.springaiexamples.model.Answer;
import org.example.springaiexamples.model.Question;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class AskController {

    private final BoardGameService boardGameService;

    public AskController(BoardGameService boardGameService) {
        this.boardGameService = boardGameService;
    }

    @PostMapping(path="/ask", produces="application/json")
    public Answer ask(@RequestBody @Valid Question question) {
        return boardGameService.askQuestion(question);
    }

//    @PostMapping(path="/ask2", produces="application/ndjson")
    @PostMapping(path="/ask2", produces="text/event-stream")
    public Flux<String> ask2(@RequestBody @Valid Question question) {
        return boardGameService.askQuestion2(question);
    }

}