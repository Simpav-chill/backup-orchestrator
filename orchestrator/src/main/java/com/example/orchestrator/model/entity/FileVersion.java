package com.example.orchestrator.model.entity;

import com.example.orchestrator.model.enums.FileVersionStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "file_versions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FileVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "file_id")
    private File file;

    @NotNull
    @Column(name = "version_number")
    private Integer versionNumber;

    @NotBlank
    @Size(max = 255)
    @Column(name = "original_filename")
    private String originalFilename;

    @Size(max = 255)
    @Column(name = "content_type")
    private String contentType;

    @NotNull
    @Column(name = "total_size_bytes")
    private Long totalSizeBytes;

    @Size(max = 64)
    private String checksum;

    @NotNull
    @Column(name = "chunk_count")
    private Integer chunkCount;

    @NotNull
    @Size(max = 20)
    private FileVersionStatus status;

    @NotNull
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "fileVersion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chunk> chunks = new ArrayList<>();
}
