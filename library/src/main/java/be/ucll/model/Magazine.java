package be.ucll.model;

import java.time.LocalDate;

public class Magazine {
    private String title;
    private String editor;
    private String issn;
    private int publicationYear;

    public Magazine(String title, String editor, String issn, int publicationYear) {
        setTitle(title);
        setEditor(editor);
        setIssn(issn);
        setPublicationYear(publicationYear);
    }

    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        if(title == null || title.trim().isEmpty()){
            throw new RuntimeException("Title is required.");
        }
        this.title = title;
    }

    public String getEditor() {
        return this.editor;
    }
    public void setEditor(String editor) {
        if(editor == null || editor.trim().isEmpty()){
            throw new RuntimeException("Editor is required.");
        }
        this.editor = editor;
    }

    public String getIssn() {
        return this.issn;
    }
    public void setIssn(String issn) {
        if(issn == null || issn.trim().isEmpty()){
            throw new RuntimeException("Issn is required.");
        }
        this.issn = issn;
    }

    public int getPublicationYear() {
        return this.publicationYear;
    }
    public void setPublicationYear(int publicationYear) {
        if(publicationYear < 0) {
            throw new RuntimeException("Publication year must be a positive integer.");
        } else if(publicationYear > LocalDate.now().getYear()) {
            throw new RuntimeException("Publication year cannot be in the future.");
        }
        this.publicationYear = publicationYear;
    }


}
