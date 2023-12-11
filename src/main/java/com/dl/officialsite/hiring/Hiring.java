package com.dl.officialsite.hiring;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity
@Table(name = "hiring")
public class Hiring {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String position;

    private String description;

    private String  location;

    private String  email;

    private String company;

    private String invoice;


    private String yearlySalary;

    private String benefits;

    private String twitter;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Date createTime;

    private String address;

}
