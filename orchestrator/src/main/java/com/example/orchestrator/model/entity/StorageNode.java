package com.example.orchestrator.model.entity;

import com.example.orchestrator.model.enums.StorageNodeStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "storage_nodes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StorageNode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotBlank
    @Size(max = 255)
    @Column(name = "base_url")
    private String baseUrl;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private StorageNodeStatus status;

    @Column(name = "last_health_check_at")
    private LocalDateTime lastHealthCheckAt;

    @CreatedDate
    @NotNull
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @NotNull
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "storageNodeId", cascade = CascadeType.ALL)
    private List<ChunkReplica> chunkReplicas = new ArrayList<>();
}
