
package com.lbt.icon.demanddraft.domain.demanddraftproducttrancodelimit;

import com.lbt.icon.core.exception.FieldValidationError;
import com.lbt.icon.core.exception.FieldValidationRuntimeException;
import com.lbt.icon.core.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.Validator;
import java.util.List;

@RequiredArgsConstructor
@Component
public class DemandDraftProductTranCodeLimitValidator {

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