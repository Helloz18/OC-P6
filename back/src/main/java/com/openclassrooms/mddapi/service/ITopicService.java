package com.openclassrooms.mddapi.service;

import java.util.List;

import com.openclassrooms.mddapi.model.Topic;

public interface ITopicService {

	Topic findTopicById(Long id);

	List<Topic> getTopics();

}
