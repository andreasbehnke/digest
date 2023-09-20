package de.andreasbehnke.digest.model;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a list of words. The factory method create grants each word to be trimmed and none empty sequences.
 */
public class WordSequence {

    private final List<String> words;

    private Set<WordSequence> suffixes;

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
     * @return Set containing all suffixes of this sequence. If this sequence is
     * abc def ghi jkl
     * than the set of all suffixes would be
     * abc def ghi jkl
     * def ghi jkl
     * ghi jkl
     * jkl
     */
    public Set<WordSequence> suffixes() {
        if (suffixes == null) {
            Set<WordSequence> sequences = new HashSet<>();
            for(int start = 0; start < words.size(); start++) {
                sequences.add(subSequence(start, size()));
            }
            this.suffixes = Collections.unmodifiableSet(sequences);
        }
        return suffixes;
    }

    /**
     * Searches the given pattern and if part of the pattern is found in this sequence,
     * returns a {@link SequenceMatch} containing from index and to index of pattern
     * within this sequence and the sub pattern found. If you want to have an exact match,
     * use method contains.
     * @param pattern The pattern to search for
     * @return match found or empty
     */
    public Optional<SequenceMatch> match(WordSequence pattern) {
        int fromIndex = -1;
        for (int patternWord = 0; patternWord < pattern.size(); patternWord++) {
            for (int i = 0; i < size(); i++) {
                if (words.get(i).equals(pattern.getWords().get(patternWord))) {
                    fromIndex = i;
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
        int index = 0;
        int searchLength = Math.min(size() - fromIndex, pattern.size());
        for (int i = 0; i < searchLength; i++) {
            if (!words.get(i + fromIndex).equals(pattern.getWords().get(i))) {
                break;
            }
            index = i;
        }
        return Optional.of(new SequenceMatch(pattern.subSequence(0, index + 1), fromIndex));
    }

    /**
     * For each pattern in patterns searches the given pattern and if part of the pattern
     * is found in this sequence, returns a {@link SequenceMatch} containing from index and
     * to index of pattern within this sequence and the sub pattern found. If you want to have
     * an exact match, use method contains.
     * @param patterns The patterns to search for
     * @return match found or empty
     */
    public Optional<SequenceMatch> match(List<WordSequence> patterns) {
        for (WordSequence pattern: patterns) {
            Optional<SequenceMatch> match = match(pattern);
            if (match.isPresent()) {
                return match;
            }
        }
        return Optional.empty();
    }

    /**
     * Searches the given pattern and if complete pattern is found in this sequence,
     * returns a {@link SequenceMatch} containing from index and to index of pattern
     * within this sequence.
     * @param pattern The pattern to search for
     * @return match found or empty
     */
    public Optional<SequenceMatch> contains(WordSequence pattern) {
        int start = -1;
        String firstWord = pattern.getWords().get(0);
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).equals(firstWord)) {
                start = i;
                break;
            }
        }
        if (start > -1) {
            if (start + pattern.size() > words.size()) {
                return Optional.empty();
            }
            for (int i = 0; i < pattern.size(); i++) {
                if (!pattern.getWords().get(i).equals(words.get(i + start))) {
                    return Optional.empty();
                }
            }
            return Optional.of(new SequenceMatch(pattern, start));
        }
        return Optional.empty();
    }

    /**
     * For each pattern in patterns searches the given pattern and if complete pattern
     * is found in this sequence, pattern is added to the set returned.
     * @param patterns The patterns to search for
     * @return matches found or empty set
     */
    public Set<SequenceMatch> contains(List<WordSequence> patterns) {
        return patterns.stream()
                .map(this::contains)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    /**
     * Using this sequence as pattern, find the most frequent common match in a list of sequences
     * @param sources Search through these sources
     * @param minLength Min match length, shorter matches will be ignored
     * @param minHits Min number of hits, otherwise return empty
     * @return Empty, if requirements are not met, or {@link PatternMatches} containing all information about matching
     */
    public Optional<PatternMatches> mostFrequentCommonMatch(List<WordSequence> sources, int minLength, int minHits) {
        List<SequenceMatch> matches = sources.stream()
                .map(sequence -> sequence.match(this))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(sequenceMatch -> sequenceMatch.length() >= minLength)
                .toList();
        int hits = matches.size();
        if (hits >= minHits) {
            int length = matches.stream()
                    .mapToInt(SequenceMatch::length)
                    .min().orElse(0);
            if (length > 0) {
                return Optional.of(new PatternMatches(hits, subSequence(0, length)));
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

    public String toString(SequenceMatch ellipsis, String ellipsisPattern) {
        String ellipsisText = ellipsis.getPattern().toString();
        return toString().replace(ellipsisText, ellipsisPattern);
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