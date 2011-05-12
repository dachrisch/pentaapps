package de.pentasys.zenal.builder;

public enum Project {
    MEDIASATURN("P080811.MED");

    private final String projectId;

    Project(final String projectId) {
        this.projectId = projectId;

    }

    public String getProjectId() {
        return projectId;
    }

}
