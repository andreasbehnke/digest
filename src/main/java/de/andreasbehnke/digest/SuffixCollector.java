package de.andreasbehnke.digest;

import de.andreasbehnke.digest.model.WordSequence;
import de.andreasbehnke.digest.model.WordSequenceComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * For a given list of source sequences, all possible suffixes are collected.
 * Example:
 * sources:
 *  - abc def ghi
 *  - 123 456 789
 *  - def ghi jkl 789
 *  - abc def xyz
 *  will result in the following suffixes, sorted in lexicographical order:
 *  123 456 789
 *  456 789
 *  789
 *  abc def ghi
 *  abc def xyz
 *  def ghi jkl 789
 *  def xyz
 *  ghi jkl 789
 *  xyz
 */
public class SuffixCollector {

    List<WordSequence> collectSuffixes(List<WordSequence> sources) {
        return sources.stream()
                .map(WordSequence::suffixes)
                .flatMap(Set::stream)
                // remove duplicates
                .distinct()
                .sorted(new WordSequenceComparator())
                .collect(Collectors.toList());
    }

    public List<WordSequence> groupSuffixes(List<WordSequence> sources) {
        ArrayList<WordSequence> groups = new ArrayList<>();
        List<WordSequence> sequences = collectSuffixes(sources);
        for (int i = 0; i < sequences.size(); i++) {
            if (i < sequences.size() - 1) {
                WordSequence sequence = sequences.get(i);
                WordSequence nextSequence = sequences.get(i + 1);
                if (!nextSequence.startsWith(sequence)) {
                    groups.add(sequence);
                }
            } else {
                groups.add(sequences.get(i));
            }
        }
        return groups;
    }
}
