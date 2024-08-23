package com.study.practice_springSecurity_app.JwtService;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.study.practice_springSecurity_app.Entity.UserData;
import com.study.practice_springSecurity_app.Repo.UserRepo;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println("got into loadUserByUsername method");
		UserData userData = userRepo.findByEmail(email);
		if(userData == null) {
			throw new UsernameNotFoundException("User not found with email: " + email);
		}
//		return User.builder()
//				.username(userData.getEmail())
//				.password(userData.getPassword())
//				.roles(userData.getRole().name())
//				.build();
	return new CustomUserDetails(userData);
	}
}





 class CustomUserDetails implements UserDetails {

   
	private static final long serialVersionUID = 1L;
	private UserData user;

    public CustomUserDetails(UserData user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

