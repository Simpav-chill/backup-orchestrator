package com.example.orchestrator.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "chunk_replicas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChunkReplica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    @Column(name = "chunk_id")
    private Long chunkId;

    @NotNull
    @OneToMany(fetch = FetchType.LAZY)
    @Column(name = "storage_node_id")
    private Long storageNodeId;

    @NotBlank
    @Column(name = "storage_key")
    private String storageKey;

    @NotNull
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
