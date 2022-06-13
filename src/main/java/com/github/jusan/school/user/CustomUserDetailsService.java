package com.github.jusan.school.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service // 1. Добавляем аннотацию @Service, чтобы Spring Boot автоматически встроил нашу реализацию.
public class CustomUserDetailsService implements UserDetailsService {
    private StudentRepository studentRepository; // 2. Добавляем указатель на CRUD репозиторий.

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException { // 3. Функция, будет принимать email из Header параметра Authorization.
        Student student = this.studentRepository.findStudentByEmail(email);
        if (student == null) {
            throw new UsernameNotFoundException("email " + email + " is not found");
        }
        return new CustomUserDetails(student);
    }
}
