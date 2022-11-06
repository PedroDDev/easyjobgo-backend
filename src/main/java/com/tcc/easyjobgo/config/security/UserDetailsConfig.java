package com.tcc.easyjobgo.config.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcc.easyjobgo.factory.UserFactory;
import com.tcc.easyjobgo.model.User;
import com.tcc.easyjobgo.repository.IUserRepository;

@Service
@Transactional
public class UserDetailsConfig  implements UserDetailsService{

    final IUserRepository userRepository = UserFactory.createUserService();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) throw new UsernameNotFoundException("User Not Found.");
        if(user.isEnabled() == false) throw new UsernameNotFoundException("User not Enabled.");
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true,  user.getAuthorities());
    }
}
