package com.java.example.demo.util.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor  //生成有参数构造方法
@NoArgsConstructor   //生成无参数构造
@Data
public class GuliException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private Integer code;//状态码
    private String msg;//异常信息

}
