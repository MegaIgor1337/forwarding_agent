package forwarding.agent.util;

import forwarding.agent.persistense.entity.Role;
import forwarding.agent.persistense.entity.RoleNameEnum;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RoleTestData {
    public static Role createRole() {
        return Role.builder()
                .id(1L)
                .roleName(RoleNameEnum.ROLE_USER)
                .build();
    }

    public static Role craeteUncomfiredRole() {
        return Role.builder()
                .id(1L)
                .roleName(RoleNameEnum.ROLE_UNCONFIRMED_USER)
                .build();
    }
}
