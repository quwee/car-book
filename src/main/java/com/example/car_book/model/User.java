package com.example.car_book.model;

import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Size(min = 3)
    @Column(name = "username")
    private String username;

    @NotBlank
    @Size(min = 4)
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Email(message = "Invalid email")
    @Column(name = "email")
    private String email;

    @CreditCardNumber(message = "Invalid credit card number")
    @Column(name = "cc_number")
    private String ccNumber;

    @Pattern(regexp = "^(0[1-9]|1[0-2])/([0-9]{2})$", message = "Must be formatted MM/YY")
    @Column(name = "cc_expiration")
    private String ccExpiration;

    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    @Column(name = "cc_cvv")
    private String ccCvv;

    @OneToOne(mappedBy = "user")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private Ride order;

    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getPrincipal() instanceof User;
    }

    public static boolean isAdmin() {
        Optional<User> userOptional = getCurrentUser();
        return userOptional.map(user -> user.role.equals("ROLE_ADMIN")).orElse(false);
    }

    public static Optional<User> getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal instanceof User ? Optional.of((User) principal) : Optional.empty();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
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
