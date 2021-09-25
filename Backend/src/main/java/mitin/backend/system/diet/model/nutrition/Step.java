package mitin.backend.system.diet.model.nutrition;

public class Step {
    private String action;

    public Step(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    @Override
    public String toString() {
        return action;
    }
}
