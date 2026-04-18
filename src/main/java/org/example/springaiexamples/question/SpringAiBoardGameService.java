package org.example.springaiexamples.question;

import org.example.springaiexamples.question.model.Answer;
import org.example.springaiexamples.question.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class SpringAiBoardGameService implements BoardGameService {

    private static final Logger log = LoggerFactory.getLogger(SpringAiBoardGameService.class);

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public SpringAiBoardGameService(ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {
        this.chatClient = chatClientBuilder.build();
        this.vectorStore = vectorStore;
    }

    @Value("classpath:/promptTemplates/systemPromptTemplate.st")
    Resource promptTemplate;

    @Override
    public Answer askQuestion(Question question) {
        String gameNameMatch = String.format("gameTitle == '%s'", normalizeGameTitle(question.gameTitle()));

        return chatClient.prompt()
                .system(systemSpec -> systemSpec
                        .text(promptTemplate)
                        .param("gameTitle", question.gameTitle()))
                .user(question.question())
                .advisors(QuestionAnswerAdvisor.builder(vectorStore).searchRequest(SearchRequest.builder()
                        .filterExpression(gameNameMatch)
                        .build()).build())
                .call()
                .entity(Answer.class);
    }

    private String normalizeGameTitle(String gameTitle) {
        return gameTitle.toLowerCase().replace(" ", "_");
    }

}
