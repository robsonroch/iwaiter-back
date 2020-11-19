package br.com.robson.waiterback.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Entidade base, com atributos universais a todas as entidades
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected int id;
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	protected Date createdAt;
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	protected Date updatedAt;
}
	