package ngodanghieu.gateway.model.response;

public class ResponseConstant {
    public static ResponseDTO ERROR_USER_NOT_ROLE = new ResponseDTO("401001","User Không được phép truy cập chức năng");

    public static ResponseDTO responseOK(String user_name){ return new ResponseDTO("200000","Thành công",user_name);}

    public static ResponseDTO responseOK(Object object){ return new ResponseDTO("200000","Thành công",object);}
    public static ResponseDTO responseOK(String user_name, Object object){ return new ResponseDTO("200000","Thành công",user_name,object);}
    public static ResponseDTO responseError401(String user_name){ return new ResponseDTO("401001","Sai key connect tại pay-maker-checker");}
}
