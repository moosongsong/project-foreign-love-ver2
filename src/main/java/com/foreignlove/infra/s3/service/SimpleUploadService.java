package com.foreignlove.infra.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.foreignlove.infra.s3.component.S3Component;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class SimpleUploadService implements UploadService {
	private final AmazonS3 amazonS3;
	private final S3Component component;

	@Override
	public void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String fileName) {
		amazonS3.putObject(new PutObjectRequest(component.getBucket(), fileName, inputStream, objectMetadata)
				.withCannedAcl(CannedAccessControlList.PublicRead));
	}

	@Override
	public void removeFile(String fileName) {
		amazonS3.deleteObject(component.getBucket(), fileName);
	}

	@Override
	public String getFileUrl(String fileName) {
		return amazonS3.getUrl(component.getBucket(), fileName).toString();
	}
}
