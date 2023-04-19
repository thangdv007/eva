package com.evadeeva.eva.mapper;

import com.evadeeva.eva.models.Role;
import com.evadeeva.eva.models.response.RoleResponse;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapper {
    RoleResponse mapModelToResponse(Role role);
}
