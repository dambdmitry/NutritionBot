package mitin.backend.system.diet.model.nutrition.products;

public class Ingredient {
    private String name;
    private String amount;

    public Ingredient(String name, String amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ingredient that = (Ingredient) o;

        if (name.equals(that.name)){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
