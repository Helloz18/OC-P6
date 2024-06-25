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

	/**
	 * Get a Topic by its Id.
	 * @param id the Id of the Topic.
	 * @return a Topic.
	 */
	@Override
	public Topic findTopicById(Long id) {
		return topicRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("Le topic n'existe pas."));
	}

	/**
	 * Get the list of all topics.
	 * @return a List of topics.
	 */
	@Override
	public List<Topic> getTopics() {
		return topicRepository.findAll();
	}
	
}
