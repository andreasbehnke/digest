package de.andreasbehnke.digest;

import de.andreasbehnke.digest.model.WordSequence;
import de.andreasbehnke.digest.model.WordSequenceComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * For a given list of source sequences, all possible series of words are collected.
 *
 * Example:
 *
 * sources:
 *
 *  - abc def ghi
 *  - 123 456 789
 *  - def ghi jkl 789
 *  - abc def xyz
 *
 *
 *  will result in the following groups of series, sorted in lexicographical order:
 *
 *  1.)
 *  123 456 789
 *
 *  2.)
 *  456 789
 *
 *  3.)
 *  789
 *
 *  4.)
 *  abc def ghi
 *
 *  5.)
 *  abc def xyz
 *
 *  6.)
 *  def ghi jkl 789
 *
 *  7.)
 *  def xyz
 *
 *  8.)
 *  ghi jkl 789
 *
 *  9.)
 *  xyz
 *
 */
public class SubSequenceCollector {

    List<WordSequence> collectSubSequences(List<WordSequence> sources) {
        return sources.stream()
                .map(WordSequence::allSubSequences)
                .flatMap(Set::stream)
                // remove duplicates
                .distinct()
                .sorted(new WordSequenceComparator())
                .collect(Collectors.toList());
    }

    public List<WordSequence> groupSequences(List<WordSequence> sources) {
        ArrayList<WordSequence> groups = new ArrayList<>();
        List<WordSequence> sequences = collectSubSequences(sources);
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
