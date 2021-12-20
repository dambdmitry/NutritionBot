package mitin.backend.system.diet;

import mitin.backend.system.DietSystem;
import mitin.backend.system.diet.engine.DietEngine;
import mitin.backend.system.diet.engine.impl.DietEngineImpl;
import mitin.backend.system.diet.model.diet.DietType;
import mitin.backend.system.diet.model.menu.MealPlanForTheDay;
import mitin.backend.system.diet.model.menu.Menu;
import mitin.backend.system.diet.model.menu.WeekDay;
import mitin.backend.system.user.UserSystemImpl;
import mitin.backend.system.user.model.Gender;

import java.util.*;

public class DietSystemImpl implements DietSystem {

    private static DietSystem dietSystem;
    private DietEngine dietEngine;

    private Map<Long, Menus> diets;

    class Menus {
        TypedMenu normal;
        TypedMenu gain;
        TypedMenu loss;

        public Menus() {
        }

        public TypedMenu getMenu(DietType type) {
            if (type == DietType.NORMAL) {
                return normal;
            } else if (type == DietType.WEIGHT_GAIN) {
                return gain;
            } else {
                return loss;
            }
        }

        public void setMenu(TypedMenu typedMenu) {
            DietType type = typedMenu.typeMenu;
            if (type == DietType.NORMAL) {
                normal = typedMenu;
            } else if (type == DietType.WEIGHT_GAIN) {
                gain = typedMenu;
            } else {
                loss = typedMenu;
            }
        }

        public TypedMenu getNormal() {
            return normal;
        }

        public void setNormal(TypedMenu normal) {
            this.normal = normal;
        }

        public TypedMenu getGain() {
            return gain;
        }

        public void setGain(TypedMenu gain) {
            this.gain = gain;
        }

        public TypedMenu getLoss() {
            return loss;
        }

        public void setLoss(TypedMenu loss) {
            this.loss = loss;
        }
    }

    private DietSystemImpl() {
        diets = new HashMap<>();
        dietEngine = new DietEngineImpl();
    }

    //Singleton pattern
    public static DietSystem createDietSystem() {
        if (dietSystem == null) {
            dietSystem = new DietSystemImpl();
            return dietSystem;
        }
        return dietSystem;
    }


    @Override
    public Menu createMenu(Long chatId, DietType dietType) {
        Menu menu;
        Integer dailyCalorie = getDailyCalorie(chatId);
        menu = dietEngine.createMenu(dailyCalorie, dietType);
        boolean hasUserMenu = diets.containsKey(chatId);
        TypedMenu typedMenu = new TypedMenu(dietType, menu);
        if (hasUserMenu) {
            //diets.computeIfAbsent(chatId, k -> new ArrayList<>()).add(typedMenu);
            Menus typedMenus = diets.get(chatId);
            typedMenus.setMenu(typedMenu);
        } else {
            //diets.put(chatId, List.of(typedMenu));
            Menus menus = new Menus();
            menus.setMenu(typedMenu);
            diets.put(chatId, menus);
        }
        return menu;
    }

    @Override
    public List<Menu> getUserDiets(Long chatId) {
        if (diets.containsKey(chatId)) {
            Menus typedMenus = diets.get(chatId);
            List<Menu> res = new LinkedList<>();
            if (typedMenus.gain != null) {
                res.add(typedMenus.gain.menu);
            }
            if (typedMenus.loss != null) {
                res.add(typedMenus.loss.menu);
            }
            if (typedMenus.normal != null) {
                res.add(typedMenus.normal.menu);
            }
            return res;
        }
        return Collections.emptyList();
    }

    @Override
    public Integer getDailyCalorie(Long chatId) {
        Map<String, String> userParameters = UserSystemImpl.createUserSystem().getUserParameters(chatId);
        Integer activity = Integer.parseInt(userParameters.get("Активность"));
        Integer weight = Integer.parseInt(userParameters.get("Вес"));
        Integer height = Integer.parseInt(userParameters.get("Рост"));
        Integer age = Integer.parseInt(userParameters.get("Возраст"));
        int act = (1 + activity / 10);
        String sex = userParameters.get("Пол");
        Gender gender;
        double dailyCalorie;
        if (sex.equals("Мужской")) {
            dailyCalorie = (10 * weight + 6.25 * height - 5 * age + 5) * (1 + activity / 10.0);
        } else {
            dailyCalorie = (10 * weight + 6.25 * height - 5 * age - 161) * (1 + activity / 10.0);
        }

        return Math.toIntExact(Math.round(dailyCalorie));
    }

    @Override
    public void deleteDiets(Long chatId) {
        if (diets.containsKey(chatId)) {
            diets.remove(chatId);
        }
    }

    @Override
    public MealPlanForTheDay getMenuByDay(Long chatId, WeekDay day, DietType type) {
        Menus menus = diets.get(chatId);
        TypedMenu typedMenu = menus.getMenu(type);
        return typedMenu.getMenu().getDayPlan(day);
    }

    class TypedMenu {
        DietType typeMenu;
        Menu menu;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TypedMenu typedMenu = (TypedMenu) o;

            if (typeMenu != typedMenu.typeMenu) return false;
            return true;
        }

        @Override
        public int hashCode() {
            int result = typeMenu.hashCode();
            result = 31 * result + menu.hashCode();
            return result;
        }

        public TypedMenu(DietType typeMenu, Menu menu) {
            this.typeMenu = typeMenu;
            this.menu = menu;
        }

        public DietType getTypeMenu() {
            return typeMenu;
        }

        public void setTypeMenu(DietType typeMenu) {
            this.typeMenu = typeMenu;
        }

        public Menu getMenu() {
            return menu;
        }

        public void setMenu(Menu menu) {
            this.menu = menu;
        }
    }
}
