package com.devmatch.publication.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.devmatch.publication.document.Publication;
import com.devmatch.publication.dto.PublicationDto;
import com.devmatch.publication.form.PublicationPatchDescription;
import com.devmatch.publication.form.PublicationPost;
import com.devmatch.publication.model.mapper.PublicationMapper;
import com.devmatch.publication.repository.PublicationRepository;
import com.devmatch.publication.service.PublicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

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
    public Mono<PublicationDto> create(PublicationPost publicationPost, Mono<FilePart> filePart) {
        return filePart
                .flatMap(fp -> createTempFile(fp))
                .map(tempFile -> {
                    Publication publication = publicationPost.toDocument();
                    String key = uploadContentToBucket(tempFile, buildFileName(tempFile.getName(), publication.getType().toString()));
                    publication.setContent(key);
                    return publication;
                })
                .flatMap(p -> repository.save(p))
                .map(PublicationDto::new);
    }

    @Override
    public Mono<PublicationDto> updateDescription(String id, PublicationPatchDescription publicationPatchDescription) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatusCode.valueOf(404), "A Publicação não foi encontrada.")))
                .map(publication -> {
                    publication.setDescription(publicationPatchDescription.getDescription());
                    return publication;
                })
                .flatMap(p -> repository.save(p))
                .map(PublicationDto::new);
    }

    @Override
    public Mono<PublicationDto> findById(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatusCode.valueOf(404), "A Publicação não foi encontrada.")))
                .map(PublicationDto::new);
    }

    @Override
    public Flux<PublicationDto> findAll(Integer page, Integer size, String sortField, Sort.Direction sortDirection) {
        return repository.findAll(
                Objects.isNull(sortField) || Objects.isNull(sortDirection)?
                        Sort.by(Sort.Direction.ASC, "id") : Sort.by(sortDirection, sortField))
                .skip(page * size)
                .take(size)
                .map(PublicationMapper::toDto);
    }

    @Override
    public Mono<Void> delete(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatusCode.valueOf(404), "A Publicação não foi encontrada.")))
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
        return String.format("%s/%s/%s", type, UUID.randomUUID(),  "content." + name.substring(name.lastIndexOf('.') + 1));
    }
}
