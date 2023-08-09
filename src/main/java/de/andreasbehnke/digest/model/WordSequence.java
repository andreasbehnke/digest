package de.andreasbehnke.digest.model;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a list of words. The factory method create grants each word to be trimmed and none empty sequences.
 */
public class WordSequence {

    private final List<String> words;

    private Set<WordSequence> subSequences;

    private String stringRepresentation;

    private WordSequence(List<String> words) {
        if (words == null || words.isEmpty()) {
            throw new RuntimeException("list of words must not be null or empty");
        }
        this.words = Collections.unmodifiableList(words);
    }

    public static WordSequence create(String inputText) {
        if (StringUtils.isEmpty(inputText)) {
            throw new RuntimeException("inputText must not be null or empty");
        }
        return new WordSequence(
                Arrays.stream(StringUtils.split(inputText))
                        .collect(Collectors.toList()));
    }

    public List<String> getWords() {
        return words;
    }

    public int size() {
        return words.size();
    }

    public WordSequence subSequence(int fromIndex, int toIndex) {
        return new WordSequence(words.subList(fromIndex, toIndex));
    }

    /**
     * @return Set containing all sub-sequences of this sequence. If this sequence is
     * abc def ghi jkl
     * than the set of all sub-sequences would be
     * abc
     * abc def
     * abc def ghi
     * abc def ghi jkl
     * def
     * def ghi
     * def ghi jkl
     * ghi
     * ghi jkl
     * jkl
     */
    public Set<WordSequence> allSubSequences() {
        if (subSequences == null) {
            Set<WordSequence> sequences = new HashSet<>();
            for(int start = 0; start < words.size(); start++) {
                for (int subStart = start; subStart < words.size(); subStart++) {
                    sequences.add(subSequence(start, subStart + 1));
                }
            }
            this.subSequences = Collections.unmodifiableSet(sequences);
        }
        return subSequences;
    }

    @Override
    public String toString() {
        if (stringRepresentation == null) {
            stringRepresentation = String.join(" ", words).toLowerCase();
        }
        return stringRepresentation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return toString().equals(o.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(toString());
    }
}