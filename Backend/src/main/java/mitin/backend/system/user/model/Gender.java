package mitin.backend.system.user.model;

public enum Gender {
    MALE("Мужской"),
    FEMALE("Женский");

    String name;

    Gender(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
