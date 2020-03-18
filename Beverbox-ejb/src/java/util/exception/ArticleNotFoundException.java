package util.exception;

/**
 *
 * @author vanes
 */
public class ArticleNotFoundException extends Exception {

   
    public ArticleNotFoundException() {
    }

    
    public ArticleNotFoundException(String msg) {
        super(msg);
    }
}
