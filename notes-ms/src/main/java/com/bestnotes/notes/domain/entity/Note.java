package com.bestnotes.notes.domain.entity;

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
@Table(name = "notes")
public class Note {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(
      name = "UUID",
      strategy = "org.hibernate.id.UUIDGenerator"
  )
  @Column(name = "id", updatable = false, nullable = false)
  private UUID id;

  @Column(name = "user_id", updatable = false, nullable = false)
  private UUID userId;

  @Column(name = "title", nullable = false, length = 50)
  private String title;

  @Column(name = "content", length = 1000)
  private String content;

  @CreationTimestamp
  @Column(name = "created_on", nullable = false)
  private LocalDateTime createdOn;

  @UpdateTimestamp
  @Column(name = "updated_on", nullable = false)
  private LocalDateTime updatedOn;

  public UUID getId() {
    return id;
  }

  public UUID getUserId() {
    return userId;
  }

  public void setUserId(final UUID userId) {
    this.userId = userId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(final String note) {
    this.content = note;
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

    final Note note1 = (Note) o;

    return new EqualsBuilder()
        .append(id, note1.id)
        .append(title, note1.title)
        .append(content, note1.content)
        .append(createdOn, note1.createdOn)
        .append(updatedOn, note1.updatedOn)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(id)
        .append(title)
        .append(content)
        .append(createdOn)
        .append(updatedOn)
        .toHashCode();
  }

}
