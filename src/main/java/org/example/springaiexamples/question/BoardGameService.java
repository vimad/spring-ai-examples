package org.example.springaiexamples.question;

import org.example.springaiexamples.question.model.Answer;
import org.example.springaiexamples.question.model.Question;
import reactor.core.publisher.Flux;

public interface BoardGameService {
    Answer askQuestion(Question question);
}
