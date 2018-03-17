package controleur.erreurs;

/**
 * @author Antho
 */
public class LoginDejaPris extends Exception {
    public LoginDejaPris(String s) {
        super(s);
    }
}
