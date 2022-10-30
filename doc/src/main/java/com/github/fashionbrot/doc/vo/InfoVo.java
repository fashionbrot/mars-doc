package com.github.fashionbrot.doc.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InfoVo implements Serializable {

    private static final long serialVersionUID = -4971519068458200311L;

    private String baseUrl;

    private String version;

    private String description;

}
