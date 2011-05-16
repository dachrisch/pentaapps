package de.pentasys.builder;

public enum Category {
    TRAVEL_START("Reisezeit(Beginn)"), PROJECT("Projektarbeit"), TRAVEL_END("Reisezeit(Ende)");

    private final String category;

    private Category(final String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

}
