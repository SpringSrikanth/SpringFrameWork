package com.koseksi.chats.application.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.koseksi.chats.application.Dto.MessagesDto;
import com.koseksi.chats.application.service.ChatSevice;

@RestController
@RequestMapping("/chats")
public class ChatController {
	@Autowired
	private  ChatSevice chatSevice;
	
	@PostMapping(path="/create/message", consumes =  "application/json",produces =  "application/json")
	public ResponseEntity<?> createMessage(@RequestBody MessagesDto messagesDto) {
		if(messagesDto.getMessage() ==null)
		{
			return new ResponseEntity<>("Message is not empty", HttpStatus.CREATED);
		}
		
		try {
			MessagesDto msgDto=chatSevice.createMessage(messagesDto);
			return new ResponseEntity<>(msgDto, HttpStatus.CREATED);
		} catch (Exception e) {
			return  new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping(path = "/allMessages",produces =  "application/json")
	public ResponseEntity<?> getAllMessages(){
		try {
			return new ResponseEntity<>(chatSevice.getAllMessages(),HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping(path = "/messages/{fromUserId}/{toUserId}",produces =  "application/json")
	public ResponseEntity<?> getAllMessagesByFromUserIdToUserId(@PathVariable int fromUserId,@PathVariable int toUserId){
		try {
			return new ResponseEntity<>(chatSevice.fetchAllMessagesByFromUserIdToUserId(fromUserId,toUserId),HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping(path = "/getMessage/{msgId}",produces =  "application/json")
	public ResponseEntity<?> getMessage(@PathVariable int msgId){
		try {			
			Optional<MessagesDto> messageData=chatSevice.fetchMessage(msgId);
			if (messageData.isPresent()) {
				return new ResponseEntity<>(messageData.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	@DeleteMapping(path = "/deleteMessage/{msgId}",produces =  "application/json")
	public ResponseEntity<?> deleteMessage(@PathVariable int msgId){
		try {			
			String message=chatSevice.deleteMessage(msgId);
			if (message!="") {
				return new ResponseEntity<>(message, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(path="/update/message", consumes =  "application/json",produces =  "application/json")
	public ResponseEntity<?> updateMessage(@RequestBody MessagesDto messagesDto) {
		System.out.println("messageData.isPresent()");
		if(messagesDto.getMessage() ==null)
		{	
			System.out.println("messageData.isPresent()");
			return new ResponseEntity<>("Message is not empty", HttpStatus.BAD_REQUEST);
		}
		
		try {
			System.out.println("messageData.isPresent()");
			MessagesDto msgDto=chatSevice.updateMessage(messagesDto);
			return new ResponseEntity<>(msgDto, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("messageData.isPresent()");
			return  new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
