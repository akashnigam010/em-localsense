package in.cw.sense.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class PersonController {

	@RequestMapping(value = "/personinfo/{pid}", method = RequestMethod.POST, headers = "Accept=application/json")
	public String hello(@PathVariable String pid) {
		System.out.println("Welcome Friends!");
		return "result";
	}

	@RequestMapping(value = "/personinfo/save/info/{name}", method = RequestMethod.POST, headers = "Accept=application/json")
	public String savePerson(@PathVariable String name) {
		System.out.println("Person Saved, name:" + name);
		return "result";
	}
}