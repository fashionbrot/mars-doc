package com.github.fashionbrot.entity;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;


@Data
public class Course extends DataEntity<Course> {

    //课程标题
    private String title;

    private List<Label> labels = new ArrayList<>();

    private List<String> labelIds = new ArrayList<>();

    private List<Classify> classifyes = new ArrayList<>();



}

