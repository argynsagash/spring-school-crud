package com.github.jusan.school.auth;

import com.github.jusan.school.user.Student;
import com.github.jusan.school.user.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Student student = this.studentRepository.findStudentByEmail(email);
        if (student == null) {
            throw new UsernameNotFoundException("email " + email + " is not found");
        }
        return new CustomUserDetails(student);
    }

    static final class CustomUserDetails extends Student implements UserDetails {
        private static final List<GrantedAuthority> ROLE_USER = Collections
                .unmodifiableList(AuthorityUtils.createAuthorityList("ROLE_USER"));

        CustomUserDetails(Student student) {
            super(student.getId(), student.getFirstName(), student.getLastName(), student.getPassword(),
                    student.getEmail());
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return ROLE_USER;
        }

        @Override
        public String getUsername() {
            return getEmail();
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
}
