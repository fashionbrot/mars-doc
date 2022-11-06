package com.github.fashionbrot.doc.vo;

import com.github.fashionbrot.doc.annotation.ApiIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocVo implements Serializable {

    private static final long serialVersionUID = -8942081915859350949L;

    private String version;

    private InfoVo info;

    private List<ClassVo> classList;

    private List<LinkVo> requestList;

    private List<LinkVo> responseList;

}
