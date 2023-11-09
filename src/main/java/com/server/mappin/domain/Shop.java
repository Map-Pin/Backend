package com.server.mappin.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "shop")
@NoArgsConstructor
@Getter @Setter
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

  @Column(name = "companyNumber")
  private String companyNumber;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne
  @JoinColumn(name = "location_id")
  private Location location;

  @Builder
  public Shop(String name, Integer point, String address, Member member, Location location, String companyNumber) {
    this.name = name;
    this.point = point;
    this.address = address;
    this.member = member;
    this.location = location;
    this.companyNumber = companyNumber;
  }
}
