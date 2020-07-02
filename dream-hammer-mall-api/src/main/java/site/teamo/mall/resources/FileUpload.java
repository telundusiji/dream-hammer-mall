package site.teamo.mall.resources;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file")
@PropertySource(value = "classpath:file-upload-dev.properties")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileUpload {
    private String imageUserFaceLocation;
    private String imageServerUrl;
}
