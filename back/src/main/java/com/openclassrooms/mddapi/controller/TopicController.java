package com.openclassrooms.mddapi.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.service.ITopicService;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Topic controller", description = "Controller used to manage topic: getAll")
@RequestMapping("/api/topic")
public class TopicController {
	
	private ITopicService topicService;
	
	public TopicController(ITopicService topicService) {
		this.topicService = topicService;		
	}


	@GetMapping("")
	@Operation(summary = "Get the list of all topics.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "a List of Topics",
					content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = Topic.class)))
	})
	public ResponseEntity<List<Topic>> getTopics() {
		List<Topic> topics = topicService.getTopics();
		return ResponseEntity.ok(topics);
	}
}
