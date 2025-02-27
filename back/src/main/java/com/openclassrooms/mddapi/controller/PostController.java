package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.PostCreateDTO;
import com.openclassrooms.mddapi.dto.PostDetailDTO;
import com.openclassrooms.mddapi.dto.PostDTO;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.ResponseMessage;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.security.JwtService;
import com.openclassrooms.mddapi.service.ICommentService;
import com.openclassrooms.mddapi.service.IPostService;
import com.openclassrooms.mddapi.service.ITopicService;
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

import java.util.List;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Post controller", description = "Controller used to manage posts: get, create, update.")
@RequestMapping("/api/post")
public class PostController {

    private IPostService postService;
    private JwtService jwtService;
    private IUserService userService;
    private ITopicService topicService;
    private ICommentService commentService;

    public PostController(IPostService postService,
                          JwtService jwtService,
                          IUserService userService,
                          ITopicService topicService,
                          ICommentService commentService) {
        this.postService = postService;
        this.jwtService = jwtService;
        this.userService = userService;
        this.topicService = topicService;
        this.commentService = commentService;
    }

    @PostMapping("")
    @Operation(summary = "Create a new post.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post is saved in database.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseMessage.class))),
            @ApiResponse(responseCode = "404", description = "The user or the topic doesn't exist in the database.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseMessage.class)))
    })
    public ResponseEntity<?> createPost(
            @Parameter(description = "Bearer token", example = "Bearer eyJhbGciOJIUzI1NiJ9...")
            @RequestHeader("Authorization") String bearer,
            @RequestBody PostCreateDTO postCreateDTO
    ){
        try {
            String emailToken = jwtService.getEmailByToken(bearer.substring(7));
            if (userService.getUserByEmail(emailToken).isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseMessage("L'utilisateur n'existe pas."));
            }
            Topic topic = topicService.findTopicById(postCreateDTO.getTopicId());
            User user = userService.getUserByEmail(emailToken).orElseThrow();
            postService.savePost(user, topic, postCreateDTO);
            return ResponseEntity.ok().body(new ResponseMessage("Artile créé avec succès"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage(e.getMessage()));
        }
    }

    @GetMapping("")
    @Operation(summary = "Get the list of posts from the user subscription.",
            description = "According to the user subscriptions, retrieve all posts from this topics.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of PostListDTO",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PostDTO.class)))
    })
    public ResponseEntity<?> getPosts(@Parameter(description = "Bearer token", example = "Bearer eyJhbGciOJIUzI1NiJ9...")
                           @RequestHeader("Authorization") String bearer
                       ) {
            String
                    emailToken =
                    jwtService.getEmailByToken(bearer.substring(7));
            if (userService.getUserByEmail(emailToken).isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseMessage("L'utilisateur n'existe pas."));
            } else {
                User
                        user =
                        userService.getUserByEmail(emailToken).orElseThrow();
                List<PostDTO>
                        posts =
                        postService.getPosts(user);
                return ResponseEntity.ok().body(posts);
            }
    }

    @GetMapping("/{postId}")
    @Operation(summary = "Get a post by its Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A PostDTO.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseMessage.class))),
            @ApiResponse(responseCode = "404", description = "The post doesn't exist in the database.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseMessage.class)))
    })
    public ResponseEntity<?> getPostDTO(@PathVariable Long postId) {
        try {
            PostDetailDTO
                    postDetailDTO =
                    postService.getPostById(postId);
            return ResponseEntity.ok().body(postDetailDTO);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage(e.getMessage()));
        }
    }

    @PostMapping("/{postId}/comment")
    @Operation(summary = "Create a new commentaire.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment is saved in database.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseMessage.class))),
            @ApiResponse(responseCode = "404", description = "The user or the post doesn't exist in the database.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseMessage.class)))
    })
    public ResponseEntity<?> saveComment(
            @Parameter(description = "Bearer token", example = "Bearer eyJhbGciOJIUzI1NiJ9...")
            @RequestHeader("Authorization") String bearer,
            @PathVariable Long postId, @RequestBody String content) {
        try {
        String
                emailToken =
                jwtService.getEmailByToken(bearer.substring(7));
        if (userService.getUserByEmail(emailToken).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("L'utilisateur n'existe pas."));
            }
            User
                    user =
                    userService.getUserByEmail(emailToken).orElseThrow();
            Post
                    post =
                    postService.findPostById(postId);
            commentService.saveComment(user, post, content);

            return ResponseEntity.ok().body(new ResponseMessage("Comment saved"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage(e.getMessage()));
        }
    }
}
