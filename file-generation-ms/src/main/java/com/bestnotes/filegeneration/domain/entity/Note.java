package com.bestnotes.filegeneration.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
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

  @Column(name = "processing_type", nullable = false)
  @Enumerated(value = EnumType.STRING)
  private ProcessingType processingType;

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

  public ProcessingType getProcessingType() {
    return processingType;
  }

  public LocalDateTime getCreatedOn() {
    return createdOn;
  }

  public LocalDateTime getUpdatedOn() {
    return updatedOn;
  }

  public void setId(final UUID id) {
    this.id = id;
  }

  public void setUserId(final UUID userId) {
    this.userId = userId;
  }

  public void setProcessingType(final ProcessingType processingType) {
    this.processingType = processingType;
  }
}
