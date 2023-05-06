package com.shramaner.studentPortal.service;

import com.shramaner.studentPortal.doa.IStudentDAO;
import com.shramaner.studentPortal.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private IStudentDAO studentDAO;

    @Override
    public User loadUserByUsername(String username) {
        Student user = studentDAO.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

       return new User(
                String.valueOf(user.getStudentId()),
                user.getPassword(),
                Arrays.asList(new SimpleGrantedAuthority("STUDENT"))

        );
    }
}