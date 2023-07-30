package com.ishopee.logisticsinventorymanagement.services.impl;

import com.ishopee.logisticsinventorymanagement.constants.UserMode;
import com.ishopee.logisticsinventorymanagement.exceptions.UserInfoNotFoundException;
import com.ishopee.logisticsinventorymanagement.models.UserInfo;
import com.ishopee.logisticsinventorymanagement.repositories.UserInfoRepo;
import com.ishopee.logisticsinventorymanagement.services.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserInfoServiceImpl implements IUserInfoService, UserDetailsService {

    @Autowired
    private UserInfoRepo repo;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public Boolean existsByEmail(String email) {
        return repo.existsByEmail(email);
    }

    @Override
    public Integer saveUserInfo(UserInfo userInfo) {
        String pwd = userInfo.getPassword();
        String encryptedPw = encoder.encode(pwd);
        userInfo.setPassword(encryptedPw);
        return repo.save(userInfo).getId();
    }

    @Override
    public List<UserInfo> getAllUserInfo() {
        return repo.findAll();
    }

    @Override
    public UserInfo getOneUserInfoByEmail(String email) {
        return repo.findByEmail(email).get();
    }

    @Override
    @Transactional
    public void updateUserStatus(Integer id, UserMode mode) {
        repo.updateUserStatus(id, mode);
    }

    @Override
    @Transactional
    public void updateUserPassword(String username, String pwd) {
        repo.updatepassword(username, pwd);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> info = repo.findByEmail(username);
        if (info.isEmpty() || info.get().getMode().equals(UserMode.DISABLED)) {
            throw new UserInfoNotFoundException("USER DOES NOT EXIST OR DISABLED !");
        }
        UserInfo userInfo = info.get();
        return new User(userInfo.getEmail(), userInfo.getPassword(), userInfo.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRole().name())).collect(Collectors.toSet()));
    }

    /*    OLD METHOD     */

/*
//     Needed 3 things i.e. username , password, authorities, and pass it to WebSecurityConfigurerAdapter again(internally)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//         Load current user by username i.e. email
        Optional<UserInfo> opt = repo.findByEmail(username);

        UserInfo userInfo;
        Set<GrantedAuthority> authorities;

        if (opt.isPresent()) {
//             Convert UserInfo into User of Security
            userInfo = opt.get();
//            Convert Userinfo Roles into User Roles format and give it  to GrantedAuthority
            authorities = new HashSet<>();
            for (Role role : userInfo.getRoles()) {
                authorities.add(new SimpleGrantedAuthority(role.getRole().name()));
            }
        } else {
            throw new UserInfoNotFoundException("USER DOES NOT EXIST !");
        }
        return new User(userInfo.getEmail(), userInfo.getPassword(), authorities);
    }
*/

}
