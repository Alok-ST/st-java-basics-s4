package com.example.demo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}


// GET , POST , PUT, DELETE, PATCH


@RestController
class CandidateController{

	@Autowired
    private CandidateRepo repo;
	
	private Map<Integer,Candidate> candidates = new HashMap<>();
	
	@GetMapping("/candidates")
	public Collection<Candidate> candidates(){
		return repo.findAll();
	}
	
	@PostMapping("/candidate")
	public Candidate addCandidate(@RequestBody Candidate c) {
		
		return repo.save(c);
		//candidates.put(c.getId(),c);
	}
	
	@PutMapping("/candidate")
	public void updateCandidate(@RequestBody Candidate c) {
		
		candidates.put(c.getCandidateId(),c);
		
	}
	
	@PatchMapping("/candidate")
	public void patchCandidate(@RequestBody Candidate c) {
		
		Candidate candidateInDB = candidates.get(c.getCandidateId());
		
		if(c.getFname()!=null) {
			candidateInDB.setFname(c.getFname());
		}
		
		if(c.getLname()!=null) {
			candidateInDB.setLname(c.getLname());
		}
		
		if(c.getPhoneNumber()!=null) {
			candidateInDB.setPhoneNumber(c.getPhoneNumber());
		}
	}
	
	@DeleteMapping("/candidate")
	public void deleteCandidate(@RequestBody Candidate c) {
		
		candidates.remove(c.getCandidateId());
		
	}
	
	
	
	
	
	@GetMapping("/candidate/{id}")
	public Candidate candidate(@PathVariable Integer id){
		return repo.getOne(id);
	}

	@PostConstruct
	public void init() {
		
		candidates.put(1,new Candidate(1,"Alok", "Sharma", "999999999"));
		candidates.put(2,new Candidate(2,"Nishant", "Kanungo", "999999990"));
		candidates.put(3,new Candidate(3,"John", "Doe", "999999992"));
	}
	
}




//@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Entity
@Table(name = "candidate")
class Candidate{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="candidate_id")
	private Integer candidateId;
	@Column(name="f_name")
	private String fname;
	@Column(name="l_name")
	private String lname;
	@Column(name="phone_number")
	private String phoneNumber;
	
	public Candidate() {}
	
	
	public Candidate(int candidateId, String fname, String lname, String phoneNumber) {
		super();
		this.candidateId = candidateId;
		this.fname = fname;
		this.lname = lname;
		this.phoneNumber = phoneNumber;
	}
	
	public Integer getCandidateId() {
		return candidateId;
	}


	public void setCandidateId(Integer candidateId) {
		this.candidateId = candidateId;
	}


	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	

}