package com.bestnotes.users.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(
      name = "UUID",
      strategy = "org.hibernate.id.UUIDGenerator"
  )
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @Column(name = "email", nullable = false)
  private String email;

  @CreationTimestamp
  @Column(name = "created_on", nullable = false)
  private LocalDateTime createdOn;

  @UpdateTimestamp
  @Column(name = "updated_on", nullable = false)
  private LocalDateTime updatedOn;

  public UUID getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(final String email) {
    this.email = email;
  }

  public LocalDateTime getCreatedOn() {
    return createdOn;
  }

  public LocalDateTime getUpdatedOn() {
    return updatedOn;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final User user = (User) o;

    return new EqualsBuilder()
        .append(id, user.id)
        .append(email, user.email)
        .append(createdOn, user.createdOn)
        .append(updatedOn, user.updatedOn)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(id)
        .append(email)
        .append(createdOn)
        .append(updatedOn)
        .toHashCode();
  }
}
