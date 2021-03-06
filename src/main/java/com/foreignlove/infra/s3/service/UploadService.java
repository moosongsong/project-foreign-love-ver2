package com.foreignlove.infra.s3.service;

import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.InputStream;

public interface UploadService {

	void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String fileName);

	void removeFile(String fileName);

	String getFileUrl(String fileName);

}
