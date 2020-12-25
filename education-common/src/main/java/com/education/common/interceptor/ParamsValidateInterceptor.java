package com.education.common.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.education.common.annotation.Param;
import com.education.common.annotation.ParamsType;
import com.education.common.annotation.ParamsValidate;
import com.education.common.annotation.Property;
import com.education.common.interceptor.validate.Validate;
import com.education.common.interceptor.validate.ValidateManager;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.RegexUtils;
import com.education.common.utils.Result;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * controller 层参数校验拦截器
 * @author zengjintao
 * @version 1.0
 * @create_at 2018/12/22 19:31
 */
@Component
public class ParamsValidateInterceptor extends BaseInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            ParamsValidate paramsValidate = handlerMethod.getMethod().getAnnotation(ParamsValidate.class);
            if (paramsValidate != null) {
                Param[] params = paramsValidate.params();
                ParamsType paramsType = paramsValidate.paramsType();
                return checkParam(request, response, params, paramsType);
            }
        }
        return true;
    }

    private boolean checkParam(HttpServletRequest request, HttpServletResponse response, Param[] params,
                               ParamsType paramsType) {
        Map dataMap = null;
        boolean isJsonData = false;

        // Validate validate = new DefaultValidate();
        if (paramsType == ParamsType.JSON_DATA) {
            String data = readData(request);
            dataMap = JSONObject.parseObject(data);
            isJsonData = true;
        }
        for (Param param : params) {
            String name = param.name();
            Object value = null;
            if (paramsType == ParamsType.FORM_DATA) {
                value = request.getParameter(name);
            } else if (isJsonData && dataMap != null) {
                value = dataMap.get(name);
            }
           /* ValidateManager validateManager = ValidateManager.build();
            Validate validate = validateManager.getValidate(value);
            if (validate.validate()) {

            }*/

            if (ObjectUtils.isEmpty(value)) {
                renderJson(response, Result.fail(param.errorCode(), param.message()));
                return false;
            } else {
                Property[] property = param.property();
               // ValidateBuilder.build();
                // 获取字段值类型
                Class paramValueClazz = value.getClass();
                if (property != null) {

                }
            }

            String regexp = param.regexp();
            if (ObjectUtils.isNotEmpty(param.regexp())) {
                boolean regexpFlag = RegexUtils.compile(regexp, value);
                if (!regexpFlag) {
                    renderJson(response, Result.fail(param.errorCode(), param.regexpMessage()));
                    return false;
                }
            }
        }
        return true;
    }
}
