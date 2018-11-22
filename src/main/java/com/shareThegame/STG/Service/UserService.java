package com.shareThegame.STG.Service;

import com.shareThegame.STG.Model.Role;
import com.shareThegame.STG.Model.User;

import com.shareThegame.STG.Repository.RoleRepository;
import com.shareThegame.STG.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sun.security.util.Password;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service("userService")
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void saveUser( User user){



        user.setPassword ( bCryptPasswordEncoder.encode (user.getPassword ()) );
        user.setActive ( 1 );
        Role role =roleRepository.findByRole ( "CLIENT" );

        user.setRoles(new ArrayList<Role>(Arrays.asList(role)));
        //System.out.println (role.getRole () +" "+role.getId (  ));
        userRepository.save(user);

    }


    public  User findByUsername(String username){
        return  userRepository.findByUsername ( username );
    }
    public User findByEmail(String email){
        return userRepository.findByEmail ( email );

    }
    public boolean comaprePassword ( String dbpassword , String postpassword ) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder ( );

        if ( passwordEncoder.matches ( postpassword , dbpassword ) ) {

            return true;
        } else {
            return false;
        }
    }
    public void changePassword( User user , String newPassword ){
        user.setPassword ( bCryptPasswordEncoder.encode (newPassword) );
        userRepository.save ( user );


    }
}
