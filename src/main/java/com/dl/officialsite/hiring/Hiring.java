package com.dl.officialsite.hiring;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "hrie")
public class Hiring {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String headline;

    private String employer;

    private String jd;

    private String role;

    private String requirement;

    private String locate;

    private String salary;

    private String memo;

   private String contact;

}
