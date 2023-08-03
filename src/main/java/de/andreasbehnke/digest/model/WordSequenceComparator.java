package de.andreasbehnke.digest.model;

import java.util.Comparator;

public class WordSequenceComparator implements Comparator<WordSequence> {
    @Override
    public int compare(WordSequence o1, WordSequence o2) {
        String seq1 = o1.toString().toLowerCase();
        String seq2 = o2.toString().toLowerCase();
        return seq1.compareTo(seq2);
    }
}
