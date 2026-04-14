package org.example.springaiexamples.service;

import org.example.springaiexamples.model.Answer;
import org.example.springaiexamples.model.Question;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class SpringAiBoardGameService implements BoardGameService {

    private final ChatClient chatClient;
    private final GameRulesService gameRulesService;

    public SpringAiBoardGameService(ChatClient.Builder chatClientBuilder, GameRulesService gameRulesService) {
        this.chatClient = chatClientBuilder.build();
        this.gameRulesService = gameRulesService;
    }

    @Value("classpath:/promptTemplates/systemPromptTemplate.st")
    Resource promptTemplate;

    @Override
    public Answer askQuestion(Question question) {

        var gameRules = gameRulesService.getRulesFor(question.gameTitle());

        // LLMs may ignore formatting instructions (non-GPT models especially).
        // This can cause non-JSON responses and lead to JsonParseException during binding.
        return chatClient.prompt()
                .system(systemSpec -> systemSpec
                        .text(promptTemplate)
                        .param("gameTitle", question.gameTitle())
                        .param("rules", gameRules)
                ).user(question.question())
                .call()
                .entity(Answer.class);
    }

}
