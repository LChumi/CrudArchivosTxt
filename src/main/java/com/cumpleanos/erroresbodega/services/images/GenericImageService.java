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

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

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

    public Map<String, Object> getPhotosByMonth(String directory) {
        File dir = new File(directory);
        File[] files = dir.listFiles((d, name) -> name.matches(".*\\.(jpg|png|jpeg)"));
        if (files == null || files.length == 0) return Map.of();

        ZoneId zone = ZoneId.systemDefault();
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("EEEE d MMMM", new Locale("es", "ES"));
        YearMonth currentMonth = YearMonth.now();

        // Agrupar por YearMonth y ordenar
        Map<YearMonth, List<LocalDate>> grouped = Arrays.stream(files)
                .map(f -> Instant.ofEpochMilli(f.lastModified()).atZone(zone).toLocalDate())
                .collect(Collectors.groupingBy(YearMonth::from, TreeMap::new, Collectors.toList()));

        Map<String, Object> result = new LinkedHashMap<>();

        for (var entry : grouped.entrySet()) {
            YearMonth ym = entry.getKey();
            List<LocalDate> dates = entry.getValue();

            String mesConAnio = ym.getMonth()
                    .getDisplayName(TextStyle.FULL, new Locale("es", "ES"))
                    + " " + ym.getYear();

            if (ym.equals(currentMonth)) {
                // Para el mes actual: detalle por día ordenado cronológicamente
                Map<String, Long> dias = dates.stream()
                        .collect(Collectors.groupingBy(
                                d -> d, // agrupar por LocalDate
                                TreeMap::new, // orden cronológico
                                Collectors.counting()
                        ))
                        .entrySet().stream()
                        .collect(Collectors.toMap(
                                e -> e.getKey().format(dayFormatter), // formatear al texto
                                Map.Entry::getValue,
                                (a, b) -> a,
                                LinkedHashMap::new // mantener el orden
                        ));

                result.put(mesConAnio, dias);
            } else {
                // Para meses anteriores: solo total
                result.put(mesConAnio, (long) dates.size());
            }
        }

        return result;
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