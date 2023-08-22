package de.andreasbehnke.digest.model;

/**
 * Represents a match of a {@link WordSequence} pattern within a source {@link WordSequence}.
 */
public class SequenceMatch {

    private final WordSequence pattern;

    private final int fromIndex;

    private final int toIndex;

    public SequenceMatch(WordSequence pattern, int fromIndex, int toIndex) {
        if (fromIndex < 0) {
            throw new IllegalArgumentException("fromIndex must not be negative");
        }
        if (toIndex < 0) {
            throw new IllegalArgumentException("toIndex must not be negative");
        }
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException("fromIndex " + fromIndex + " is greater than toIndex " + toIndex);
        }
        if (pattern == null) {
            throw new IllegalArgumentException("pattern must not be null");
        }
        if (pattern.size() != toIndex - fromIndex + 1) {
            throw new IllegalArgumentException("pattern length does not match distance between fromIndex and toIndex");
        }
        this.pattern = pattern;
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
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
