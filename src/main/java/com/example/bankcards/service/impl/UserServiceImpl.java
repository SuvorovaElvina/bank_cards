package com.example.bankcards.service.impl;

import com.example.bankcards.dto.UserDto;
import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.NotFoundException;
import com.example.bankcards.exception.ValidationException;
import com.example.bankcards.repository.RoleRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.UserService;
import com.example.bankcards.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper mapper;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public UserDto registry(UserDto userDto) {
        log.debug(String.format("Регистрация пользователя %s", userDto));
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        roles.add(roleUser);

        User user = mapper.toUser(userDto);
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setRoles(roles);
        User userNew = userRepository.save(user);
        return mapper.toUserDto(userNew);
    }

    @Override
    public User findByLogin(String login) {
        log.debug(String.format("Получить пользователя по логину %s", login));
        return userRepository.findByLogin(login);
    }

    @Override
    public List<UserDto> getAllById(List<Long> usersId, Integer from, Integer size) {
        log.debug(String.format("Найти пользователей с id %s, постраничный вывод с %d страницы с размером %d", usersId, from, size));
        int pageNumber = (int) Math.ceil((double) from / size);
        Page<User> users;
        if (usersId == null) {
            users = userRepository.findAll(PageRequest.of(pageNumber, size));
        } else {
            users = userRepository.findAllByIdIn(usersId, PageRequest.of(pageNumber, size));
        }
        return users.stream().map(mapper::toUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDto updateById(Long userId, UserDto userDto) {
        log.debug(String.format("Изменить пользователя с id %d на %s", userId, userDto));
        Optional<User> userOld = userRepository.findById(userId);
        User user = userOld.orElseThrow(() -> new NotFoundException(String.format("Пользователь с id %d - не существует.", userId)));
        ;

        if (user.getEmail().equals(userDto.getEmail()) && user.getLogin().equals(userDto.getLogin())) {
            if (!userDto.getName().isEmpty()) {
                user.setName(userDto.getName());
            } else {
                throw new ValidationException("Вы не можете зарегистрировать пустое имя");
            }
        }
        return mapper.toUserDto(userRepository.save(user));
    }

    @Override
    public List<UserDto> update(List<Long> userId) {
        log.debug(String.format("Изменить пользователей %s", userId));
        List<User> users = userRepository.findAllById(userId);
        if (users.isEmpty()) {
            throw new NotFoundException("Указанные пользователи не найдены, проверьте корректность ids");
        }
        List<User> update = users.stream().peek(user -> user.setName("Пользователь" + user.getId())).toList();
        return userRepository.saveAll(update).stream().map(mapper::toUserDto).toList();
    }

    @Override
    public void delete(Long userId) {
        log.debug(String.format("Удалить пользователя с id %d", userId));
        userRepository.deleteById(userId);
    }

    @Override
    public void deleteAllUserById(List<Long> usersId) {
        log.debug(String.format("Удалить пользователей с id %s", usersId));
        userRepository.deleteAllById(usersId);
    }

    @Override
    public User getUser(Long id) {
        log.debug(String.format("Получить пользователя %d", id));
        Optional<User> optional = userRepository.findById(id);
        return optional.orElseThrow(() -> new NotFoundException(String.format("Пользователь с id %d - не существует.", id)));
    }

}
