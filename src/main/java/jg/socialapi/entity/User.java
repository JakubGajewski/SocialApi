package jg.socialapi.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    //TODO @Length(max = 140)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Post> posts;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) //TODO na pewno nie kasujemy
    @ToString.Exclude
    private List<User> following;

}
