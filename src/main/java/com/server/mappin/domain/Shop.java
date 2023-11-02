package com.server.mappin.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "shop")
@NoArgsConstructor
public class Shop {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "shop_id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "point")
  private Integer point;

  @Column(name = "address")
  private String address;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne
  @JoinColumn(name = "location_id")
  private Location location;

  @Builder
  public Shop(String name, Integer point, String address, Member member, Location location) {
    this.name = name;
    this.point = point;
    this.address = address;
    this.member = member;
    this.location = location;
  }
}
