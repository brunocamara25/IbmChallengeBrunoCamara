package com.ibm.bruno.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@RestController
public class UserController {

	@RequestMapping("/test")
	@ResponseBody
	public String getUsers() {
		return "IBM Challenge :) !";
	}
	
	
	@ApiOperation(value = " ", notes = "Lista todos os repositórios do GitHub de um determinado usuário", response = UserController.class, responseContainer = "List", tags = {"IbmChallenge"})
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "Content-Type", required = true, dataType = "string", paramType = "header", defaultValue = "application/json")}
    )
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = "OK", response = UserController.class),
            @ApiResponse(code = 201, message = "Created", response = UserController.class),
            @ApiResponse(code = 400, message = "Bad request", response = UserController.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = UserController.class),
            @ApiResponse(code = 403, message = "Forbidden", response = UserController.class),
            @ApiResponse(code = 404, message = "Not Found", response = UserController.class),
            @ApiResponse(code = 500, message = "The server encountered an unexpected condition which prevented it from fulfilling the request", response =  UserController.class)})
   	@RequestMapping("/list")
	@GetMapping(produces="application/json")
	public @ResponseBody List<DtoGithub> listUserData(){

   	
		List<DtoGithub> listResponse = new ArrayList<DtoGithub>();
	    RestTemplate restTemplate = new RestTemplate(); //1
	   	String url = "https://api.github.com/users/vanildo/repos"; //2
	   	ResponseEntity<String> result = restTemplate.getForEntity(url, String.class); //3
	   	
	   	ObjectMapper objectMapper = new ObjectMapper();
	   	String jsonCarArray = result.getBody();
	   	try {
			List<ResponseItem> list = objectMapper.readValue(jsonCarArray, new TypeReference<List<ResponseItem>>(){});
			for (ResponseItem responseItem : list) {
				DtoGithub dto = new DtoGithub();
				dto.setCreated_at(responseItem.getCreated_at());
				dto.setDescription(responseItem.getDescription());
				dto.setFull_name(responseItem.getFull_name());
				dto.setLanguage(responseItem.getLanguage());
				dto.setName(responseItem.getName());
				dto.setOwner(responseItem.getOwner().getLogin());
				dto.setUpdated_at(responseItem.getUpdated_at());
				if (!("").equals(responseItem.get_private()) && responseItem.get_private() != null) {
					dto.set_private(Boolean.valueOf(responseItem.get_private()));
				} 
				listResponse.add(dto);
				
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	   	
	   	return listResponse;
   	
	}
   	
   	
}
