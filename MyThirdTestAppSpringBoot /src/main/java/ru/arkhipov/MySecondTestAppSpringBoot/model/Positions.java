package ru.arkhipov.MySecondTestAppSpringBoot.model;

public enum Positions {
    DEV("Developer", false),
    HR("HR-specialist", false),
    TL("Team Lead", true),
    QA("Quality Assurance", false),
    PM("Project Manager", true),
    HR_MANAGER("HR Manager", true),
    DIRECTOR("Director", true);

    private final String description;
    private final boolean isManager;

    Positions(String description, boolean isManager) {
        this.description = description;
        this.isManager = isManager;
    }

    public String getDescription() {
        return description;
    }

    public boolean isManager() {
        return isManager;
    }
}