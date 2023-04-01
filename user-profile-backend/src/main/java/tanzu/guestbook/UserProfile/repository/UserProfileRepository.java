package tanzu.guestbook.UserProfile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tanzu.guestbook.UserProfile.model.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long>{

}
