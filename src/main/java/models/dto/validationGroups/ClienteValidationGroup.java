package models.dto.validationGroups;
import jakarta.validation.groups.Default;

public interface ClienteValidationGroup {
    interface Create extends Default {}
    interface Update extends Default {}
}