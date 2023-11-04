package com.server.mappin.domain;

import com.server.mappin.domain.enums.ProviderType;
import com.server.mappin.domain.enums.Role;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Getter
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
  private LocalDate foundDate;

  private String imageUrl;


  private Double x;

  private Double y;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private Category category;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @OneToOne
  @JoinColumn(name = "location_id")
  private Location location;

  @Builder
  public Lost(Long id, String title, String content, LocalDateTime createdAt, LocalDate foundDate, String imageUrl, Double x, Double y, Category category, Member member, Location location) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.createdAt = createdAt;
    this.foundDate = foundDate;
    this.imageUrl = imageUrl;
    this.x = x;
    this.y = y;
    this.category = category;
    this.member = member;
    this.location = location;
  }
}
