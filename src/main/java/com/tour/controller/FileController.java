/**
 * 
 */
package com.tour.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tour.entity.File;
import com.tour.services.FileService;

/**
 * @author Ramanand
 *
 */
@RestController
@RequestMapping("/file")
public class FileController {

	@Autowired
	private FileService storyFileService;

	@PostMapping(path = "/upload")
	public File uploadFile(@RequestParam(required = false, value = "file") MultipartFile file) {
		return storyFileService.uploadFile(file);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Resource> getFile(@PathVariable Long id, HttpServletRequest request) {
		return storyFileService.getFileAsResponseById(id, request);
	}

	@DeleteMapping("/{id}")
	public void deleteFile(@PathVariable Long id) {
		storyFileService.deleteFileById(id);
	}
	
}
