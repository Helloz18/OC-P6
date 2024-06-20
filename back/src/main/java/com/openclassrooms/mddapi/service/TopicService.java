package com.openclassrooms.mddapi.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.repository.TopicRepository;

@Service
public class TopicService implements ITopicService {

	private TopicRepository topicRepository;
	
	public TopicService(TopicRepository topicRepository) {
		this.topicRepository = topicRepository;
	}

	@Override
	public Topic findTopicById(Long id) {
		return topicRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Le topic n'existe pas."));
	}

	@Override
	public List<Topic> getTopics() {
		return topicRepository.findAll();
	}
	
}
