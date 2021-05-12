package com.javatpoint.server.main.user;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;  
import java.net.URI;  
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.hateoas.Resource;  
import org.springframework.hateoas.mvc.ControllerLinkBuilder;  
import org.springframework.http.ResponseEntity;  
import org.springframework.web.bind.annotation.DeleteMapping;  
import org.springframework.web.bind.annotation.GetMapping;  
import org.springframework.web.bind.annotation.PathVariable;  
import org.springframework.web.bind.annotation.PostMapping;  
import org.springframework.web.bind.annotation.RequestBody;  
import org.springframework.web.bind.annotation.RestController;  
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;  
@RestController  
public class UserJPAResource   
{  
@Autowired  
private UserRepository userRepository;
@GetMapping("/jpa/users")  
public List<User> retriveAllUsers()  
{  
return userRepository.findAll();  
} 
//@Autowired  
//private UserDaoService service;  
//@GetMapping("/jpa/users")  
//public List<User> retriveAllUsers()  
//{  
//return service.findAll();  
//}  

@GetMapping("/jpa/users/{id}")  
public Resource<User> retriveUser(@PathVariable int id)  
{  
Optional<User> user= userRepository.findById(id);  
if(!user.isPresent())  
//runtime exception  		
throw new UserNotFoundException("id: "+ id);  
//"all-users", SERVER_PATH + "/users"  
//retrieveAllUsers  
Resource<User> resource=new Resource<User>(user.get()); //constructor of Resource class  
//add link to retrieve all the users  
ControllerLinkBuilder linkTo=linkTo(methodOn(this.getClass()).retriveAllUsers());  
resource.add(linkTo.withRel("all-users"));  
return resource;  
}  
//@GetMapping("/jpa/users/{id}")  
//public Resource<User> retriveUser(@PathVariable int id)  
//{  
//User user= service.findOne(id);  
//if(user==null)  
////runtime exception  
//throw new UserNotFoundException("id: "+ id);  
////"all-users", SERVER_PATH + "/users"  
////retrieveAllUsers  
//Resource<User> resource=new Resource<User>(user);   //constructor of Resource class  
////add link to retrieve all the users  
//ControllerLinkBuilder linkTo=linkTo(methodOn(this.getClass()).retriveAllUsers());  
//resource.add(linkTo.withRel("all-users"));  
//return resource;  
//}  


//method that delete a user resource 
@DeleteMapping("/jpa/users/{id}")  
public void deleteUser(@PathVariable int id)  
{  
userRepository.deleteById(id);  
}  
//@DeleteMapping("/jpa/users/{id}")  
//public void deleteUser(@PathVariable int id)  
//{  
//User user= service.deleteById(id);  
//if(user==null)  
////runtime exception  
//throw new UserNotFoundException("id: "+ id);  
//}  


//method that posts a new user detail and returns the status of the user resource  
@PostMapping("/jpa/users")  
public ResponseEntity<Object> createUser(@Valid @RequestBody User user)     
{  
//User sevedUser=service.save(user); 
User sevedUser=userRepository.save(user);
URI location=ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(sevedUser.getId()).toUri();  
return ResponseEntity.created(location).build();  
}  
}  
