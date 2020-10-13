package com.boot.security.server.service;

import com.boot.security.server.model.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

	FileInfo save(MultipartFile file) throws IOException;

	void delete(String id);

}
