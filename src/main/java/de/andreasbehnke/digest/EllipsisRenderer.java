package de.andreasbehnke.digest;

import de.andreasbehnke.digest.model.PatternMatches;
import de.andreasbehnke.digest.model.PatternMatchesComparator;
import de.andreasbehnke.digest.model.SequenceMatch;
import de.andreasbehnke.digest.model.WordSequence;

import java.util.*;
import java.util.stream.Collectors;

public class EllipsisRenderer {

    private final int minMatchLength;

    private final int minMatchHits;

    private List<WordSequence> sortedPatterns;

    private Set<WordSequence> currentMatches;

    public EllipsisRenderer(int minMatchLength, int minMatchHits) {
        this.minMatchLength = minMatchLength;
        this.minMatchHits = minMatchHits;
    }

    public EllipsisRenderer() {
        this.minMatchLength = 2;
        this.minMatchHits = 2;
    }

    private String renderText(WordSequence input) {

        Set<SequenceMatch> newMatches = input.contains(sortedPatterns);
        // build union set with current matches, these matches could be
        // replaced with ellipsis.
        Set<SequenceMatch> activeMatches = newMatches.stream()
                // filter all matches which have been matched previous input text
                .filter(match -> currentMatches.contains(match.getPattern()))
                .collect(Collectors.toSet());
        // produce set of all positions which must be replaced with ellipsis
        Set<Integer> ellipsis = activeMatches.stream()
                .map(SequenceMatch::range)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        this.currentMatches = newMatches.stream().map(SequenceMatch::getPattern).collect(Collectors.toSet());

        return input.toString(ellipsis, "...");
    }

    public List<String> render(final List<String> inputTexts) {
        final List<WordSequence> inputSequences = inputTexts.stream().map(WordSequence::create).toList();
        sortedPatterns = new SuffixCollector().groupSuffixes(inputSequences)
                .stream().map(sequence -> sequence.mostFrequentCommonMatch(inputSequences, minMatchLength, minMatchHits))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .sorted(new PatternMatchesComparator())
                .map(PatternMatches::getPattern)
                .distinct()
                .toList();
        currentMatches = new HashSet<>();
        return inputSequences.stream().map(this::renderText).toList();
    }
}
