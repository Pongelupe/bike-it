package br.com.pongelupe.bikeit.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Embeddable id for SearchItemXSegment
 * 
 * @author pongelupe
 *
 */

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchItemXSegmentId implements Serializable {

	private static final long serialVersionUID = -6282893423944369251L;

	private int idSearchItem;
	private int idSegment;
}