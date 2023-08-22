package de.andreasbehnke.digest.model;

public class PatternMatches {

    private final int hits;

    private final WordSequence pattern;

    public PatternMatches(int hits, WordSequence pattern) {
        this.hits = hits;
        this.pattern = pattern;
    }

    public int getHits() {
        return hits;
    }

    public WordSequence getPattern() {
        return pattern;
    }

    public int length() {
        return pattern.size();
    }
}
