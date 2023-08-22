package de.andreasbehnke.digest.model;

/**
 * Represents a match of a {@link WordSequence} pattern within a source {@link WordSequence}.
 */
public class SequenceMatch {

    private final WordSequence pattern;

    private final int fromIndex;

    private final int toIndex;

    SequenceMatch(WordSequence pattern, int fromIndex) {
        if (fromIndex < 0) {
            throw new IllegalArgumentException("fromIndex must not be negative");
        }
        if (pattern == null) {
            throw new IllegalArgumentException("pattern must not be null");
        }
        if (pattern.size() == 0) {
            throw new IllegalArgumentException("pattern length must not be 0");
        }
        this.pattern = pattern;
        this.fromIndex = fromIndex;
        this.toIndex = pattern.size() + fromIndex - 1;
    }

    public WordSequence getPattern() {
        return pattern;
    }

    public int getFromIndex() {
        return fromIndex;
    }

    public int getToIndex() {
        return toIndex;
    }

    public int length() {
        return toIndex - fromIndex + 1;
    }
}
