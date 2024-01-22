package carmencaniglia.bes7l1.exceptions;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message){
        super(message);
}
