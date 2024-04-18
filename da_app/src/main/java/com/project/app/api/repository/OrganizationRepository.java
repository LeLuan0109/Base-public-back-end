package com.project.app.api.repository;

import com.project.app.api.domain.Organization;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Integer> {



	@Query("select p from Organization as p where p.id in (:id)")
	List<Organization> filterOrganization(
		@Param("id")	List<Integer> id
	);
}
