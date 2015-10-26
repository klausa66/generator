package andreas.klaus.generator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;

public class ModelValidationDefClassData extends Helper {
  private final String packageName;
  private final String validationDefClass;
  private final SortedMap<String, ModelValidationFieldData> fieldMap;

  public ModelValidationDefClassData(String packageName, String validationDefClass,
      SortedMap<String, ModelValidationFieldData> fieldMap) {
    super();
    this.packageName = packageName;
    this.validationDefClass = validationDefClass;
    this.fieldMap=fieldMap;
  }

  public String getPackageName() {
    return packageName;
  }

  public String getValidationDefClassname() {
    return validationDefClass;
  }

  
  
  public SortedMap<String, ModelValidationFieldData> getFieldMap() {
    return fieldMap;
  }

  public String getValidatorClassname() {
    String orgName = validationDefClass;
    if (orgName.length() < 7) {
      return "BadValidationDefClassName_" + orgName;
    }
    return orgName.substring(0, orgName.length() - 5) + "Validator";

  }
  public String getDefInstanceName() {
    return firstCharToLowerCase(validationDefClass);
  }

  public  List<ModelValidationFieldData> getFieldProps() {
    List<ModelValidationFieldData> ret= new ArrayList<ModelValidationFieldData>();
    Iterator<ModelValidationFieldData> it= this.fieldMap.values().iterator();
    while (it.hasNext()) {
      ModelValidationFieldData modelFieldData = (ModelValidationFieldData) it.next();
      ret.add(modelFieldData);
    }
    return ret;
  }
  

  @Override
  public String toString() {
    return "ModelValidationDefClassData [packageName=" + packageName + ", validationDefClass=" + validationDefClass
        + ", fieldMap=" + fieldMap + "]";
  }

}
