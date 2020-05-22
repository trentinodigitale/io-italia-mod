package it.tndigit.iot.domain.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@EntityListeners({AuditingEntityListener.class})
@MappedSuperclass
@Getter
@Setter
public abstract class DatePO extends CommonPO {

	private static final long serialVersionUID = 3423266755397048452L;
	
	@CreatedDate
	@Column(name = "DATAINSERIMENTO", insertable = true, updatable = false)
	private LocalDateTime dataInserimento;

	@CreatedBy
	@Column(name = "UTENTEINSERIMENTO", insertable = true, updatable = false, length = 100, nullable = false)
	private String utenteInserimento;

	@LastModifiedDate
	@Column(name = "DATAMODIFICA")
	private LocalDateTime dataModifica;

	@LastModifiedBy
	@Column(name = "UTENTEMODIFICA", length = 100)
	private String utenteModifica;

	@Column(name = "VERSION")
	@Version
	@NotNull
	private Integer version;

}
