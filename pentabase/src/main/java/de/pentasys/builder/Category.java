package de.pentasys.builder;

public enum Category {
    TRAVEL_START("Reisezeit(Beginn)"), PROJECT("Projektarbeit"), TRAVEL_END("Reisezeit(Ende)");

    private final String categoryName;

    private Category(final String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

}
