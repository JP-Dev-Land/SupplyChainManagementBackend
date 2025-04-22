package com.jpdevland.supply_chain_mgmt.backend.model;

import com.jpdevland.supply_chain_mgmt.backend.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "support_tickets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false, of = "id")
@ToString(exclude = {"customer", "responses"}) // Avoid recursion
public class SupportTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ON DELETE SET NULL is handled by the database.
    // JPA needs nullable=true here to handle cases where the user might be deleted.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = true) // Nullable reflects ON DELETE SET NULL
    private User customer;

    @Column(length = 255, nullable = false)
    private String subject;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private TicketStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMPTZ DEFAULT NOW()")
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMPTZ DEFAULT NOW()")
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<TicketResponse> responses = new HashSet<>();

    // Helper methods for bidirectional relationship management (optional)
    public void addResponse(TicketResponse response) {
        responses.add(response);
        response.setTicket(this);
    }

    public void removeResponse(TicketResponse response) {
        responses.remove(response);
        response.setTicket(null);
    }
}