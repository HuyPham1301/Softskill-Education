package com.example.swp490_g25_sse.service;

import com.example.swp490_g25_sse.dto.AccountInfoDto;
import com.example.swp490_g25_sse.dto.UserInfoDto;
import com.example.swp490_g25_sse.dto.UserRegistrationDto;
import com.example.swp490_g25_sse.model.Role;
import com.example.swp490_g25_sse.model.Student;
import com.example.swp490_g25_sse.model.Teacher;
import com.example.swp490_g25_sse.model.User;
import com.example.swp490_g25_sse.repository.RoleRepository;
import com.example.swp490_g25_sse.repository.StudentRepository;
import com.example.swp490_g25_sse.repository.TeacherRepository;
import com.example.swp490_g25_sse.repository.UserRepository;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


/**
 *
 * @author Admin
 */
@Service
public class UserServiceImpl implements UserService {

    final private UserRepository userRepository;
    final private RoleRepository roleRepository;
    final private TeacherRepository teacherRepository;
    final private StudentRepository studentRepository;

    @Autowired
    private Environment env;

    private BCryptPasswordEncoder passwordEncoder;
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
            TeacherRepository teacherRepository, StudentRepository studentRepository) {
        super();
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public User save(UserRegistrationDto registrationDto) {
        Role existedRole = roleRepository.findFirstByName((registrationDto.getRole()));
        Role role;
        String imgUrl = "/images/default-user-icon.jpg";

        if (existedRole != null) {
            role = existedRole;
        } else {
            role = new Role(registrationDto.getRole());
        }

        User user = new User(registrationDto.getFirstName(),
                registrationDto.getLastName(), registrationDto.getEmail(),
                passwordEncoder.encode(registrationDto.getPassword()), Arrays.asList(role), imgUrl);

        User newUser = userRepository.save(user);
        if (role.getName().equals("ROLE_TEACHER")) {
            Teacher teacher = new Teacher(user);
            teacherRepository.save(teacher);
        }

        if (role.getName().equals("ROLE_STUDENT")) {
            Student student = new Student(user);
            studentRepository.save(student);
        }

        return newUser;
    }

    @Override
    public CustomUserDetailsService loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.trace(
                "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        logger.trace(username);
        User user = userRepository.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new CustomUserDetailsService(user);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User updateInfo(UserInfoDto userInfoDto, User currentUser) {
        User userInDatabase = userRepository.findFirstByEmail((userInfoDto.getEmail()));

        if (userInDatabase != null && userInDatabase.getId() != currentUser.getId()) {
            // throw 1 cai gi day
            return currentUser;
        }

        String prefix = env.getProperty("FIREBASE_PREFIX");
        String suffix = env.getProperty("FIREBASE_SUFFIX");
        currentUser.setEmail(userInfoDto.getEmail());
        currentUser.setFirstName(userInfoDto.getFirstName());
        currentUser.setLastName(userInfoDto.getLastName());
        currentUser.setImageURL(prefix + userInfoDto.getImageURL() + suffix);

        User updatedUser = userRepository.save(currentUser);

        return updatedUser;
    }

    @Override
    public User updateAccount(AccountInfoDto accountInfoDto) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService currentUser = (CustomUserDetailsService) auth.getPrincipal();

        User currentAccount = currentUser.getUser();

        if (currentAccount.getPassword().equals(passwordEncoder.encode(accountInfoDto.getCurrentPassword()))) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    new String("Mật khẩu hiện tại không đúng".getBytes(), Charset.forName("UTF-8")));
        } else if (!accountInfoDto.getNewPassword().equals(accountInfoDto.getConfirmNewPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    new String("Mật khẩu không trùng khớp".getBytes(), Charset.forName("UTF-8")));
        }
        currentAccount.setPassword(passwordEncoder.encode(accountInfoDto.getNewPassword()));

        User updatedAccount = userRepository.save(currentAccount);
        return updatedAccount;
    }
    
    @Override
    public boolean checkIfUserExist(String email) {
        return userRepository.findByEmail(email) != null ? true : false;
    }
}
