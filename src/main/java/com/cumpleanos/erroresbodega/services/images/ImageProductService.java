/*
 * Copyright (c) 2025 Luis Chumi.
 * Este software está licenciado bajo la Licencia Pública General de GNU versión 3. Puedes encontrar una copia de la licencia en https://www.gnu.org/licenses/gpl-3.0.html.
 *
 * Para consultas o comentarios, puedes contactarme en "luischumi.9@gmail.com".
 * Me gustaría ser reconocido por mi trabajo y estar abierto a colaboraciones o enseñanzas sobre el programa.
 */

package com.cumpleanos.erroresbodega.services.images;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class ImageProductService {

    private final String[] EXTENSIONS = {".jpg", ".JPG"};

    @Value("${file.images.products}")
    private String image_directory;

    public Resource getImageResource(String imageName) throws MalformedURLException {
        return searchWithExtensions(imageName);
    }

    public String getContentType(Resource resource) {
        return MediaTypeFactory.getMediaType(resource)
                .orElse(MediaType.APPLICATION_OCTET_STREAM)
                .toString();
    }

    private Resource searchWithExtensions(String basename) throws MalformedURLException {
        String nameWithoutExtension = basename.replaceAll("\\.[^.]+$", ""); // Remover extensión si tiene
        for (String extension : EXTENSIONS) {
            Resource resource = getReadableResource(image_directory, nameWithoutExtension + extension);
            if (resource != null) {
                return resource;
            }
        }

        log.info("No se encontró ningún recurso, retornando imagen predeterminada");
        return getReadableResource(image_directory, "default.jpg");
    }


    private Resource getReadableResource(String directory, String fileName) throws MalformedURLException {
        Path path = Paths.get(directory).resolve(fileName);
        Resource resource = new UrlResource(path.toUri());
        return (resource.exists() && resource.isReadable()) ? resource : null;
    }
}
