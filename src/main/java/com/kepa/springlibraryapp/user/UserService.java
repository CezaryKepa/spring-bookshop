package com.kepa.springlibraryapp.user;


import com.kepa.springlibraryapp.order.Order;
import com.kepa.springlibraryapp.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final String DEFAULT_ROLE = "ROLE_USER";
    private UserRepository userRepository;
    private UserRoleRepository roleRepository;
    private OrderRepository orderRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(UserRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void addWithDefaultRole(User user) {
        UserRole defaultRole = roleRepository.findByRole(DEFAULT_ROLE);
        user.getRoles().add(defaultRole);
        String passwordHash = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordHash);
        userRepository.save(user);
    }

    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<UserDto> findById(Long id) {
        return userRepository.findById(id).map(UserMapper::toDto);
    }

    public List<UserDto> findAllByNameOrAuthor(String text) {
        return userRepository.findAllByNameOrLastName(text)
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    void delete(Long userId) {
        System.out.println(userId);
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

    void update(User user,Long userId) {
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
