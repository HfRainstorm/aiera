package cn.hfstorm.aiera.system.api.domain;

import lombok.Data;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serial;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author : hmy
 * @since : 2025/4/7
 */
@Data
public class CommonsMultipleFile implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * contents of the file as an array of bytes.
     */
    private byte[] bytes;

    /**
     * original filename
     */
    private String originalFilename;

    /**
     * 文件存储方式
     * {@link  cn.hfstorm.aiera.file.enums.FileEnumd}
     */
    private String fileSaveType;

    /**
     * content type of the file
     */
    private String contentType;

    /**
     * the size of the file in bytes.
     */
    private long size;

    private long lastModified;

    private boolean empty;

    private boolean exists;

    private InputStream inputStream;

    public CommonsMultipleFile(MultipartFile multipartFile) throws IOException {
        this(multipartFile, "");
    }

    public CommonsMultipleFile(MultipartFile multipartFile, String fileSaveType) throws IOException {
        this.fileSaveType = fileSaveType;
        this.bytes = multipartFile.getBytes();
        this.contentType = multipartFile.getContentType();
        this.size = multipartFile.getSize();
        this.originalFilename = multipartFile.getOriginalFilename();
        this.empty = multipartFile.isEmpty();
        this.exists = multipartFile.getResource().exists();
    }

    public void transferTo(File dest) throws IOException, IllegalStateException {
        if (dest.isAbsolute() && !dest.exists()) {
            FileCopyUtils.copy(this.getInputStream(), Files.newOutputStream(dest.toPath()));
        }
    }

    public InputStream getInputStream() {
        if (this.inputStream == null) {
            this.inputStream = new ByteArrayInputStream(this.bytes);
        }
        return this.inputStream;
    }
    public void transferTo(Path dest) throws IOException, IllegalStateException {
        FileCopyUtils.copy(this.getInputStream(), Files.newOutputStream(dest));
    }
}
