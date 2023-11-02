package com.server.mappin.domain;

import com.server.mappin.domain.enums.ProviderType;
import com.server.mappin.domain.enums.Role;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Lost {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "lost_id")
  private Long id;

  @Column(name = "title")
  private String title;

  @Column
  private String content;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "found_date")
  private LocalDateTime foundDate;

  private String imageUrl;

  private Integer x;

  private Integer y;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  @Builder
  public Lost(String content, LocalDateTime createdAt, LocalDateTime foundDate, String imageUrl, int x, int y) {
    this.content = content;
    this.createdAt = createdAt;
    this.foundDate = foundDate;
    this.imageUrl = imageUrl;
    this.x = x;
    this.y = y;
  }
}
