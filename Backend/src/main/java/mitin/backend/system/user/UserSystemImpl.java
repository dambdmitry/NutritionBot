package mitin.backend.system.user;

import mitin.backend.system.DietSystem;
import mitin.backend.system.UserSystem;
import mitin.backend.system.diet.DietSystemImpl;
import mitin.backend.system.diet.model.diet.DietType;
import mitin.backend.system.model.Response;
import mitin.backend.system.model.TypeResult;
import mitin.backend.system.user.model.User;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class UserSystemImpl implements UserSystem {

    private List<User> users;
    private DietSystem dietSystem;

    private static UserSystemImpl userSystem;


    private UserSystemImpl() {
        users = new LinkedList<>();
    }

    //Singleton pattern
    public static UserSystem createUserSystem(){
        return Objects.requireNonNullElseGet(userSystem, UserSystemImpl::new);
    }

    @Override
    public Response signUp(User user) {
        if (isContainTheUser(user.getLogin())){
            return new Response(TypeResult.BAD, "Такой пользователь уже есть");
        }else {
            addUser(user);
            return new Response(TypeResult.GOOD, "Успешная регистрация");
        }
    }

    @Override
    public User getUser(String login) {
        if (isContainTheUser(login)){
            return findUser(login);
        }
        return null;
    }

    @Override
    public DietSystemImpl createMenu(String userLogin, DietType menuType) {
        return dietSystem.createMenu(userLogin, menuType);
    }

    @Override
    public List<DietSystemImpl> getUserDiets(String userLogin) {
        return dietSystem.getUserDiets(userLogin);
    }

    /**
     * @param login Login for identifier User
     * @return found user or runtime exception
     */
    private User findUser(String login) {
        return users.stream().filter(user -> user.getLogin().equals(login)).findAny().get();
    }

    private boolean isContainTheUser(String login){
        return users.stream().anyMatch(user -> user.getLogin().equals(login));
    }

    private void addUser(User user){
        users.add(user);
    }
}
