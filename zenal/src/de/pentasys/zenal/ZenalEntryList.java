package de.pentasys.zenal;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class ZenalEntryList {

    private final SortedSet<ZenalEntry> entries = new TreeSet<ZenalEntry>();

    public int size() {
        return entries.size();
    }

    public boolean add(final ZenalEntry zenalEntry) {
        return entries.add(zenalEntry);
    }

    public ZenalEntry last() {
        return entries.last();
    }

    public ZenalEntry first() {
        return entries.first();
    }

    public Iterator<ZenalEntry> iterator() {
        return entries.iterator();
    }

}
