package com.openclassrooms.KatzenheimLibrariesApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.KatzenheimLibrariesApp.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
