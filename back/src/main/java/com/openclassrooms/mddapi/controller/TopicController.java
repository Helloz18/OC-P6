package com.openclassrooms.mddapi.controller;

import java.util.List;

import com.openclassrooms.mddapi.model.ResponseMessage;
import com.openclassrooms.mddapi.security.JwtService;
import com.openclassrooms.mddapi.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.service.ITopicService;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Topic controller", description = "Controller used to manage topic: getAll, subscribe.")
@RequestMapping("/api/topic")
public class TopicController {
	
	private ITopicService topicService;
	private IUserService userService;
	private JwtService jwtService;
	
	public TopicController(ITopicService topicService, IUserService userService, JwtService jwtService) {
		this.topicService = topicService;
		this.userService = userService;
		this.jwtService = jwtService;
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

	@PutMapping("/{topicId}")
	@Operation(summary = "Subscribe to a topic.",
			description = "The Id of the topic and the email of the user are mandatory.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "The subscription is added.",
					content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = ResponseMessage.class))),
			@ApiResponse(responseCode = "404", description = "The user or the topic doesn't exist in the database.",
					content = @Content(mediaType = "application/json",
							schema = @Schema(implementation = ResponseMessage.class)))
	})
	public ResponseEntity<ResponseMessage> subscribe(
			@Parameter(description = "Bearer token", example = "Bearer eyJhbGciOJIUzI1NiJ9...")
			@RequestHeader("Authorization") String bearer,
			@PathVariable Long topicId) throws Exception {
		String emailToken = jwtService.getEmailByToken(bearer.substring(7));

		if (userService.getUserByEmail(emailToken).isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseMessage("L'utilisateur n'existe pas."));
		}
		try {
			Topic topic = topicService.findTopicById(topicId);
			String message = userService.subscribe(topic, emailToken);
			return ResponseEntity.ok(new ResponseMessage(message));
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ResponseMessage(e.getMessage()));
		}
	}
}
