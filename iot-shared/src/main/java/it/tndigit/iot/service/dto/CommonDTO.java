package it.tndigit.iot.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public abstract class CommonDTO implements Serializable {

	/**
	 * 
	 */

	protected Long idObj;

	public CommonDTO() {
	}

	public CommonDTO(Long idObj) {
		this.idObj = idObj;
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	protected Integer version;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String erroreImprevisto;

	@JsonIgnore
	private LocalDateTime dataModifica;


	@JsonIgnore
	private LocalDateTime dataInserimento;





}


