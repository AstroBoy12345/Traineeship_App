package traineeship_app.domainmodel;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


@Entity
@Table(name = "users")
public class User implements UserDetails{
	
	@Id
	@Column(name="username", unique=true)
	private String username;
	
	@Column(name="password")
	private String password;
	
    @Enumerated(EnumType.STRING)
    @Column(name="role")
	private Role role;
    
    @Column(name="profileCompleted")
    private boolean profileCompleted = false;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
	     return Collections.singletonList(authority);
    }
    
    // Getter and Setter for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter and Setter for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter and Setter for role
    public Role getRole() {
        return role;
    }

    public void setRole(String role) {
    	this.role = Role.fromValue(role);
    }
    
    public boolean isProfileCompleted() {
        return profileCompleted;
    }

    public void setProfileCompleted(boolean profileCompleted) {
        this.profileCompleted = profileCompleted;
    }
    
}
