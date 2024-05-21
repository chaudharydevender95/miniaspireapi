package com.example.miniaspireapi.repository;

import com.example.miniaspireapi.dao.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author devenderchaudhary
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Set<Role> getRoleByNameIn(List<String> roles);
}
