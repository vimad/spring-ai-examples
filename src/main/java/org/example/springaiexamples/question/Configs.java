package org.example.springaiexamples.question;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configs {

    @Bean
    ChatClient chatClient(ChatClient.Builder chatClientBuilder, ChatClient.Builder chatClientBuilder2, VectorStore vectorStore) {
        var advisor = RetrievalAugmentationAdvisor.builder()
                .documentRetriever(
                        VectorStoreDocumentRetriever.builder()
                                .vectorStore(vectorStore)
                                .build())
                .queryTransformers(
                        RewriteQueryTransformer.builder()
                                .chatClientBuilder(chatClientBuilder2)
                                .build())
                .build();

        return chatClientBuilder
                .defaultAdvisors(advisor)
                .build();
    }
}
