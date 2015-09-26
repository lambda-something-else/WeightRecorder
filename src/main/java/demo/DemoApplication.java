package demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}

@Controller
class WeightMvcController {
	
	@Autowired
	private WeightRepository weightRepository;
	
	@RequestMapping("weights")
	String getWeights(Model model) {
		
		model.addAttribute("weights", this.weightRepository.findAll());
		return "weights";
	}

	@RequestMapping("weight")
	String putWeights(@RequestBody String name, Model model) {
		// test
		this.weightRepository.save(new Weight(name));
		model.addAttribute("weights", this.weightRepository.findAll());
		return "weights";
	}
}

@RepositoryRestResource
interface WeightRepository extends JpaRepository<Weight, Long> {
}

@Entity
class Weight {

	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	public Weight() {
	}
	
	public Weight(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}