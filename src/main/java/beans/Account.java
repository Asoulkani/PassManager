//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b01 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2019.10.03 � 02:29:13 PM WET 
//


package beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}UserId"/>
 *         &lt;element ref="{}MasterPass"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "userId",
    "masterPass"
})
@XmlRootElement(name = "account")
public class Account {

    @XmlElement(name = "UserId", required = true)
    protected String userId;
    @XmlElement(name = "MasterPass", required = true)
    protected String masterPass;

    /**
     * Obtient la valeur de la propri�t� userId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * D�finit la valeur de la propri�t� userId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserId(String value) {
        this.userId = value;
    }

    /**
     * Obtient la valeur de la propri�t� masterPass.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMasterPass() {
        return masterPass;
    }

    /**
     * D�finit la valeur de la propri�t� masterPass.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMasterPass(String value) {
        this.masterPass = value;
    }

}
