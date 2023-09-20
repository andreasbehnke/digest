package de.andreasbehnke.digest.model;

import java.util.Comparator;

public class PatternMatchesComparator implements Comparator<PatternMatches> {

    @Override
    public int compare(PatternMatches o1, PatternMatches o2) {
        return o1.getHits() - o2.getHits();
    }
}
