package com.back.kdquiz.files.dto.img;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ImgChangeDto {
    private Long id;
    private List<String> img;
}
