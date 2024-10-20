package ir.co.sadad.promissory.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PROMISSORY_LOG")
public class PromissoryLog extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ERROR_CLASS")
    private String errorClass;

    @Column(name = "ERROR_MESSAGE", length = 1000)
    private String errorMessage;

    @Column(name = "Request_ID")
    private String requestId;

    @Column(name = "METHOD_NAME")
    private String methodName;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "SERVICE_RESPONSE", columnDefinition = "CLOB")
    private String serviceResponse;
}

