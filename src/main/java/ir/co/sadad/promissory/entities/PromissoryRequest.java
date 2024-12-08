package ir.co.sadad.promissory.entities;

import ir.co.sadad.promissory.commons.enums.RequestStatus;
import ir.co.sadad.promissory.commons.enums.RequestType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "PROMISSORY_REQUEST")
public class PromissoryRequest extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "REQUEST_UID")
    private String requestUid;
    @Column(name = "REQUEST_TYPE")
    @Enumerated(EnumType.STRING)
    private RequestType requestType;
    @Column(name = "REQUEST_DATE")
    private String requestDate;
    @Column(name = "REQUEST_STATUS")
    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    @ManyToOne
    @JoinColumn(name = "PROMISSORY_ID", foreignKey = @ForeignKey(name = "FKPROMISSORY_REQUEST_TO_PROMISSORY"))
    private Promissory promissory;
}
