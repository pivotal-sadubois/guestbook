package tanzu.guestbook.UserProfile.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tanzu.guestbook.UserProfile.model.UserProfile;
import tanzu.guestbook.UserProfile.repository.UserProfileRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/")
public class UserProfileController {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @GetMapping("users")
    public List < UserProfile > getUsers() {
        return this.userProfileRepository.findAll();
    }
}
