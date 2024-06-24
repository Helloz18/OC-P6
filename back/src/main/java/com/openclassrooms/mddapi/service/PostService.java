package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.dto.PostCreateDTO;
import com.openclassrooms.mddapi.dto.PostDetailDTO;
import com.openclassrooms.mddapi.dto.PostDTO;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PostService implements IPostService {

	private PostRepository postRepository;
	
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}


	/**
	 * Save a new Post in the database. The creation date of the post is set in this method.
	 * @param user
	 * @param topic
	 * @param postCreateDTO
	 */
	@Override
	public void savePost(User user,Topic topic, PostCreateDTO postCreateDTO) {
		Post post = new Post();

		post.setUser(user);
		post.setTopic(topic);
		post.setTitle(postCreateDTO.getTitle());
		post.setContent(postCreateDTO.getContent());
		post.setCreatedAt(String.valueOf(Instant.now()));

		postRepository.save(post);
	}

	/**
	 * Get PostDTOs from the topics subscribed by the user.
	 * @param user
	 * @return a List of PostDTO
	 */
	@Override
	public List<PostDTO> getPosts(User user) {
		List<PostDTO>
				listOfPostsDTO = new ArrayList<>();
		for(Topic t : user.getTopics()) {
			List<Post> posts = postRepository.findByTopic_id(t.getId());
			for(Post p : posts) {
				PostDTO
						postsDTO = new PostDTO();
				postsDTO.setId(p.getId());
				postsDTO.setTitle(p.getTitle());
				postsDTO.setContent(p.getContent());
				postsDTO.setCreatedAt(p.getCreatedAt());
				postsDTO.setAuthor(p.getUser().getName());
				listOfPostsDTO.add(postsDTO);
			}
		}
		return listOfPostsDTO;
	}

	/**
	 * Get a PostDetailDTO by its Id.
	 * A PostDetailDTO contains a different structure than a Post to send only the usefull information to the view.
	 * @param postId id of the post.
	 * @return a PostDetailDTO
	 */
	@Override
	public PostDetailDTO getPostById(Long postId) {
		Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("L'article n'existe pas."));
		PostDetailDTO
				postDetailDTO = new PostDetailDTO();
		postDetailDTO.setAuthor(post.getUser().getName());
		postDetailDTO.setTitle(post.getTitle());
		postDetailDTO.setContent(post.getContent());
		postDetailDTO.setCreatedAt(post.getCreatedAt());
		postDetailDTO.setTopicName(post.getTopic().getName());

		List<CommentDTO> commentsDTO = new ArrayList<>();
		for(Comment comment: post.getComments()) {
			CommentDTO commentDTO = new CommentDTO();
			commentDTO.setContent(comment.getContent());
			commentDTO.setAuthor(comment.getUser().getName());

			commentsDTO.add(commentDTO);
		}

		postDetailDTO.setComments(commentsDTO);

		return postDetailDTO;
	}

	/**
	 * Find a Post by its Id.
	 * @param postId
	 * @return a Post
	 */
	@Override
	public Post findPostById(Long postId) {
		return postRepository.findById(postId).orElseThrow(
				() -> new NoSuchElementException("L'article n'existe pas."));
	}
}
