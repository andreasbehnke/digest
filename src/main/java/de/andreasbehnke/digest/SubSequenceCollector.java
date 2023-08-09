package de.andreasbehnke.digest;

import de.andreasbehnke.digest.model.GroupOfWordSequences;
import de.andreasbehnke.digest.model.WordSequence;
import de.andreasbehnke.digest.model.WordSequenceComparator;

import java.util.ArrayList;
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
