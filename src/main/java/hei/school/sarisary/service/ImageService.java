package hei.school.sarisary.service;

import hei.school.sarisary.file.BucketComponent;
import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageConverter;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

@Service
public class ImageService {

    private final school.hei.sary.repository.ImageTransformationRepository imageTransformationRepository;
    private final BucketComponent bucketComponent;
    private final String directory = "images/";

    public ImageService(
            school.hei.sary.repository.ImageTransformationRepository imageTransformationRepository, BucketComponent bucketComponent) {
        this.imageTransformationRepository = imageTransformationRepository;
        this.bucketComponent = bucketComponent;
    }

    public String transformImage(String id, MultipartFile image) throws IOException {
        String suffix = "." + FilenameUtils.getExtension(image.getOriginalFilename());
        String prefixOriginal = id + "-initial";
        String prefixTransformed = id + "-grayscaled";
        String bucketKeyOriginal = directory + prefixOriginal + suffix;
        String bucketKeyTransformed = directory + prefixTransformed + suffix;

        File toUpload = File.createTempFile(prefixOriginal, suffix);
        image.transferTo(toUpload);
        File grayscaled = this.toGrayscale(id, toUpload);

        bucketComponent.upload(toUpload, bucketKeyOriginal);
        bucketComponent.upload(grayscaled, bucketKeyTransformed);

        return bucketComponent.presign(bucketKeyTransformed, Duration.ofMinutes(30)).toString();
    }

    public File toGrayscale(String name, File original) throws IOException {
        String extension = FilenameUtils.getExtension(original.getName());
        ImagePlus originalImage = IJ.openImage(original.getAbsolutePath());
        new ImageConverter(originalImage).convertToGray32();
        File transformed = File.createTempFile(name + "-grayscale", "." + extension);
        ImageIO.write(originalImage.getBufferedImage(), extension, transformed);
        return transformed;
    }
}
