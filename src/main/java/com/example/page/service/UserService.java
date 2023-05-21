package com.example.page.service;

import com.example.page.model.Role;
import com.example.page.model.User;
import com.example.page.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User save(User user){
        //사용자가 전달한 비밀번호를 인코더함
        String encodePassword = passwordEncoder.encode(user.getPassword());
        //암호화된 비밀번호 set 저장
        user.setPassword(encodePassword);
        //회원가입 하면 활성화 상태
        user.setEnabled(true);

        Role role = new Role();
        role.setId(1l);
        user.getRoles().add(role);
        return userRepository.save(user);
    }

}
