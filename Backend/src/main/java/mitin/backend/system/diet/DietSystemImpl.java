package mitin.backend.system.diet;

import mitin.backend.system.DietSystem;
import mitin.backend.system.diet.model.diet.DietType;

import java.util.List;
import java.util.Objects;

public class DietSystemImpl implements DietSystem {


    private static DietSystem dietSystem;

    private DietSystemImpl(){}

    public static DietSystem createDietSystem(){
        return Objects.requireNonNullElseGet(dietSystem, DietSystemImpl::createDietSystem);
    }
    @Override
    public DietSystemImpl createMenu(String userLogin, DietType dietType) {
        return null;
    }

    @Override
    public List<DietSystemImpl> getUserDiets(String userLogin) {
        return null;
    }
}
