//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b01 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2019.10.03 � 02:29:13 PM WET 
//


package beans;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the beans package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@SuppressWarnings("restriction")
@XmlRegistry
public class ObjectFactory {

    private final static QName _UserId_QNAME = new QName("", "UserId");
    private final static QName _MasterPass_QNAME = new QName("", "MasterPass");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: beans
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Password }
     * 
     */
    public Password createPassword() {
        return new Password();
    }

    /**
     * Create an instance of {@link AccountDataBase }
     * 
     */
    public AccountDataBase createAccountDataBase() {
        return new AccountDataBase();
    }

    /**
     * Create an instance of {@link Account }
     * 
     */
    public Account createAccount() {
        return new Account();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "UserId")
    public JAXBElement<String> createUserId(String value) {
        return new JAXBElement<String>(_UserId_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "MasterPass")
    public JAXBElement<String> createMasterPass(String value) {
        return new JAXBElement<String>(_MasterPass_QNAME, String.class, null, value);
    }

}
