package com.example.persimmoncocktails.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:var/general.properties")
public class PersonRolesConfig {

    public static Integer clientRoleId;

    @Value("${role_authorized_id}")
    public void setClientRoleId(Integer roleId){
        PersonRolesConfig.clientRoleId = roleId;
    }

    public static Integer moderatorRoleId;

    @Value("${role_moderator_id}")
    public void setModeratorRoleId(Integer roleId){
        PersonRolesConfig.moderatorRoleId = roleId;
    }

    public static Integer adminRoleId;

    @Value("${role_admin_id}")
    public void setAdminRoleId(Integer roleId){
        PersonRolesConfig.adminRoleId = roleId;
    }
}
