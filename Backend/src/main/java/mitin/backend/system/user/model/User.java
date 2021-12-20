package mitin.backend.system.user.model;

public class User {
    private Long chatId;
    private Gender sex;
    private Integer weight;
    private Integer height;
    private Integer age;
    private Integer activity;


    public User(Long chatId) {
        this.chatId = chatId;
        this.sex = null;
        this.weight = null;
        this.height = null;
        this.age = null;
        this.activity = null;
    }

    public Long getChatId() {
        return chatId;
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

    public void setSex(Gender sex) {
        this.sex = sex;
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
