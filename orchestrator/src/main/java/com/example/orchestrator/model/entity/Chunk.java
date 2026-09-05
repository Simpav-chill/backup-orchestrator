package com.example.orchestrator.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chunks")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Chunk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "file_version_id")
    private FileVersion fileVersion;

    @NotNull
    @Column(name = "chunk_index")
    private Integer chunkIndex;

    @NotNull
    @Column(name = "size_bytes")
    private Long sizeBytes;

    @NotBlank
    @Size(max = 64)
    private String checksum;

    @OneToMany(mappedBy = "chunkId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChunkReplica> chunkReplicas = new ArrayList<>();
}
