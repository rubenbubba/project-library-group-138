package be.ucll.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;

@Entity
@DiscriminatorValue("MAGAZINE")
public class Magazine extends Publication {

    @NotBlank(message = "Editor is required.")
    private String editor;

    @NotBlank(message = "ISSN is required.")
    private String issn;

    protected Magazine() { }   // JPA

    public Magazine(String title, int year, int copies,
                    String editor, String issn) {
        super(title, year, copies);
        this.editor = editor;
        this.issn   = issn;
    }

    public String getEditor() { return editor; }
    public String getIssn()   { return issn;   }
}
