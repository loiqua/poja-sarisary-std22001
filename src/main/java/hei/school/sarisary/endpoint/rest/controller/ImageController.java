package hei.school.sarisary.endpoint.rest.controller;





import hei.school.sarisary.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ImageController {

    @Autowired
    private ImageService imageService;

    @RequestMapping(
            method = RequestMethod.PUT,
            path = "/black-and-white/{id}",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> transformToBlackAndWhite(
            @PathVariable(name = "id") String id, @RequestPart(name = "file") MultipartFile file)
            throws IOException {
        try {
            String transformedUrl = imageService.transformImage(id, file);
            // Si la transformation réussit, retourner un code 200 OK sans contenu dans le corps
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            // En cas d'échec de la transformation, retourner un code 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/black-and-white/{id}")
    public ResponseEntity<Map<String, String>> getBlackAndWhiteImage(@PathVariable String id) {
        // Supposons que vous avez une méthode pour récupérer les URL pré-signées des images originale et transformée
        String originalUrl = imageService.getOriginalImageUrl(id);
        String transformedUrl = imageService.getTransformedImageUrl(id);

        Map<String, String> urls = new HashMap<>();
        urls.put("original_url", originalUrl);
        urls.put("transformed_url", transformedUrl);

        // Retourner un code 200 OK avec les URLs dans le corps de la réponse
        return ResponseEntity.ok(urls);
    }
}
