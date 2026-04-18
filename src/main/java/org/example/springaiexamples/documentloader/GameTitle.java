package org.example.springaiexamples.documentloader;

public record GameTitle(String title) {

    public String getNormalizedTitle() {
        return title.toLowerCase().replace(" ", "_");
    }

}
