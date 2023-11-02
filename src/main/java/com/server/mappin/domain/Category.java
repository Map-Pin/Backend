package com.server.mappin.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "category")
@Getter
@NoArgsConstructor
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "category_id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Builder
  public Category(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}

