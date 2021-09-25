package mitin.backend.system.user.model;

import mitin.backend.system.user.model.Gender;

public class User {
    private String login;
    private String name;
    private Gender sex;
    private Integer weight;
    private Integer height;
    private Integer age;
    private Integer activity;

    public User(String login, String name, Gender sex, Integer weight, Integer height, Integer age, Integer activity) {
        this.login = login;
        this.name = name;
        this.sex = sex;
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.activity = activity;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public Gender getSex() {
        return sex;
    }

    public Integer getWeight() {
        return weight;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getAge() {
        return age;
    }

    public Integer getActivity() {
        return activity;
    }
}
