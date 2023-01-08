package com.github.fashionbrot.doc.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author fashionbrot
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCodeVo {

    private String code;

    private String message;

}
