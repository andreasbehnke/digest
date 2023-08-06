package de.andreasbehnke.digest.model;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents a list of words. The factory method create grants each word to be trimmed and none empty sequences.
 */
public class WordSequence {

    private final List<String> words;

    private WordSequence(List<String> words) {
        if (words == null || words.isEmpty()) {
            throw new RuntimeException("list of words must not be null or empty");
        }
        this.words = words;
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

    @Override
    public String toString() {
        return String.join(" ", words);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordSequence sequence = (WordSequence) o;
        return words.equals(sequence.words);
    }

    @Override
    public int hashCode() {
        return Objects.hash(words);
    }
}