package com.devmatch.publication.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.devmatch.lib.util.Utils;
import com.devmatch.publication.document.Publication;
import com.devmatch.publication.dto.PublicationDto;
import com.devmatch.publication.form.PublicationPatchDescription;
import com.devmatch.publication.form.PublicationPost;
import com.devmatch.publication.repository.PublicationRepository;
import com.devmatch.publication.service.PublicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class PublicationServiceImpl implements PublicationService {

    private final Path basePath = Paths.get(System.getProperty("java.io.tmpdir"));

    @Autowired
    private PublicationRepository repository;

    @Autowired
    private AmazonS3 amazonS3Client;

    @Value("${aws.bucketName}")
    private String bucketName;

    @Override
    public Mono<PublicationDto> create(Mono<PublicationPost> publicationPost, Mono<FilePart> filePart) {
        return filePart
                .flatMap(fp -> createTempFile(fp))
                .zipWith(publicationPost, (f, p) -> {
                    Publication publication = p.toDocument();
                    String key = uploadContentToBucket(f, buildFileName(f.getName(), publication.getType().toString()));
                    publication.setContent(key);
                    return publication;
                })
                .flatMap(p -> repository.save(p))
                .map(PublicationDto::new);
    }

    @Override
    public Mono<PublicationDto> updateDescription(String id, Mono<PublicationPatchDescription> publicationPatchDescription) {
        return repository.findById(id)
                .zipWith(publicationPatchDescription, (p, pPatchDescription) -> {
                    p.setDescription(pPatchDescription.getDescription());
                    return p;
                })
                .flatMap(p -> repository.save(p))
                .map(PublicationDto::new);
    }

    @Override
    public Mono<Publication> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Flux<Publication> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Void> delete(String id) {
        return repository.findById(id)
                .flatMap(p -> removeBucketContent(p))
                .flatMap(p -> repository.deleteById(p.getId()))
                .then();
    }

    private Mono<File> createTempFile(FilePart fp) {
        return fp.transferTo(basePath.resolve(fp.filename())).then(Mono.just(basePath.resolve(fp.filename()).toFile()));
    }

    private Mono<Publication> removeBucketContent(Publication p) {

        try {

            if (amazonS3Client.doesBucketExistV2(bucketName)) {
                DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, p.getContent());
                amazonS3Client.deleteObject(deleteObjectRequest);
                p.setType(null);
                p.setContent(null);
                return Mono.just(p);
            }

            throw new RuntimeException("Bucket not exist!");
        } catch (Exception e) {

            throw new RuntimeException(e.getMessage());
        }
    }

    private String uploadContentToBucket(File file, String fileName) throws RuntimeException {

        try {

            if (amazonS3Client.doesBucketExistV2(bucketName)) {
                PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, file);
                amazonS3Client.putObject(putObjectRequest);

                return amazonS3Client.getObject(bucketName, fileName).getKey();
            }

            throw new RuntimeException("Bucket not exist!");
        } catch (Exception e) {

            throw new RuntimeException(e.getMessage());
        } finally {
            file.delete();
        }
    }

    private String buildFileName(String name, String type) {
        return String.format("%s/%s/%s", type, Utils.generateUUID(),  "content." + name.substring(name.lastIndexOf('.') + 1));
    }
}
