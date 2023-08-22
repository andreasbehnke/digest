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
     * abc def ghi jkl
     * def ghi jkl
     * ghi jkl
     * jkl
     */
    public Set<WordSequence> allSubSequences() {
        if (subSequences == null) {
            Set<WordSequence> sequences = new HashSet<>();
            for(int start = 0; start < words.size(); start++) {
                sequences.add(subSequence(start, size()));
            }
            this.subSequences = Collections.unmodifiableSet(sequences);
        }
        return subSequences;
    }

    /**
     * Searches the given pattern and if part of the pattern is found in this sequence,
     * returns a {@link SequenceMatch} containing from index and to index of pattern
     * within this sequence and the sub pattern found.
     * @param pattern The pattern to search for
     * @return match found or empty
     */
    public Optional<SequenceMatch> match(WordSequence pattern) {
        int fromIndex = -1;
        for (int patternWord = 0; patternWord < pattern.size(); patternWord++) {
            for (int index = 0; index < size(); index++) {
                if (words.get(index).equals(pattern.getWords().get(patternWord))) {
                    fromIndex = index;
                    break;
                }
            }
            if (fromIndex > -1) {
                break;
            }
        }
        if (fromIndex == -1) {
            return Optional.empty();
        }
        int toIndex = fromIndex;
        int searchLength = Math.min(size() - fromIndex, pattern.size());
        for (int index = 0; index < searchLength; index++) {
            if (!words.get(index + fromIndex).equals(pattern.getWords().get(index))) {
                break;
            }
            toIndex = index + fromIndex;
        }
        return Optional.of(new SequenceMatch(pattern.subSequence(0, toIndex - fromIndex + 1), fromIndex, toIndex));
    }

    /**
     * Using this sequence as pattern, find the most frequent common match in a list of sequences
     * @param sources Search through these sources
     * @param minLength Min match length, shorter matches will be ignored
     * @param minCount Min number of hits, otherwise return empty
     * @return Empty, if requirements are not met, or {@link SequenceMatch} for the best fitting match
     */
    public Optional<SequenceMatch> mostFrequentCommonMatch(List<WordSequence> sources, int minLength, int minCount) {
        List<SequenceMatch> matches = sources.stream()
                .map(sequence -> sequence.match(this))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(sequenceMatch -> sequenceMatch.length() >= minLength)
                .toList();
        if (matches.size() >= minCount) {
            int length = matches.stream()
                    .mapToInt(SequenceMatch::length)
                    .min().orElse(0);
            if (length > 0) {
                return Optional.of(new SequenceMatch(subSequence(0, length), 0, length - 1));
            }
        }
        return Optional.empty();
    }

    /**
     * @param another sequence which is a prefix of this sequence
     * @return true, if this sequence has another prefix as prefix
     */
    public boolean startsWith(WordSequence another) {
        if (another.size() > size()) {
            return false;
        } else {
            for (int i = 0; i < another.size(); i++) {
                if (!words.get(i).equals(another.words.get(i))) {
                    return false;
                }
            }
            return true;
        }
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