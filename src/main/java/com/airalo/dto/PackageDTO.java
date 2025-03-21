package com.airalo.dto;

import javax.money.MonetaryAmount;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
public class PackageDTO extends AbstractContextDto {
	private final String coverage;
	private final String data;
	private final String validity;
	private final MonetaryAmount price;
}