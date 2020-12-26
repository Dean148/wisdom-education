package com.education.common.interceptor.validate;

import com.education.common.annotation.Param;
import com.education.common.annotation.Property;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/26 9:49
 */
public class PropertyValidate extends AbstractValidate {

    @Override
    public boolean supportParamType(Object paramValue) {
        return false;
    }

    @Override
    public void validateParam(HttpServletRequest request, HttpServletResponse response,
                              Integer errorCode, String errorMsg, Object paramValue) {
        Map params = (Map) paramValue;
        Param param = this.getParam();
        Property[] propertyArray = param.property();
        if (propertyArray != null) {
            for (Property property: propertyArray ) {
                String name = property.name();
                List<Validate> validateList = ValidateManager.build().getValidateList();
                Object value = params.get(name);
                for (Validate validate: validateList) {
                    if (validate.supportParamType(value)) {
                        validate.setParam(param);
                        validate.validateParam(request, response, param.errorCode(), param.message(), value);
                    }
                }
            }
        }
    }
}
