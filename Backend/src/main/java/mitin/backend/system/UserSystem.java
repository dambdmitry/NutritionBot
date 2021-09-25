package mitin.backend.system;

import mitin.backend.system.diet.DietSystemImpl;
import mitin.backend.system.diet.model.diet.DietType;
import mitin.backend.system.model.Response;
import mitin.backend.system.user.model.User;

import java.util.List;

public interface UserSystem {
    Response signUp(User user);

    User getUser(String login);

    DietSystemImpl createMenu(String userLogin, DietType menuType);

    List<DietSystemImpl> getUserDiets(String userLogin);
}
