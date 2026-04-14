package org.example.springaiexamples.service;

import org.example.springaiexamples.model.Answer;
import org.example.springaiexamples.model.Question;
import reactor.core.publisher.Flux;

public interface BoardGameService {
    Answer askQuestion(Question question);
    Flux<String> askQuestion2(Question question);
}
