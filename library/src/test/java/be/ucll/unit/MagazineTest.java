package be.ucll.unit;

import be.ucll.model.Magazine;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.*;

class   MagazineTest {

    private static final Validator validator =
            Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void validMagazine_ok() {
        Magazine m = new Magazine("NatGeo","Editor","0027-9358",2023,4);
        Assertions.assertTrue(validator.validate(m).isEmpty());
    }

    @Test
    void blankEditor_violation() {
        Magazine m = new Magazine("NatGeo","", "0027-9358",2023,4);
        Assertions.assertFalse(validator.validate(m).isEmpty());
    }
}
