package demo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @ModelAttribute
    public WeightForm setUpForm() {
    	WeightForm form = new WeightForm();
        return form;
    }

    @RequestMapping("weights")
	String getWeights(Model model) {
		
		model.addAttribute("weights", this.weightRepository.findAll());
		return "weights";
	}

	@RequestMapping(value="weight", method=RequestMethod.POST)
	String putWeights(@Valid WeightForm form, BindingResult bindingResult, // (3)
            Model model, RedirectAttributes attributes) {
		
		Weight weight = new Weight();
		weight.setRecordDate(new Date());
		weight.setWeight1(Integer.parseInt(form.getWeight1()));
		weight.setWeight2(Integer.parseInt(form.getWeight2()));

		this.weightRepository.save(weight);
		return "complete";
	}
}

@RepositoryRestResource
interface WeightRepository extends JpaRepository<Weight, Long> {
}

@Entity
class Weight {

	@Id
	private Date recordDate;

	@NotNull
	@Min(0)
	@Max(999)
	private Integer weight1;

	@NotNull
	@Min(0)
	@Max(99)
	private Integer weight2;

	public Weight() {
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public void setWeight1(Integer weight1) {
		this.weight1 = weight1;
	}

	public void setWeight2(Integer weight2) {
		this.weight2 = weight2;
	}

	public Integer getWeight1() {
		return weight1;
	}

	public Integer getWeight2() {
		return weight2;
	}
}

class WeightForm implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String recordDate;
    private String weight1;
    private String weight2;
    
	public String getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}
	public String getWeight1() {
		return weight1;
	}
	public void setWeight1(String weight1) {
		this.weight1 = weight1;
	}
	public String getWeight2() {
		return weight2;
	}
	public void setWeight2(String weight2) {
		this.weight2 = weight2;
	}
}