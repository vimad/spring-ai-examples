package org.example.springaiexamples.service;

import org.example.springaiexamples.model.Answer;
import org.example.springaiexamples.model.Question;

public interface BoardGameService {
    Answer askQuestion(Question question);
}
