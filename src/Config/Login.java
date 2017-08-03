package Config;

public class Login {
    
    public class Xpath{
        // text box username
        public static final String TXT_USERNAME = "//input[contains(@id, 'Login1_txtUserName')]";
        
        // text box Password
        public static final String TXT_PASSWORD = "//input[contains(@id, 'txtPass')]";
        
        // button Login
        public static final String BTN_LOGIN = "//input[contains(@id, 'Login1_btnLogin')]";
        
        public static final String DD_ChiNhanh=".//*[@id='ddcl-ctl00_Footer1_ddlDropDownCheckListDivision_ddlCheck']/span";
        
        public static final String DD_AllChiNhanh = "//input[contains(@id, 'Footer1_ddlDropDownCheckListDivision_ddlCheck-i0')]";
        
        public static final String DD_CuThe = "//input[contains(@id, 'Footer1_ddlDropDownCheckListDivision_ddlCheck-i2')]";
    }
    
    public class Name{
        public static final String BTN_ReFresh = "ctl00$Footer1$ctl06";
    }
    //Login1_
    

}
