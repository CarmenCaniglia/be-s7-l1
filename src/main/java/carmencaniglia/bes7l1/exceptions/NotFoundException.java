package carmencaniglia.bes7l1.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(long id){
        super("Elemento con id " + id+ " non trovato!");
    }
}
