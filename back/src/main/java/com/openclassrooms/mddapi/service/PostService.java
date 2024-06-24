package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.CommentDTO;
import com.openclassrooms.mddapi.dto.PostCreateDTO;
import com.openclassrooms.mddapi.dto.PostDTO;
import com.openclassrooms.mddapi.dto.PostForListDTO;
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


	@Override
	public void savePost(User user,Topic topic, PostCreateDTO postCreateDTO) {
		Post post = new Post();
		//get from DTO
		post.setTopic(topic);
		post.setTitle(postCreateDTO.getTitle());
		post.setContent(postCreateDTO.getContent());
		// automatic add
		post.setCreatedAt(String.valueOf(Instant.now()));
		post.setUser(user);
		postRepository.save(post);
	}

	@Override
	public List<PostForListDTO> getPosts(User user) {
		List<PostForListDTO>
				listOfPostForListDTO = new ArrayList<>();
		for(Topic t : user.getTopics()) {
			List<Post> posts = postRepository.findByTopic_id(t.getId());
			for(Post p : posts) {
				PostForListDTO
						postForListDTO = new PostForListDTO();
				postForListDTO.setId(p.getId());
				postForListDTO.setTitle(p.getTitle());
				postForListDTO.setContent(p.getContent());
				postForListDTO.setCreatedAt(p.getCreatedAt());
				postForListDTO.setAuthor(p.getUser().getName());
				listOfPostForListDTO.add(postForListDTO);
			}
		}
		return listOfPostForListDTO;
	}

	@Override
	public PostDTO getPostById(Long postId) {
		Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("L'article n'existe pas."));
		PostDTO postDTO = new PostDTO();
		postDTO.setAuthor(post.getUser().getName());
		postDTO.setTitle(post.getTitle());
		postDTO.setContent(post.getContent());
		postDTO.setCreatedAt(post.getCreatedAt());
		postDTO.setTopicName(post.getTopic().getName());

		List<CommentDTO> commentsDTO = new ArrayList<>();
		for(Comment comment: post.getComments()) {
			CommentDTO commentDTO = new CommentDTO();
			commentDTO.setContent(comment.getContent());
			commentDTO.setAuthor(comment.getUser().getName());

			commentsDTO.add(commentDTO);
		}

		postDTO.setComments(commentsDTO);

		return postDTO;
	}

	@Override
	public Post findPostById(Long postId) {
		return postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("L'article n'existe pas."));
	}
}
