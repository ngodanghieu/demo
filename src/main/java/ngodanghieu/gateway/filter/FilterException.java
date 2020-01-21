package ngodanghieu.gateway.filter;

import ngodanghieu.gateway.model.response.ResponseConstant;
import ngodanghieu.gateway.model.response.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class FilterException {
    public static ResponseEntity<?> errorUserNotRole(ResponseDTO responseDTO){
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.UNAUTHORIZED);
    }
}
