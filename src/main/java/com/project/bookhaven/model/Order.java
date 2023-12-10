package com.project.bookhaven.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Data
@SQLDelete(sql = "UPDATE orders SET is_deleted = TRUE WHERE id = ?")
@Where(clause = "is_deleted=false")
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @NotNull
    private BigDecimal total;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "shipping_address", nullable = false)
    private String shippingAddress;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<OrderItem> orderItems = new HashSet<>();

    @Column(nullable = false)
    private boolean isDeleted = false;

    public enum OrderStatus {
        NEW,
        PROCESSING,
        PENDING_PAYMENT,
        PAID,
        SHIPPED,
        COMPLETED,
        CANCELLED,
        REJECTED,
        REFUNDED;

        public static Optional<OrderStatus> fromValue(String value) {
            for (OrderStatus status : OrderStatus.values()) {
                if (status.name().equalsIgnoreCase(value)) {
                    return Optional.of(status);
                }
            }
            return Optional.empty();
        }
    }
}
