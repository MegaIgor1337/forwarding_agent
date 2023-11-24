package forwarding.agent.service.impl;

import forwarding.agent.persistense.entity.Role;
import forwarding.agent.persistense.entity.RoleNameEnum;
import forwarding.agent.persistense.entity.User;
import forwarding.agent.persistense.repository.RoleRepository;
import forwarding.agent.persistense.repository.UserRepository;
import forwarding.agent.service.UserService;
import forwarding.agent.service.dto.UserResponseDto;
import forwarding.agent.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public boolean isUserExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public UserResponseDto confirmUser(Long id) {
        User user = userRepository.getReferenceById(id);
        user.getRoles().remove(roleRepository.findByRoleName(RoleNameEnum.ROLE_UNCONFIRMED_USER));
        user.getRoles().add(roleRepository.findByRoleName(RoleNameEnum.ROLE_USER));
        User updatedUser = userRepository.save(user);
        log.info("User confirmed with id {}", id);
        return userMapper.fromEntityToResponseDto(updatedUser);
    }

    @Override
    public boolean isUserExistsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public boolean isUserConfirmed(Long id) {
        return !userRepository.getReferenceById(id)
                .getRoles().contains(roleRepository
                        .findByRoleName(RoleNameEnum.ROLE_UNCONFIRMED_USER));
    }
}
