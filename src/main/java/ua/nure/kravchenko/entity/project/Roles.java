package ua.nure.kravchenko.entity.project;

public enum Roles {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER"),
    ROLE_MANAGER("ROLE_MANAGER"),
    IOT("IOT");

    private String name;

    Roles(String name) {
        this.name = name();
    }

    public String getName() {
        return name;
    }
}
