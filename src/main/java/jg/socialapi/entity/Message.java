package jg.socialapi.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Entity
@Data
@Accessors(chain = true)
public class Message {

    @Id
    @GeneratedValue
    private Integer id;

    @CreationTimestamp
    private Timestamp timestamp;

    private String value;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonManagedReference
    private User user;
}
