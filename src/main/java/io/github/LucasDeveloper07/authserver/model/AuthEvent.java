package io.github.LucasDeveloper07.authserver.model;

import io.github.LucasDeveloper07.authserver.model.enums.EventType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.net.InetAddress;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "auth_events")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class AuthEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "attempted_email")
    private String attemptedEmail;

    @JdbcTypeCode(SqlTypes.INET) // Mapeamento para o tipo INET do SQL
    @Column(name = "ip_address")
    private InetAddress ip;

    @Column(name = "user_agent")
    private String userAgent;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private EventType eventType;

    @CreatedDate
    @Column(name = "created_at")
    private Instant createdDate;
}
