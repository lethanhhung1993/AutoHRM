package Pages.ATT;

import Config.Config;

public class NgayNghi2 extends Config {
    
    public String value;
    public String value1;
    public String value2;
    public String value2333;
    
    public NgayNghi2(String className) {
        super(className);
        // TODO Auto-generated constructor stub
    }

    public class Xpath{
        public static final String Txt_TenNV="/input[aria-owns='VnrSelectProfileOrOrgStructure_Profile_SelectProfileOrOrgStructureCreateLeaveDayInfo_taglist VnrSelectProfileOrOrgStructure_Profile_SelectProfileOrOrgStructureCreateLeaveDayInfo_listbox']";
  
    }
    
    public void fillform(){
        SetAndSelectValueByXpath(Xpath.Txt_TenNV, value);
        System.out.println("ãndfsfs");
    }
    
    
}
