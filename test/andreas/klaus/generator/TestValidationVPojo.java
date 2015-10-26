package andreas.klaus.generator;

import andreas.klaus.generator.annotations.ValidationDefClass;
import andreas.klaus.generator.annotations.ValidationField;
@ValidationDefClass
public class TestValidationVPojo
{
	@ValidationField 
	private String mandatoryField;

	@ValidationField(notEmpty=false,shortDateFormat=true)
	private String dateStringD2;
	
	private Object irgendwas;

	public String getMandatoryField() {
		return mandatoryField;
	}

	public void setMandatoryField(String mandatoryField) {
		this.mandatoryField = mandatoryField;
	}

	public String getDateStringD2() {
		return dateStringD2;
	}

	public void setDateStringD2(String dateStringD2) {
		this.dateStringD2 = dateStringD2;
	}

	public Object getIrgendwas() {
		return irgendwas;
	}

	public void setIrgendwas(Object irgendwas) {
		this.irgendwas = irgendwas;
	}
	
	
}
