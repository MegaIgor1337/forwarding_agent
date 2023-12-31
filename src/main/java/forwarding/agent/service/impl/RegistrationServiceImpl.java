package forwarding.agent.service.impl;

import forwarding.agent.persistense.entity.User;
import forwarding.agent.persistense.repository.UserRepository;
import forwarding.agent.service.RegistrationService;
import forwarding.agent.service.dto.AuthenticationUserDto;
import forwarding.agent.service.dto.RegistrationRequestDto;
import forwarding.agent.service.mapper.AuthenticationUserMapper;
import forwarding.agent.service.mapper.RegistrationUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final UserRepository userRepository;
    private final AuthenticationUserMapper authenticationUserMapper;
    private final RegistrationUserMapper registrationMapper;
    @Override
    @Transactional
    public AuthenticationUserDto registrationUser(RegistrationRequestDto requestDto) {
        User user = registrationMapper.fromRequestDtoToEntity(requestDto);
        User savedUser = userRepository.save(user);
        log.info("User successfully passed registration {}", savedUser.getEmail());
        return authenticationUserMapper.fromEntityToAuthenticationDto(savedUser);
    }
}
