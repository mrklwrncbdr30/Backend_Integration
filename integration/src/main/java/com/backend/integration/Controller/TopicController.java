package com.backend.integration.Controller; 

// import java.io.IOException; 
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.http.HttpStatus; 
// import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.DeleteMapping; 
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.PathVariable; 
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RestController;

import com.backend.integration.Entity.Chapter;
import com.backend.integration.Entity.Topic;
import com.backend.integration.Service.TopicService;
 

// Controller class for handling topic-related operations
@RestController
@RequestMapping("/api/v1/auth") // Base path for topic related operations
@CrossOrigin("http://localhost:5173") // Allowing requests from this origin
public class TopicController {

    @Autowired // Injection of TopicService dependency
    private TopicService topicService;


     @GetMapping("/topics")
    public List<Topic> getAllTopic() {
        return topicService.getAllTopic();
    }

    @GetMapping("/{topic_id}")
    public ResponseEntity<Topic> getTopicById(@PathVariable Long topic_id) {
        Optional<Topic> topic = topicService.getTopicById(topic_id);
        return topic.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/postTopic")
    public ResponseEntity<Topic> createChapter(@RequestBody Topic topic) {
        Topic createdTopic = topicService.saveOrUpdateTopic(topic);
        return new ResponseEntity<>(createdTopic, HttpStatus.CREATED);
    }

    @PutMapping("/{topic_id}")
    public ResponseEntity<Topic> updateTopic(@PathVariable Long topic_id, @RequestBody Topic topicDetails) {
        Optional<Topic> topic = topicService.getTopicById(topic_id);
        if (topic.isPresent()) {
            Topic updatedTopic = topicService.saveOrUpdateTopic(topicDetails);
            return new ResponseEntity<>(updatedTopic, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/del/{topic_id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long topic_id) {
        topicService.deleteTopic(topic_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
