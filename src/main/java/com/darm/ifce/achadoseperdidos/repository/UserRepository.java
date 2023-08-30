package com.darm.ifce.achadoseperdidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.darm.ifce.achadoseperdidos.model.User;
import com.darm.ifce.achadoseperdidos.model.enums.PermissionTypeEnum;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);

    List<User> findByPermission(PermissionTypeEnum role);

    Boolean existsByLogin(String email);

    void deleteByPersonId(Long id);

    Optional<User> findByPersonId(Long id);

    Optional<User> findByVerificationCode(String code);

}
