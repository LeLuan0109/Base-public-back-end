package com.project.app.securitys.repositorieSecurity;

import com.project.app.securitys.modelSecurity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface RoleRepository extends JpaRepository<Role, Long> {

	@Query("SELECT m.url FROM Menu m JOIN m.roles r WHERE r.id = :id")
	List<String> filterUrl(
			@Param("id") Long id
	);
}
