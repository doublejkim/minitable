package example.minitable.dto.file;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadFileDto {

    private String realfileName; // original file name
    private String virtualfileName; // uuid 로 변환된 파일 명
    private String ext;

}
