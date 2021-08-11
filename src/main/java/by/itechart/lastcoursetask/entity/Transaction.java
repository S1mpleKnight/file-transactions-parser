package by.itechart.lastcoursetask.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@NoArgsConstructor
public class Transaction {
    @Id
    private UUID id;
    private Boolean status;
    private BigDecimal amount;
    private String currency;
    private UUID customerId;
    private String customerFirstName;
    private String customerLastName;
    private String customerEmail;
    private LocalDateTime dateTime;
    @ManyToOne
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) && Objects.equals(status, that.status) && Objects.equals(amount, that.amount) && Objects.equals(currency, that.currency) && Objects.equals(customerId, that.customerId) && Objects.equals(customerFirstName, that.customerFirstName) && Objects.equals(customerLastName, that.customerLastName) && Objects.equals(customerEmail, that.customerEmail) && Objects.equals(dateTime, that.dateTime) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, amount, currency, customerId, customerFirstName, customerLastName, customerEmail, dateTime, user);
    }
}
