package com.lbt.icon.demanddraft.config;

import com.lbt.icon.base.security.domain.role.rolepermissions.permission.Permission;
import com.lbt.icon.base.security.domain.role.rolepermissions.permission.PermissionProvider;
import com.lbt.icon.base.security.domain.role.rolepermissions.permission.dto.CreatePermissionDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class DDProductPermissionProviderImpl implements PermissionProvider {

  private final ModelMapper modelMapper;

  @Override
  public Set<CreatePermissionDto> provide() {
    return getPermissions();
  }

  private Set<CreatePermissionDto> getPermissions() {
    Set<CreatePermissionDto> createPermission = new HashSet<>();
    for (DDProductPermissionEnum permissionEnum: DDProductPermissionEnum.values()){
      createPermission.add(modelMapper.map(new Permission(permissionEnum), CreatePermissionDto.class));
    }

    return createPermission;
  }
}
