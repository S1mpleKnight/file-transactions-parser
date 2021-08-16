package by.itechart.lastcoursetask.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Operator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String nickname;
    private String password;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private Role role;
    @ToString.Exclude
    @OneToMany(mappedBy = "operator", fetch = FetchType.LAZY)
    private Set<Transaction> transactions;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operator operator = (Operator) o;
        return Objects.equals(id, operator.id) && Objects.equals(firstName, operator.firstName) && Objects.equals(lastName, operator.lastName) && Objects.equals(nickname, operator.nickname) && Objects.equals(password, operator.password) && Objects.equals(role, operator.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, nickname, password, role);
    }
}
