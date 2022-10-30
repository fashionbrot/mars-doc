package com.github.fashionbrot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fashionbrot.entity.TestEntity;
import com.github.fashionbrot.vo.RespVo;

import java.util.List;

public class JsonTest
{


    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        //在反序列化时忽略在 json 中存在但 Java 对象不存在的属性
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

        RespVo<List<TestEntity>> bean = new RespVo<>();

        System.out.println(mapper.writeValueAsString(bean));

    }

}
