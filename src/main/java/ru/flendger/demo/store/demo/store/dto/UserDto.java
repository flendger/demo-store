package ru.flendger.demo.store.demo.store.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.flendger.demo.store.demo.store.model.Role;
import ru.flendger.demo.store.demo.store.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
public class UserDto {
    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    public UserDto(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
