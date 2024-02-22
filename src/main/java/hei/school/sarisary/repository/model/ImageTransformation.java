package hei.school.sarisary.repository.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.security.Timestamp;

@Entity
@Getter
@Setter
public class ImageTransformation {
    @Id
    private String id;

    @Column(name = "transformation_timestamp")
    private Timestamp transformationTimestamp;
}