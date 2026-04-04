package com.xuanyimao.app.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.ToNumberPolicy;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * gson处理工具类
 */
public class GsonUtil {
    //处理整数被转换成double
    private final static Gson gson=new GsonBuilder().setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE).create();

    /**
     * 字符串转Map
     * @param json
     * @return
     */
    public static Map<String,Object> jsonToMap(String json){
        return gson.fromJson(json, new TypeToken<Map<String,Object>>() {}.getType());
    }

    /**
     * 字符串转List
     * @param json
     * @return
     */
    public static  <T> List<T> jsonToList(String json,Class clazz) {
        Type type = new ParameterizedTypeImpl(clazz);
        List<T> list =  new Gson().fromJson(json, type);
        return list;
    }

    private static class ParameterizedTypeImpl implements ParameterizedType {
        Class clazz;

        public ParameterizedTypeImpl(Class clz) {
            clazz = clz;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{clazz};
        }

        @Override
        public Type getRawType() {
            return List.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }

}
