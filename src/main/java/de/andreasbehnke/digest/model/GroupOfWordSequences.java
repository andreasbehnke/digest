package de.andreasbehnke.digest.model;

import java.util.List;

public class GroupOfWordSequences {

    private final List<WordSequence> sequences;

    private final int id;

    public GroupOfWordSequences(List<WordSequence> sequences, int id) {
        this.sequences = sequences;
        this.id = id;
    }

    public List<WordSequence> getSequences() {
        return sequences;
    }

    public int getId() {
        return id;
    }
}
