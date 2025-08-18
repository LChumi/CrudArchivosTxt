/*
 * Copyright (c) 2025 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.services.images;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class GenericImageService {

    private final String[] EXTENSIONS = {".jpg", ".JPG", ".png", ".jpeg"};

    public Resource getImageFrom(String directory, String imageName, String defaultImage) throws MalformedURLException {
        String nameWithoutExtension = imageName.replaceAll("\\.[^.]+$", "");
        for (String extension : EXTENSIONS) {
            Path path = Paths.get(directory).resolve(nameWithoutExtension + extension);
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            }
        }

        if (defaultImage != null) {
            return getDefaultImage(directory, defaultImage);
        }

        return null;
    }

    public Short imageExist(String directory, String imageName){
        String nameWithoutExtension = imageName.replaceAll("\\.[^.]+$", "");
        for (String extension : EXTENSIONS) {
            Path path = Paths.get(directory).resolve(nameWithoutExtension + extension);
            if (Files.exists(path) && Files.isReadable(path) && Files.isRegularFile(path)) {
                return 1;
            }
        }
        return 0;
    }

    private Resource getDefaultImage(String directory, String fileName) throws MalformedURLException {
        Path path = Paths.get(directory).resolve(fileName);
        Resource resource = new UrlResource(path.toUri());
        return (resource.exists() && resource.isReadable()) ? resource : null;
    }

    public String getContentType(Resource resource) {
        return MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM).toString();
    }
}
