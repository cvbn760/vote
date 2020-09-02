import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.vote.topjava.model.*;
import ru.vote.topjava.repository.*;
import ru.vote.topjava.service.*;

import java.util.List;
import java.util.stream.Collectors;

public class MainClass {


    public static void main(String...args) {
        try {
            ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-db.xml");

            MealRepository mealRepository = applicationContext.getBean(MealRepository.class);
            MenuRepository menuRepository = applicationContext.getBean(MenuRepository.class);
            RestaurantRepository restaurantRepository = applicationContext.getBean(RestaurantRepository.class);
            UserRepository userRepository = applicationContext.getBean(UserRepository.class);
            VoterRepository voterRepository = applicationContext.getBean(VoterRepository.class);

            MealService mealService = new MealService(mealRepository);
            MenuService menuService = new MenuService(menuRepository);
            RestaurantService restaurantService = new RestaurantService(restaurantRepository);
            UserService userService = new UserService(userRepository);
            VoterService voterService = new VoterService(voterRepository);

            //////////////////////////////////////////////////////////////////////////////////////////////
            //////////////////ЧАСТИ СЦЕНАРИЕВ
            //////////////////////////////////////////////////////////////////////////////////////////////
            System.out.println("\nДостать меню");
            Menu menu = menuService.getMenuById(4);
            System.out.println(menu.toString());

            //////////////////////////////////////////////////////////////////////////////////////////////
            System.out.println("\nСохранить еду");
            Meal meal = new Meal(null,1, "TEST_NAME", "TEST_DESCRIPTION", 2000, (float) 99.9 );
            meal.setMenu(menu);
            System.out.println(mealRepository.save(meal).toString());

            //////////////////////////////////////////////////////////////////////////////////////////////
            System.out.println("\nДостать сохраненную еду");
            System.out.println(mealService.getMealById(45));

            //////////////////////////////////////////////////////////////////////////////////////////////
            System.out.println("\nПопытка добавить еду без ссылки на меню (ошибка)");
            // Meal mealWithoutRefOnMenu = new Meal(null, null, "TEST_NAME", "TEST_DESCRIPTION", 9999, (float) 99.9);
            // System.out.println(mealRepository.save(mealWithoutRefOnMenu).toString());

            //////////////////////////////////////////////////////////////////////////////////////////////
            System.out.println("\nПопытка добавить еду с несуществующей ссылкой на меню(ошибка)");
            // Meal mealWithNotValidRefOnMenu = new Meal(null,300, "TEST_NAME", "TEST_DESCRIPTION", 9999, (float) 99.9);
            // System.out.println(mealRepository.save(mealWithNotValidRefOnMenu).toString());

            //////////////////////////////////////////////////////////////////////////////////////////////
            System.out.println("\nУдалить еду");
            mealService.deleteMealById(38);
            // System.out.println(mealRepository.findById(38).get());

            //////////////////////////////////////////////////////////////////////////////////////////////
            System.out.println("\nДостать вообще всю еду");
            mealService.getAllMeal().stream().forEach(System.out::println);

            //////////////////////////////////////////////////////////////////////////////////////////////
            System.out.println("\nДостать меню по ссылке из еды");
            Meal mealForMenu = mealService.getMealById(30);
            Menu menuInMeal = mealForMenu.getMenu();
            System.out.println(menuInMeal.toString());

            //////////////////////////////////////////////////////////////////////////////////////////////
            System.out.println("\nДостать список еды конкретного меню");
            menuService.getMenuById(3).getMealList().stream().forEach(System.out::println);

            //////////////////////////////////////////////////////////////////////////////////////////////
            System.out.println("\nДостать все рестораны");
            restaurantService.getAllRestaurants().stream().forEach(System.out::println);

            //////////////////////////////////////////////////////////////////////////////////////////////
            System.out.println("\nДостать ресторан");
            System.out.println(restaurantService.getRestaurantById(2).toString());

            //////////////////////////////////////////////////////////////////////////////////////////////
            System.out.println("\nДостать администратора ресторана");
            System.out.println(restaurantService.getRestaurantById(2).getAdminRest().toString());

            //////////////////////////////////////////////////////////////////////////////////////////////
            System.out.println("\nДостать все меню ресторана");
            restaurantService.getRestaurantById(2).getMenus().stream().forEach(System.out::println);

            //////////////////////////////////////////////////////////////////////////////////////////////
            System.out.println("\nДостать вообще всех пользователей");
            userService.getAllUsers().stream().forEach(System.out::println);

            //////////////////////////////////////////////////////////////////////////////////////////////
            System.out.println("\nДостать только администраторов ресторана");
            userService.findAdministrators().stream().forEach(System.out::println);

            //////////////////////////////////////////////////////////////////////////////////////////////
            System.out.println("\nДостать только обычных пользователей");
            userService.findOtherUsers().stream().forEach(System.out::println);

            //////////////////////////////////////////////////////////////////////////////////////////////
            System.out.println("\nДостать одного любого пользователя");
            User user = userService.getAllUsers().get(2);
            System.out.println(user.toString());

            //////////////////////////////////////////////////////////////////////////////////////////////
            System.out.println("\nПолучить роль пользователя");
            System.out.println(user.getRoles().toString());

            //////////////////////////////////////////////////////////////////////////////////////////////
            System.out.println("\nПолучить вообще все результаты голосования");
            voterService.getAllVoters().stream().forEach(System.out::println);

            //////////////////////////////////////////////////////////////////////////////////////////////
            System.out.println("\nПолучить все результаты голосования для конкретного пользователя");
            User userOne = userService.findOtherUsers().get(2);
            List<Voter> voters = voterService.getReportForUser(userOne);
            for (Voter v : voters){
                System.out.println(v.toString());
            }

            //////////////////////////////////////////////////////////////////////////////////////////////
            System.out.println("\nПолучить любое меню из тех, в голосовании за которое пользователь принимал участие (1)");
            User igor = userService.getUserById(2);
            System.out.println("Пользователь: " + igor);
            System.out.println("Принимал участие в голосованиях: ");
            List<Voter> voterList = voterService.getReportForUser(igor).stream().collect(Collectors.toList());
            voterList.stream().forEach(System.out::println);
            System.out.println("2020-07-15 он отдал свой голос за меню: ");
            System.out.println(menuService.getMenuForUserByVote(igor.getId(), voterList.get(0).getMenuId()));

            //////////////////////////////////////////////////////////////////////////////////////////////
            System.out.println("\nПолучить любое меню из тех, в голосовании за которое пользователь принимал участие (2)");
            User usr = userService.getUserById(4);
            System.out.println("Пользователь: " + usr);
            System.out.println("Принимал участие в голосованиях: ");
            List<Voter> voterListTo = voterService.getReportForUser(usr).stream().collect(Collectors.toList());
            voterListTo.stream().forEach(System.out::println);
            System.out.println("2020-07-14 он отдал свой голос за меню: ");
            System.out.println(menuService.getMenuForUserByVote(usr.getId(), voterListTo.get(1).getMenuId()));

            //////////////////////////////////////////////////////////////////////////////////////////////
            System.out.println("\nПолучить меню, победившее в конкретный день");
            System.out.println(menuService.getBestMenuByDate(voterListTo.get(0).getDate()));

            //////////////////////////////////////////////////////////////////////////////////////////////
            System.out.println("\nПолучить меню, проигравшее в конкретный день");
            System.out.println(menuService.geMenuLoserByDate(voterListTo.get(0).getDate()));

            //////////////////////////////////////////////////////////////////////////////////////////////
            System.out.println("\nПользователь голосует (тест 1 - уже проголосовал за это меню)");
            User userT1 = userService.getUserById(4);
            Menu menuT1 = menuService.getMenuById(6);
            System.out.println("Пользователь: " + userT1 + ". \nдолжен проголосовать за меню: " + menuT1);
            System.out.println("Было меню: " +  menuService.getMenuById(6));
            System.out.println("Был голос: " + voterService.getReportForUser(userT1));
            voterService.addRecAboutVote(menuT1, userT1, true);
            System.out.println("Стало меню: " + menuService.getMenuById(6));
            System.out.println("Стал голос: " + voterService.getReportForUser(userT1));

            //////////////////////////////////////////////////////////////////////////////////////////////
            System.out.println("\nПользователь голосует (тест 2 - проголосовал за 1 ресторан, но затем передумал и решил проголосовать за другой)");
            User userT2 = userService.getUserById(4);
            Menu menuT2 = menuService.getMenuById(0);
            System.out.println("Пользователь: " + userT2 + ". \nдолжен проголосовать за меню: " + menuT2);
            System.out.println("Было меню 1: " +  menuService.getMenuById(6));
            System.out.println("Было меню 2: " +  menuService.getMenuById(0));
            System.out.println("Был голос 1: " + voterService.getReportForUser(userT2));
            voterService.addRecAboutVote(menuT2, userT2, true);
            System.out.println("Стало меню 1: " + menuService.getMenuById(6));
            System.out.println("Стало меню 2: " +  menuService.getMenuById(0));
            System.out.println("Стало голос 1: " + voterService.getReportForUser(userT2));


            //////////////////////////////////////////////////////////////////////////////////////////////
            System.out.println("\nПользователь голосует (тест 3 - не голосовал в этот день вообще)");
            User userT3 = userService.getUserById(4);
            Menu menuT3 = menuService.getMenuById(2);
            System.out.println("Пользователь: " + userT3 + ". \nдолжен проголосовать за меню: " + menuT3);
            System.out.println("Было меню: " +  menuService.getMenuById(2));
            System.out.println("Был голос: " + voterService.getReportForUser(userT3));
            voterService.addRecAboutVote(menuT3, userT3, true);
            System.out.println("Стало меню: " + menuService.getMenuById(2));
            System.out.println("Стал голос: " + voterService.getReportForUser(userT3));

            //////////////////////////////////////////////////////////////////////////////////////////////
            System.out.println("\nПользователь голосует (тест 4 - проголосовал, но решил отменить голос)");
            User userT4 = userService.getUserById(4);
            Menu menuT4 = menuService.getMenuById(4);
            System.out.println("Пользователь: " + userT4 + ". \nдолжен отменить голос за меню: " + menuT4);
            System.out.println("Было меню: " +  menuService.getMenuById(4));
            System.out.println("Был голос: " + voterService.getReportForUser(userT4));
            voterService.addRecAboutVote(menuT4, userT4, false);
            System.out.println("Стало меню: " + menuService.getMenuById(4));
            System.out.println("Стал голос: " + voterService.getReportForUser(userT4));



            System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Тесты успешно пройдены");
        }
        catch (Exception exc){
            System.out.println("\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Что-то пошло не так...");
            exc.printStackTrace();
        }
    }
}
