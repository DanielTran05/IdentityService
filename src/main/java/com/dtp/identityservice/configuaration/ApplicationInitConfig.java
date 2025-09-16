package com.dtp.identityservice.configuaration;

import com.dtp.identityservice.entity.User;
import com.dtp.identityservice.repository.UserRepository;
import enums.Role;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
@RequiredArgsConstructor
@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
              if(userRepository.findByUsername("admin").isEmpty()){
                  var roles = new HashSet<String>();
                  roles.add(Role.ADMIN.name());

                  User user = User.builder()
                          .username("admin")
                          .password(passwordEncoder.encode("admin"))
                          //.roles(roles)
                          .build();
                  userRepository.save(user);
                  log.warn("Admin has been created, please change password!");
              }
        };
    }
}
