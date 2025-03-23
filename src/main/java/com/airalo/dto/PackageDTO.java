package com.airalo.dto;

import javax.money.MonetaryAmount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PackageDTO extends AbstractContextDto {
	private String coverage;
	private String data;
	private String validity;
	private MonetaryAmount price;
}