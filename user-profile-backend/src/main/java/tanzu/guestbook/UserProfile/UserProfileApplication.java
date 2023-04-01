package tanzu.guestbook.UserProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import tanzu.guestbook.UserProfile.model.UserProfile;
import tanzu.guestbook.UserProfile.repository.UserProfileRepository;

@SpringBootApplication
public class UserProfileApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(UserProfileApplication.class, args);
    }

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public void run(String...args) throws Exception {
        this.userProfileRepository.save(new UserProfile("Ramesh", "Fadatare", "ramesh@gmail.com"));
        this.userProfileRepository.save(new UserProfile("Tom", "Cruise", "tom@gmail.com"));
        this.userProfileRepository.save(new UserProfile("Tony", "Stark", "tony@gmail.com"));
    }
}