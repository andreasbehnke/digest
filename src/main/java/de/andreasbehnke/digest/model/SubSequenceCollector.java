package de.andreasbehnke.digest.model;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SubSequenceCollector {

    List<WordSequence> collectSubSequences(List<WordSequence> sources) {
        return sources.stream()
                .map(WordSequence::allSubSequences)
                .flatMap(Set::stream)
                .distinct()
                .sorted(new WordSequenceComparator())
                .collect(Collectors.toList());
    }
}
