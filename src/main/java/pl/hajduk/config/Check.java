package pl.hajduk.config;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.util.List;

public class Check
    {
    Boolean hasPermission;
    Boolean hasRole;
    
    public Boolean hasPermission(Member member, Permission permission)
        {
        return member.hasPermission(permission);
        }
    
    public Boolean hasRole(Member member,String roleId)
        {
        List<Role>roles =member.getRoles();
        for (Role r:roles)
            {
            if (r.getId().equals(roleId))
                return true;
            }
        
        return false;
        }

    }
