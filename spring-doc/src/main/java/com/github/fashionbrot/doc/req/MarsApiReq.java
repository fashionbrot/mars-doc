package com.github.fashionbrot.doc.req;

import lombok.Data;

@Data
public class MarsApiReq {

    private String username;

    private String password;

    private Integer rememberDay;
}
