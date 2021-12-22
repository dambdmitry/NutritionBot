package mitin.backend.system.user.model;

public class User {
    private final Long chatId;
    private Gender gender;
    private Integer weight;
    private Integer height;
    private Integer age;
    private Integer activity;


    public User(Long chatId) {
        this.chatId = chatId;
        this.gender = null;
        this.weight = null;
        this.height = null;
        this.age = null;
        this.activity = null;
    }

    public Long getChatId() {
        return chatId;
    }

    public Gender getGender() {
        return gender;
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

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setActivity(Integer activity) {
        this.activity = activity;
    }
}
