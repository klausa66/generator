package andreas.klaus.generator;

import andreas.klaus.generator.annotations.ValidationDefClass;
import andreas.klaus.generator.annotations.ValidationField;
@ValidationDefClass
public class TestValidation2VPojo
{
	@ValidationField 
	private String mandatoryField;

//	@ValidationField(notEmpty=false, length=2, integer=true )
//	private int number;
	
	private Object irgendwas;

	public String getMandatoryField() {
		return mandatoryField;
	}

	public void setMandatoryField(String mandatoryField) {
		this.mandatoryField = mandatoryField;
	}



	public Object getIrgendwas() {
		return irgendwas;
	}

	public void setIrgendwas(Object irgendwas) {
		this.irgendwas = irgendwas;
	}
	
	
}
