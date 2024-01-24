package com.blog.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "blog_post")
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_title",nullable = false)
    private String title;


    private String content;

    private String imageName;

    @CreationTimestamp
    @Column(name = "created_date")
    private Date addDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    private User users;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<Comment> comment=new ArrayList<>();


}
