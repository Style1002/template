package com.gouge.template.util;

import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author GouGe
 * @data 2019/6/5 15:44
 */
public class JsonUtils {

    /**
     * 字符串转成对象
     *
     * @param jsonString
     * @param cls
     * @return
     */
    public static <T> T gsonFromJson(String jsonString, Class<T> cls) {
        Gson gson = new GsonBuilder().registerTypeAdapter(new TypeToken<Map>() {
        }.getType(), new MapTypeAdapter()).registerTypeAdapter(new TypeToken<List>() {
        }.getType(), new MapTypeAdapter()).create();
        return gson.fromJson(jsonString, cls);
    }

    /**
     * 转成字符串
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String gsonToJson(T obj) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
                    @Override
                    public JsonElement serialize(Date src, Type type, JsonSerializationContext context) {
                        String format = "yyyy-MM-dd HH:mm:ss";
                        long time = ((Date) src).getTime();
                        SimpleDateFormat sdf = new SimpleDateFormat(format);
                        return new JsonPrimitive(sdf.format(time));
                    }
                }).create();
        return gson.toJson(obj);
    }

    public static class MapTypeAdapter extends TypeAdapter<Object> {
        @Override
        public Object read(JsonReader in) throws IOException {
            JsonToken token = in.peek();
            switch (token) {
                case BEGIN_ARRAY:
                    List<Object> list = new ArrayList<Object>();
                    in.beginArray();
                    while (in.hasNext()) {
                        list.add(read(in));
                    }
                    in.endArray();
                    return list;

                case BEGIN_OBJECT:
                    Map<String, Object> map = new LinkedTreeMap<String, Object>();
                    in.beginObject();
                    while (in.hasNext()) {
                        map.put(in.nextName(), read(in));
                    }
                    in.endObject();
                    return map;

                case STRING:
                    return in.nextString();

                case NUMBER:
                    String temp = in.nextString();
                    BigDecimal dbNum = new BigDecimal(temp);
                    BigDecimal maxLong = new BigDecimal(Long.MAX_VALUE);
                    BigDecimal maxInteger = new BigDecimal(Integer.MAX_VALUE);
                    // 数字超过long的最大值，返回BigDecimal类型
                    if (dbNum.compareTo(maxLong) == 1) {
                        return dbNum;
                    } else if (dbNum.compareTo(maxInteger) == 1) {
                        long lngNum = Long.parseLong(temp);
                        return lngNum;
                    } else {
                        int lngNum = Integer.parseInt(temp);
                        return lngNum;
                    }

                case BOOLEAN:
                    return in.nextBoolean();

                case NULL:
                    in.nextNull();
                    return null;

                default:
                    throw new IllegalStateException();
            }
        }

        @Override
        public void write(JsonWriter out, Object value) throws IOException {
            // 序列化无需实现
        }

    }
}
