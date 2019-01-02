package com.shareThegame.STG.Repository;

import com.shareThegame.STG.Model.FavouriteObjects;
import com.shareThegame.STG.Model.SportObject;
import com.shareThegame.STG.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    User findByEmail(String email);
    User findByUsername(String username);
    User findByUsernameOrEmail(String username, String email);
    List<SportObject> findBySportObjects(SportObject sportObject);


}
