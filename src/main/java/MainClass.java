import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.vote.topjava.repository.UserRepository;
import ru.vote.topjava.service.UserService;

public class MainClass {


    public static void main(String...args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-db.xml");
        UserRepository userRepository = applicationContext.getBean(UserRepository.class);
        UserService userService = new UserService(userRepository);

        System.out.println(userService.getAll().toString());
    }
}
