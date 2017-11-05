//package io.renren.common.xss;
//
//import org.apache.commons.io.IOUtils;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.web.reactive.function.server.ServerRequest;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.LinkedHashMap;
//import java.util.Map;
//import java.util.stream.IntStream;
//
///**
// * XSS过滤处理
// *
// * @author ChunLiang Hu
// * @email davichi2009@gmail.com
// * @date 2017-04-01 11:29
// */
//public class XssHttpRequestWrapper {
//    /**
//    /**
//     * html过滤
//     */
//    private final static HTMLFilter HTML_FILTER = new HTMLFilter();
//
//
//    public String getInputStream(ServerRequest request) throws IOException {
//
//        //非json类型，直接返回
//        if (!MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(request.headers().contentType().orElse(null).getParameter(HttpHeaders.CONTENT_TYPE))) {
//            return super.getInputStream();
//        }
//
//        //为空，直接返回
//        String json = IOUtils.toString(super.getInputStream(), "utf-8");
//        if (StringUtils.isBlank(json)) {
//            return super.getInputStream();
//        }
//
//        //xss过滤
//        return xssEncode(json);
//
//    }
//
//    public String getParameter(String name) {
//        String value = super.getParameter(xssEncode(name));
//        if (StringUtils.isNotBlank(value)) {
//            value = xssEncode(value);
//        }
//        return value;
//    }
//
//    public String[] getParameterValues(String name) {
//        String[] parameters = super.getParameterValues(name);
//        if (parameters == null || parameters.length == 0) {
//            return null;
//        }
//
//        IntStream.range(0, parameters.length).forEach(i -> parameters[i] = xssEncode(parameters[i]));
//        return parameters;
//    }
//
//    public Map<String, String[]> getParameterMap() {
//        Map<String, String[]> map = new LinkedHashMap<>();
//        Map<String, String[]> parameters = super.getParameterMap();
//        parameters.forEach((s, strings) -> Arrays.stream(strings).forEach(this::xssEncode));
//        map.putAll(parameters);
//        return map;
//    }
//
//    public String getHeader(String name) {
//        String value = super.getHeader(xssEncode(name));
//        if (StringUtils.isNotBlank(value)) {
//            value = xssEncode(value);
//        }
//        return value;
//    }
//
//    private String xssEncode(String input) {
//        return HTML_FILTER.filter(input);
//    }
//
//
//}
