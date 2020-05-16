package jg.socialapi.entity;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;
import javax.persistence.ManyToOne;


@Entity
@Data
public class Post {

    @Id
    @GeneratedValue
    private Integer id;

    //@CreationTimestamp
    //private Timestamp timestamp;

    //@NotNull
    //TODO @Length(max = 140)
    private String message;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL) //TODO zmien na bardziej granularne
    private User user;
}
