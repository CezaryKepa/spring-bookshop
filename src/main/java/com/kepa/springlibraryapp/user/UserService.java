package com.kepa.springlibraryapp.user;


import com.kepa.springlibraryapp.verification.MailService;
import com.kepa.springlibraryapp.order.Order;
import com.kepa.springlibraryapp.order.OrderRepository;
import com.kepa.springlibraryapp.verification.Token;
import com.kepa.springlibraryapp.verification.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    public static final String DEFAULT_ROLE = "ROLE_USER";
    private static final String APP_URL = "http://51.68.142.7:8080/";
    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    private UserRoleRepository roleRepository;
    private OrderRepository orderRepository;
    private PasswordEncoder passwordEncoder;
    private MailService mailService;

    @Autowired
    public UserService(UserRepository userRepository,
                       TokenRepository tokenRepository,
                       UserRoleRepository roleRepository,
                       OrderRepository orderRepository,
                       PasswordEncoder passwordEncoder,
                       MailService mailService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.roleRepository = roleRepository;
        this.orderRepository = orderRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
    }

    public void addWithDefaultRole(User user) {
        Optional<User> userFind = userRepository.findByEmailOpt(user.getEmail());

        if (userFind.isPresent())
            throw new DuplicateException();

        UserRole defaultRole = roleRepository.findByRole(DEFAULT_ROLE);
        user.getRoles().add(defaultRole);
        String passwordHash = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordHash);
        userRepository.save(user);
        sendToken(user);
    }

    private void sendToken(User user) {
        String tokenValue = UUID.randomUUID().toString();
        Token token = new Token();
        token.setValue(tokenValue);
        token.setAppUser(user);
        tokenRepository.save(token);

        String url = APP_URL+"token?value=" + tokenValue;

        try {
            mailService.sendMail(user.getEmail(), "Potwierdzenie bookshop!", url, false);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto findById(Long id) {
        Optional<UserDto> userDto = userRepository.findById(id).map(UserMapper::toDto);
        return userDto.orElseThrow(UserNotFoundException::new);
    }

    public List<UserDto> findAllByNameOrAuthor(String text) {
        return userRepository.findAllByNameOrLastName(text)
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    void save(User user) {
        userRepository.save(user);
    }

    void delete(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        User userEntity = user.orElseThrow(UserNotFoundException::new);

        List<Order> orders = userEntity.getOrders();

        if (!orders.isEmpty()) {
            orders.forEach(order -> {
                orderRepository.deleteById(order.getId());
            });
        }

        userRepository.deleteById(userId);
    }

    void update(User user, Long userId) {
        Optional<User> userById = userRepository.findById(userId);

        User userEntity = userById.orElseThrow(UserNotFoundException::new);

        userEntity.setEmail(user.getEmail());
        userEntity.setFirstname(user.getFirstname());
        userEntity.setLastname(user.getLastname());
        String passwordHash = passwordEncoder.encode(user.getPassword());
        userEntity.setPassword(passwordHash);

        userRepository.save(userEntity);
    }
}
