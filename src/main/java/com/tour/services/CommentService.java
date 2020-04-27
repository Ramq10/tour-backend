/**
 * 
 */
package com.tour.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tour.entity.Comment;
import com.tour.entity.TravelStory;
import com.tour.entity.User;
import com.tour.entity.dto.CommentDTO;
import com.tour.exception.UnprocessableEntityException;
import com.tour.repository.CommentRepository;

/**
 * @author 91945
 *
 */
@Service
public class CommentService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private TravelStoryService travelStoryService;
	@Autowired
	private UserService userService;

	public void save(CommentDTO commentDTO) {
		logger.info("Inside CommentService::save");
		validateComment(commentDTO);
		Comment comment = null;
		User user = userService.getLoggedInUser();
		TravelStory travelStory = travelStoryService.findById(commentDTO.getPostId());
		if (commentDTO.getId() == null) {
			comment = new Comment(commentDTO);
			if (commentDTO.getParentCommentId() != null) {
				comment.setParentComment(getCommentById(commentDTO.getParentCommentId()));
			}
		} else {
			comment = commentRepository.findById(commentDTO.getId()).get();
			checkValidUser(user, comment.getCommentedBy());
			comment.setMessage(commentDTO.getMessage());
		}
		comment.setCommentedBy(user);
		comment.setPost(travelStory);
		commentRepository.save(comment);
		logger.info("Completed CommentService::save");

	}

	private void checkValidUser(User user, User commentedBy) {
		logger.info("Inside CommentService::checkValidUser");
		if (user.getId() != commentedBy.getId()) {
			throw new UnprocessableEntityException("You Cannot edit this Comment.");
		}
		logger.info("completed CommentService::checkValidUser");

	}

	private void validateComment(CommentDTO commentDTO) {
		logger.info("Inside CommentService::validateComment");
		if (StringUtils.isBlank(commentDTO.getMessage())) {
			throw new UnprocessableEntityException("Invalid Comment.");
		}
		if (commentDTO.getParentCommentId() != null && !commentRepository.existsById(commentDTO.getParentCommentId())) {
			throw new UnprocessableEntityException("You cannot reply to this comment.");
		}
		if (commentDTO.getPostId() == null) {
			throw new UnprocessableEntityException("Invalid Post.");
		}
		logger.info("Inside CommentService::validateComment");

	}

	public CommentDTO getCommentDTOById(Long id) {
		return new CommentDTO(commentRepository.findById(id).get());
	}

	public Comment getCommentById(Long id) {
		return commentRepository.findById(id).get();
	}

	public List<CommentDTO> getCommentDTOByParentId(Long parentId) {
		List<CommentDTO> commentDTOList = new ArrayList<CommentDTO>();
		commentRepository.findByParantId(parentId).forEach(c -> {
			commentDTOList.add(new CommentDTO(c));
		});
		return commentDTOList;
	}

	public List<CommentDTO> getCommentDTOByPostId(Long postId) {
		List<CommentDTO> commentDTOList = new ArrayList<CommentDTO>();
		commentRepository.findByTravelStoryId(postId).forEach(c -> {
			CommentDTO cdto = new CommentDTO(c);
			commentDTOList.add(cdto);
//			Long localId = c.getId();

//			List<Comment> childComment = commentRepository.findByParantId(localId);
//			if (childComment != null && childComment.size() > 0) {
//				List<CommentDTO> childCommentDTO = new ArrayList<>();
//				childComment.forEach(x -> {
//					childCommentDTO.add(new CommentDTO(x));
//				});
//				cdto.setChildComment(childCommentDTO);
//			}
//			
			
		});
		return commentDTOList;
//		List<Comment> commentListFromDB = commentRepository.findByTravelStoryId(postId);
//		List<CommentDTO> commentDTOList = new ArrayList<>();
//		List<Comment> commentCheckList = new ArrayList<>();
//		for(Comment c:commentListFromDB) {
//			if(!commentCheckList.contains(c) && c.getParentComment()==null) {
//				commentCheckList.add(c);
//				commentDTOList.add(new CommentDTO(c));
//			}else if(!commentCheckList.contains(c) && c.getParentComment()!=null) {
//				commentDTOList
//			}
//		}
//		return commentDTOList;
	}

}
