package edu.whu.demo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@JsonInclude(JsonInclude.Include.NON_NULL) //不序列化为null的字段
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T>{
    T data;
    String error;
}
