package com.thalasoft.user.data.jpa.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class AbstractEntity {

  @Id
  // Compatible with H2, MySQL, Postgresql, Oracle
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_generator")
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

  @Version
  @Column(nullable = false)
  private int version;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getVersion() {
    return this.version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }

    if (this.id == null || object == null || !(this.getClass().equals(object.getClass()))) {
      return false;
    }

    AbstractEntity that = (AbstractEntity) object;

    return this.id.equals(that.getId());
  }

  @Override
  public int hashCode() {
    return id == null ? 0 : id.hashCode();
  }

}
