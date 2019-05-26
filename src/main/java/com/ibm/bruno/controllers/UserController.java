package com.ibm.bruno.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class UserController {

	@RequestMapping("/users")
	@ResponseBody
	public String getUsers() {
		return "{\"users\":[{\"name\":\"Lucas\", \"country\":\"Brazil\"}," +
		           "{\"name\":\"Jackie\",\"country\":\"China\"}]}";
	}
	
	@RequestMapping("/list")
	@GetMapping(produces="application/json")
	public @ResponseBody ResponseEntity<String> listUserData(){
//   	DtoGithub git = new DtoGithub();
//   	git.setCreated_at("10/10/2000");
//   	git.setDescription("teste");
//   	git.setFull_name("bruno");
//   	git.setLanguage("BR");
//   	git.setName("bruno");
//   	git.setOwner("bb");
//   	git.setType(true);
//   	git.setUpdated_at("26/05/2019");
   	
   	
   	RestTemplate restTemplate = new RestTemplate(); //1
   	String url = "https://api.github.com/users/brunocamara25/repos"; //2
   	ResponseEntity<String> response = restTemplate.getForEntity(url, String.class); //3
   	//Foo foo = restTemplate.getForObject(fooResourceUrl, Foo.class);
   	return response;
   }
	
	
   
}
