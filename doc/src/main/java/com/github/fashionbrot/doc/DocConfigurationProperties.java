package com.github.fashionbrot.doc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocConfigurationProperties {

    public static final String BEAN_NAME = "marsDocConfigurationProperties";

    private String springProfilesActive;


}
