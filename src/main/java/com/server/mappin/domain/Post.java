package com.server.mappin.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Post")
@NoArgsConstructor
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

  @Column(name = "found_date")
  private LocalDate foundDate;

  private String imageUrl;

  private Integer x;

  private Integer y;

  @ManyToOne
  @JoinColumn(name = "location_id")
  private Location location;

  @Builder

  public Post(Member member, String title, String content, LocalDate createdAt, LocalDate foundDate, String imageUrl, Integer x, Integer y, Location location) {
    this.member = member;
    this.title = title;
    this.content = content;
    this.createdAt = createdAt;
    this.foundDate = foundDate;
    this.imageUrl = imageUrl;
    this.x = x;
    this.y = y;
    this.location = location;
  }
}

