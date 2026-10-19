package io.github.LucasDeveloper07.authserver.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.net.InetAddress;
import java.time.Instant;

@Entity
@Table(name = "blocked_ips")
@Getter
@Setter
public class BlockedIps {

    @Id
    @JdbcTypeCode(SqlTypes.INET) // Mapeamento para o tipo INET do SQL
    @Column(name = "ip_address", nullable = false)
    private InetAddress ipAddress;

    @Column(name = "blocked_at")
    private Instant blockedDate = Instant.now();

    @Column(name = "expires_at")
    private Instant expiresDate;
}
