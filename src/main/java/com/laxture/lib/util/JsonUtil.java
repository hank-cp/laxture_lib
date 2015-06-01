package com.laxture.lib.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Type;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class JsonUtil {

    public static final String SKIP_FIELDS = "SKIP_FIELDS";
    public static final String FIELDS_NAMING = "FIELDS_NAMING";

    private static final Gson GSON = new GsonBuilder()
            // skip static fields
            .excludeFieldsWithModifiers(java.lang.reflect.Modifier.STATIC)
            // skip fields annotated with @SkipJson
            .setExclusionStrategies(new SkipFieldExclusionStrategy())
            .registerTypeAdapter(Date.class, new DateTypeAdapter())
            .registerTypeAdapter(DateTime.class, new DateTimeTypeAdapter())
            .registerTypeAdapter(Boolean.class, new BooleanTypeAdapter())
            .registerTypeAdapter(boolean.class, new BooleanTypeAdapter()).create();

    /**
     * Util method for {@link Gson#toJson(Object, Type)}}
     */
    public static <T> String toJson(T obj, Class<?> clazz) {
        try {
            return GSON.toJson(obj);
        } catch (Exception e) {
            LLog.e("Resolve Json Failed. Reason: %s", e, e.getMessage());
            return null;
        }
    }

    /**
     * Util method for {@link Gson#fromJson(String, Class)}
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromJson(String string, Class<T> clazz) {
        try {
            return (T) GSON.fromJson(string, clazz);
        } catch (Exception e) {
            LLog.e("Resolve Json Failed. Reason: %s", e, e.getMessage());
            return null;
        }
    }

    /**
     * Util method for {@link Gson#fromJson(String, Type)}
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromJson(String string, Type type) {
        try {
            return (T) GSON.fromJson(string, type);
        } catch (Exception e) {
            LLog.e("Resolve Json Failed. Reason: %s", e, e.getMessage());
            return null;
        }
    }

    /**
     * Util method for {@link Gson#fromJson(JsonElement, Type)}}
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromJson(JsonElement json, Class<T> type){
        try {
            return (T) GSON.fromJson(json, type);
        } catch (Exception e) {
            LLog.e("Resolve Json Failed. Reason: %s", e, e.getMessage());
            return null;
        }
    }

    //***********************Custom Date Serializer****************************

    private static final class BooleanTypeAdapter implements JsonSerializer<Boolean>,
                                                             JsonDeserializer<Boolean> {

        @Override
        public JsonElement serialize(Boolean src, Type typeOfSrc,
                JsonSerializationContext context) {
            return new JsonPrimitive(src);
        }

        @Override
        public Boolean deserialize(JsonElement json, Type typeOfT,
                JsonDeserializationContext context) throws JsonParseException {
            if (json.getAsString().equals("true")) return true;
            if (json.getAsString().equals("false")) return false;
            if (json.getAsInt() == 1) return true;
            if (json.getAsInt() == 0) return false;
            return json.getAsBoolean();
        }
    }

    private static final class DateTypeAdapter implements JsonSerializer<Date>,
                                                          JsonDeserializer<Date> {

        @Override
        public JsonElement serialize(Date src, Type typeOfSrc,
                JsonSerializationContext context) {
            return new JsonPrimitive(src.getTime()/1000);
        }

        @Override
        public Date deserialize(JsonElement json, Type typeOfT,
                JsonDeserializationContext context) throws JsonParseException {
            if (!(json instanceof JsonPrimitive)) {
                throw new JsonParseException(
                        "The date should be a string value");
            }
            String str = json.getAsString();
            if (Checker.isEmpty(str)) return null;
            long dateLong = (str.indexOf(".") > 0)
                    ? Long.parseLong(str.substring(0, str.indexOf(".")))
                    : json.getAsLong();
            return new Date(dateLong*1000);
        }
    }

    private static final class DateTimeTypeAdapter implements JsonSerializer<DateTime>,
                                                              JsonDeserializer<DateTime> {

        @Override
        public JsonElement serialize(DateTime src, Type typeOfSrc,
                JsonSerializationContext context) {
            return new JsonPrimitive(src.getMillis() / 1000);
        }

        @Override
        public DateTime deserialize(JsonElement json, Type typeOfT,
                JsonDeserializationContext context) throws JsonParseException {
            if (!(json instanceof JsonPrimitive)) {
                throw new JsonParseException(
                        "The date should be a string value");
            }
            String str = json.getAsString();
            if (Checker.isEmpty(str))
                return null;
            Long dateLong = null;
            try {
                dateLong = (str.indexOf(".") > 0) ? Long.parseLong(str
                        .substring(0, str.indexOf("."))) : json.getAsLong();
            } catch (NumberFormatException e) {}

            if (dateLong != null) return new DateTime(dateLong * 1000);
            else
                try {
                    return new DateTime(str);
                } catch (IllegalArgumentException e) {
                    LLog.d("Failed to resolve date by default format");
                    // 素材管理系统的日期格式
                    DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:SS");
                    return dtf.parseDateTime(str);
                }
        }
    }

    //*************************Exclusion Strategy******************************

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.TYPE})
    public @interface SkipJson {}

    private static class SkipFieldExclusionStrategy implements ExclusionStrategy {

        public boolean shouldSkipClass(Class<?> clazz) {
            return clazz.getAnnotation(SkipJson.class) != null;
        }

        public boolean shouldSkipField(FieldAttributes field) {
            return field.getAnnotation(SkipJson.class) != null;
        }
    }

    //*****************************Util Methods********************************

    public static boolean isEmptyJSONObject(JSONObject json) {
        return json == null || json.length() == 0;
    }

    public static boolean isEmptyJSONArray(JSONArray json) {
        return json == null || json.length() == 0;
    }

    /**
     * key:"value1,value2,value3"
     * ->
     * key:["value1","value2","value3"]
     *
     * @param json
     * @param keyword
     * @return
     */
    public static String wrapBracket(String json, String keyword) {
        StringBuilder sb = new StringBuilder(json);
        keyword = '"'+keyword+'"'+':';
        int startIndex = 0;

        while (sb.indexOf(keyword, startIndex) > 0) {

            startIndex = sb.indexOf(keyword, startIndex)+keyword.length();
            int endIndex = sb.indexOf("\"", startIndex+1)+1;

            // wrap barcket
            sb.insert(startIndex++, "[");
            sb.insert(++endIndex, "]");

            // wrap quote to items
            for (int i=startIndex; i<=endIndex; i++) {
                if (sb.charAt(i) != ',') continue;
                sb.insert(i, "\"");
                sb.insert(i+2, "\"");
                endIndex += 2;
                i += 2;
            }

            // remove last empty item
            if (sb.charAt(endIndex-2) == '"') sb.delete(endIndex-4, endIndex-1);

        }

        return sb.toString();
    }
}
