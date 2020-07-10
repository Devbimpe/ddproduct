
package com.lbt.icon.demanddraft.domain.demanddraftproductinstr;

import com.lbt.icon.core.exception.FieldValidationError;
import com.lbt.icon.core.exception.FieldValidationRuntimeException;
import com.lbt.icon.core.util.CommonUtils;
import com.lbt.icon.demanddraft.domain.demanddraftproductinstr.dto.DemandDraftProductInstrDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.Validator;
import java.util.List;

@RequiredArgsConstructor
@Component
public class DemandDraftProductInstrValidator {

    private final Validator validator;

    public void validateFields(Object obj) {
        List<FieldValidationError> fieldValidationErrors =
                CommonUtils.getStaticFieldValidationErrors(
                        obj,
                        validator
                );

        if (fieldValidationErrors.isEmpty()) {
            return;
        }

        throw new FieldValidationRuntimeException(fieldValidationErrors);
    }


}