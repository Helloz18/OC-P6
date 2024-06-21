package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.PostCreateDTO;
import com.openclassrooms.mddapi.dto.PostDTO;
import com.openclassrooms.mddapi.dto.PostForListDTO;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
		PostDTO post = new PostDTO();
		return post;
	}
}
