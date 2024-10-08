package ir.co.sadad.promissory.entities;

import ir.co.sadad.promissory.commons.enums.StakeholderRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "PROMISSORY_STAKEHOLDER")
public class PromissoryStakeholder extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NATIONAL_NUMBER")
    private String nationalNumber;
    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private StakeholderRole role;
    @Column(name = "CELLPHONE")
    private String cellphone;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "POSTAL_CODE")
    private String postalCode;
    @Column(name = "FULL_NAME")
    private String fullName;
    @Column(name = "BIRTHDATE")
    private String birthdate;
    @Column(name = "PAYMENT_PLACE")
    private String paymentPlace;
    @Column(name = "ACCOUNT_NUMBER")
    private String accountNumber;

    @ManyToOne
    @JoinColumn(name = "REQUEST_ID", foreignKey = @ForeignKey(name = "FKPROMISSORY_STAKEHOLDER_TO_REQUEST"))
    private PromissoryRequest request;

}
