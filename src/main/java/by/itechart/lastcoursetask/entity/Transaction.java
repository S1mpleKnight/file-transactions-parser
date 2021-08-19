package by.itechart.lastcoursetask.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@NoArgsConstructor
@Table(name = "transactions")
public class Transaction {
    @Id
    @Column(name = "id")
    @Type(type = "uuid-char")
    private UUID id;
    private Boolean status;
    private BigDecimal amount;
    private String currency;
    @Column(name = "customer_id")
    @Type(type = "uuid-char")
    private UUID customerId;
    private LocalDateTime dateTime;
    @ManyToOne
    private Operator operator;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) && Objects.equals(status, that.status) && Objects.equals(amount, that.amount) && Objects.equals(currency, that.currency) && Objects.equals(customerId, that.customerId) && Objects.equals(dateTime, that.dateTime) && Objects.equals(operator, that.operator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, amount, currency, customerId, dateTime, operator);
    }
}
