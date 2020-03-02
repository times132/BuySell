package com.example.giveandtake.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class FileDTO {

    private String uuid;
    private String uploadPath;
    private String fileName;
    private Boolean fileType;
}
