package base.pkg.entity;

import base.pkg.user.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.Builder.Default;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
        //implements UserDetails {
    
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
   
    @Column(length = 60)
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Default
    private boolean enabled = true;

//    public Long getId() {return id;}
//    public void setId(Long id) {this.id = id;}
//
//    public String getFirstName() {return firstName;}
//    public void setFirstName(String firstName) {this.firstName = firstName;}
//
//    public String getLastName() {return lastName;}
//    public void setLastName(String lastName) {this.lastName = lastName;}
//
//    public String getEmail() {return email;}
//    public void setEmail(String email) {this.email = email;}
//
//    public void setPassword(String password) {this.password = password;}
//
//    public Role getRole() {return role;}
//    public void setRole(Role role) {this.role = role;}
//
//
//    public boolean isEnabled() {return enabled;}
//    public void setEnabled(boolean enabled) {this.enabled = enabled;}

// ---- bellow
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        //return List.of(new SimpleGrantedAuthority(role.name()));
//        return role.getAuthorities();
//    }
//    @Override
//    public String getUsername() {
//        return email;
//    }
//    @Override
//    public String getPassword() {
//        return password;
//    }
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
}
