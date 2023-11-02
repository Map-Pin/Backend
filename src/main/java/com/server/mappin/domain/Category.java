package com.server.mappin.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "category")
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "category_id")
  private Long id;

  @Column(name = "name")
  private String name;



}

