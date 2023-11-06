package com.server.mappin.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Post")
@NoArgsConstructor
@Getter
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "post_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  @Column(name = "title")
  private String title;

  @Column(name = "content")
  private String content;

  @Column(name = "created_at")
  private LocalDate createdAt;

  @Column(name = "lost_date")
  private LocalDate lostDate;

  private String imageUrl;

  private Double x;

  private Double y;

  @ManyToOne
  @JoinColumn(name = "location_id")
  private Location location;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @Builder
  public Post(Member member, String title, String content, LocalDate createdAt, LocalDate lostDate, String imageUrl, Double x, Double y, Location location, Category category) {
    this.member = member;
    this.title = title;
    this.content = content;
    this.createdAt = createdAt;
    this.lostDate = lostDate;
    this.imageUrl = imageUrl;
    this.x = x;
    this.y = y;
    this.location = location;
    this.category = category;
  }
}

