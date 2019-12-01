package com.lbt.icon.demanddraft.domain.demanddraft.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor(access=AccessLevel.PUBLIC)
@NoArgsConstructor(access=AccessLevel.PUBLIC)
@Builder
public class DemandDraftProductCurrencyDto {
	String currencyCode;
	String currencyName;
}
