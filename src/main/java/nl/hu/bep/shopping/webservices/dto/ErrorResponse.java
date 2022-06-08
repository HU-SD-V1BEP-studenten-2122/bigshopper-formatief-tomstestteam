package nl.hu.bep.shopping.webservices.dto;

public class ErrorResponse {
    public String error;

    public static ErrorResponse fromString(String errorMelding){
        ErrorResponse result = new ErrorResponse();
        result.error = errorMelding;

        return result;
    }
}
