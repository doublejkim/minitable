package example.minitable.util;

import example.minitable.dto.file.UploadFileDto;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileUtil {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public String extractExt(String originalFileName) {
        int pos = originalFileName.lastIndexOf(".");
        return originalFileName.substring(pos + 1);
    }

    public String createStoreFileName(String originalFileName) {
        String ext = extractExt(originalFileName);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    public UploadFileDto storeFile(MultipartFile multipartFile) throws IOException {
        if(multipartFile.isEmpty()) {
            return null;
        }

        String realfileName = multipartFile.getOriginalFilename();
        String virtualfileName = createStoreFileName(realfileName);
        multipartFile.transferTo(new File(getFullPath(virtualfileName)));

        return new UploadFileDto(realfileName, virtualfileName, extractExt(realfileName));
    }





}
