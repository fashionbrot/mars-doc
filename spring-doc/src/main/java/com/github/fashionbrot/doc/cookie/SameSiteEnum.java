package com.github.fashionbrot.doc.cookie;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SameSiteEnum {
    //仅允许一方请求携带Cookie，即浏览器将只发送相同站点请求的Cookie，即当前网页URL与请求目标URL完全一致，浏览器默认该模式。
    STRICT("Strict"),
    //允许部分第三方请求携带Cookie
    LAX("Lax"),
    //无论是否跨站都会发送Cookie
    NONE("");

    private String sameSite;

}