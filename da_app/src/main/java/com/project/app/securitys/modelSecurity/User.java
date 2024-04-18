package com.project.app.securitys.modelSecurity;

import com.project.app.api.domain.Organization;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "authors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullname", length = 100 ,nullable = false )
    private String fullName;

    @Column(name = "username", length =200)
    private String username;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "phone", length = 10)
    private String phoneNumber;
    @Column(name = "email", length =200 ,nullable = false )
    private String email;

    @Column(name = "address", length = 200)
    private String address;

    @Column(name = "birthday")
    private String birthday;

    @Column(name = "password", length = 200, nullable = false)
    private String password;

    @Column(name = "is_active")
    private boolean active;

    @OneToMany
    @JoinColumn(name = "org_id") // Tên cột trong bảng TblOrg để lưu trữ ID của người dùng liên quan
    private List<Organization> organizations = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<Role> roles = new HashSet<>(); // Thay List bằng Set
        roles.add(getRole());
        return getGrantedAuthorities(getPermissions(getMenus(roles)));
    }

    private Set<Menu> getMenus(Collection<Role> roles) { // Thay List<Menu> bằng Set<Menu>
        Set<Menu> allMenus = new HashSet<>();
        for (Role role : roles) {
            allMenus.addAll(role.getMenus());
        }
        return allMenus;
    }

    private Set<String> getPermissions(Set<Menu> menus) {
        Set<String> permissions = new HashSet<>();
        for (Menu menu : menus) {
            for (Permission permission : menu.getPermissions()) {
                String combinedPermission = menu.getDescription() + "_" + permission.getName();
                permissions.add(combinedPermission);
            }
        }
        permissions.add(this.role.getName());
        return permissions;
    }


    private Set<GrantedAuthority> getGrantedAuthorities(Set<String> menus) { // Thay List<GrantedAuthority> bằng Set<GrantedAuthority>
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (String privilege : menus) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }


    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
