CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE storage_nodes (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    base_url VARCHAR(500) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    last_health_check_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
);

CREATE TABLE files (
    id BIGSERIAL PRIMARY KEY,
    owner_id BIGINT NOT NULL REFERENCES users(id),
    filename VARCHAR(255) NOT NULL,
    current_version_number INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT uq_owner_file UNIQUE (owner_id, filename)
);

CREATE TABLE file_versions (
    id BIGSERIAL PRIMARY KEY,
    file_id BIGINT NOT NULL REFERENCES files(id) ON DELETE CASCADE,
    version_number INT NOT NULL,
    original_filename VARCHAR(255) NOT NULL,
    content_type VARCHAR(255),
    total_size_bytes BIGINT NOT NULL,
    checksum VARCHAR(64),
    chunk_count INT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'UPLOADING',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT uq_file_version UNIQUE (file_id, version_number)
);

CREATE TABLE chunks (
    id BIGSERIAL PRIMARY KEY,
    file_version_id BIGINT NOT NULL REFERENCES file_versions(id) ON DELETE CASCADE,
    chunk_index INT NOT NULL,
    size_bytes BIGINT NOT NULL,
    checksum VARCHAR(64) NOT NULL,

    CONSTRAINT uq_file_version_chunk_index UNIQUE (file_version_id, chunk_index)
);

CREATE TABLE chunk_replicas (
    id BIGSERIAL PRIMARY KEY,
    chunk_id BIGINT NOT NULL REFERENCES chunks(id) ON DELETE CASCADE,
    storage_node_id BIGINT NOT NULL REFERENCES storage_nodes(id),
    storage_key VARCHAR NOT NULL DEFAULT 'STORED',
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT uq_chunk_node UNIQUE (chunk_id, storage_node_id)
);

CREATE INDEX idx_files_owner_id ON files(owner_id);
CREATE INDEX idx_file_versions_file_id_version ON file_versions(file_id, version_number);
CREATE INDEX idx_chunks_file_version_id ON chunks(file_version_id);
CREATE INDEX idx_chunks_file_version_id_index ON chunks(file_version_id, chunk_index);
CREATE INDEX idx_chunk_replicas_chunk_id ON chunk_replicas(chunk_id);
CREATE INDEX idx_chunk_replicas_chunk_id_status ON chunk_replicas(chunk_id, status);
CREATE INDEX idx_chunk_replicas_node_id ON chunk_replicas(storage_node_id);
CREATE INDEX idx_storage_nodes_status ON storage_nodes(status);