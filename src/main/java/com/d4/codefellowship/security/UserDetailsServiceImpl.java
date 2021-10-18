package com.d4.codefellowship.security;

import com.d4.codefellowship.models.ApplicationUser;
import com.d4.codefellowship.repos.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AppUserRepo appUserRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser checkUser = appUserRepo.findApplicationUserByUsername(username).orElseThrow();
        
//        Authentication authentication = new UsernamePasswordAuthenticationToken(checkUser, null, new ArrayList<>());
//        SecurityContextHolder.getContext().setAuthentication(authentication);

        return checkUser;
    }
}
