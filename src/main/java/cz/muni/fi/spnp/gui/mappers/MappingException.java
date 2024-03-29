package cz.muni.fi.spnp.gui.mappers;

/**
 * Exception for the mapping errors.
 */
public class MappingException extends Throwable {

    private final String elementName;

    public MappingException(String elementName, Throwable cause) {
        super(cause);

        this.elementName = elementName;
    }

    public String getElementName() {
        return elementName;
    }

}
