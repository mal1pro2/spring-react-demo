package com.moodys.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.moodys.demo.model.Employee;
import com.moodys.demo.service.EmployeeService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(value = { "/" }, method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public void create(@RequestBody Employee e) {
		employeeService.create(e);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Mono<Employee>> findById(@PathVariable("id") Integer id) {
		Mono<Employee> e = employeeService.findById(id);
		HttpStatus status = e != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<Mono<Employee>>(e, status);
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<Employee> findAll() {
        Flux<Employee> emps = employeeService.findAll();
        return emps;
    }

	@RequestMapping(value = "/", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<Mono<Employee>> update(@RequestBody Employee e) {
		Mono<Employee> emp = employeeService.update(e);
		HttpStatus status = emp != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<Mono<Employee>>(emp, status);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ResponseBody
	public void delete(@PathVariable("id") Integer id) {
		employeeService.delete(id).subscribe();
	}
}
