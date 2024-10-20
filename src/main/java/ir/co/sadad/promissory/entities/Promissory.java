package ir.co.sadad.promissory.entities;

import ir.co.sadad.promissory.commons.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "PROMISSORY")
public class Promissory extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "PROMISSORY_UID")
    private String promissoryUid;
    @Column(name = "PROMISSORY_NAME")
    private String promissoryName;
    @Column(name = "TRANSFERABLE", columnDefinition = "SMALLINT")
    private Boolean transferable;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "DUEDATE")
    private String dueDate;
    @Column(name = "AMOUNT")
    private Long amount;
    @Column(name = "FEE_TRANS_ID")
    private String feeTransId;
    @Column(name = "PAYMENT_DATETIME")
    private String paymentDateTime;
    @Column(name = "STAMP_TRANS_ID")
    private String stampTransId;
    @Column(name = "FEE")
    private BigDecimal fee;
    @Column(name = "STAMP_DUTY")
    private BigDecimal stampDuty;
    @Column(name = "FEE_STAMP_TOTAL")
    private BigDecimal feeAndStampTotal;
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
}
