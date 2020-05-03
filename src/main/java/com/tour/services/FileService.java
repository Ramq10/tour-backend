
package com.tour.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.tour.entity.File;
import com.tour.exception.UnprocessableEntityException;
import com.tour.repository.FileRepository;

/**
 * @author Ramanand
 *
 */
@Service
public class FileService {

	@Autowired
	private FileRepository fileRepository;

	@Autowired
	@Value("/home/witty/upload")
	private final Path fileStorageLocation;

	@Autowired
	public FileService() {
		this.fileStorageLocation = Paths.get("/home/witty/upload").toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public File uploadFile(MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		Path targetLocation = this.fileStorageLocation.resolve(fileName);
		try {
			Files.copy(file.getInputStream(), targetLocation);
		} catch (IOException e) {
			e.printStackTrace();
		}
		File storyFile = new File(null, fileName, targetLocation.toString());
		return fileRepository.save(storyFile);

	}

	public ResponseEntity<Resource> getFileAsResponseById(Long id, HttpServletRequest request) {
		Resource resource = loadFileAsResource(id);
		try {
			return ResponseEntity.ok()
					.contentType(MediaType.parseMediaType(
							request.getServletContext().getMimeType(resource.getFile().getAbsolutePath())))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
					.body(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Resource loadFileAsResource(Long id) {
		try {
			File storyFile = getFileById(id);
			Path filePath = this.fileStorageLocation.resolve(storyFile.getName()).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			}
			return resource;
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public File getFileById(Long id) {
		Optional<File> storyFile = fileRepository.findById(id);
		if (storyFile == null || !storyFile.isPresent()) {
			throw new UnprocessableEntityException("Invalid File Id.");
		}
		return storyFile.get();
	}

	public void deleteFileById(Long id) {
		fileRepository.deleteById(id);
	}

}
