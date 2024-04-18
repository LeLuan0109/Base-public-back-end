package com.project.app.securitys.modelSecurity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Entity
@Table(name = "menus")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Menu {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "url")
	private String url;

	@ManyToMany(mappedBy = "menus")
	private Collection<Role> roles;


	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "menus_permissions",
			joinColumns = @JoinColumn(
					name = "menu_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(
					name = "per_id", referencedColumnName = "id"))
	private Collection<Permission> permissions;

}
