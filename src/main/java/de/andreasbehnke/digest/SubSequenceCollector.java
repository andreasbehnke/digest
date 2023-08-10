package de.andreasbehnke.digest;

import de.andreasbehnke.digest.model.GroupOfWordSequences;
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
 *  123
 *  123 456
 *  123 456 789
 *
 *  2.)
 *  456
 *  456 789
 *
 *  3.)
 *  789
 *
 *  4.)
 *  abc
 *  abc def
 *  abc def ghi
 *
 *  5.)
 *  abc def xyz
 *
 *  6.)
 *  def
 *  def ghi
 *  def ghi jkl
 *  def ghi jkl 789
 *
 *  7.)
 *  def xyz
 *
 *  8.)
 *  ghi
 *  ghi jkl
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

    public List<GroupOfWordSequences> groupSequences(List<WordSequence> sources) {
        int groupId = 0;
        int lastSequenceSize = 0;
        List<WordSequence> sequences = collectSubSequences(sources);
        List<GroupOfWordSequences> groups = new ArrayList<>();
        List<WordSequence> groupedSequences = new ArrayList<>();
        for (WordSequence sequence: sequences) {
            if (sequence.size() <= lastSequenceSize) {
                // equal or less size indicates beginning of new prefix group
                groups.add(new GroupOfWordSequences(groupedSequences, groupId));
                groupId++;
                groupedSequences = new ArrayList<>();
            }
            lastSequenceSize = sequence.size();
            groupedSequences.add(sequence);
        }
        groups.add(new GroupOfWordSequences(groupedSequences, groupId));
        return groups;
    }
}
