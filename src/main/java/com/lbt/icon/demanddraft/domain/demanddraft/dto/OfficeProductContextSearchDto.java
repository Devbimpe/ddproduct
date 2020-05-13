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
@AllArgsConstructor(access=AccessLevel.PRIVATE)
@NoArgsConstructor(access=AccessLevel.PRIVATE)
@Builder
public class OfficeProductContextSearchDto {
	String branchCode;
	String currencyCode;
	String glSubCode;
	//String instrumentCode;
	String productCode;
	String productName;
	String productStatus;
	String spacerCode;
	String accountNoGenCode;
}
