package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.PostCreateDTO;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

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
}
