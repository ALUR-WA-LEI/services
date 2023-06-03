package org.sola.admin.opentenure.services.ejbs.claim.businesslogic;

import java.util.List;
import javax.ejb.Local;
import org.sola.admin.opentenure.services.ejbs.claim.entities.AdministrativeBoundary;
import org.sola.admin.opentenure.services.ejbs.claim.entities.ClaimStatus;
import org.sola.admin.opentenure.services.ejbs.claim.entities.FieldConstraintType;
import org.sola.admin.opentenure.services.ejbs.claim.entities.FieldType;
import org.sola.admin.opentenure.services.ejbs.claim.entities.FieldValueType;
import org.sola.admin.opentenure.services.ejbs.claim.entities.FormTemplate;
import org.sola.services.common.ejbs.AbstractEJBLocal;

@Local
public interface ClaimAdminEJBLocal extends AbstractEJBLocal {
    List<ClaimStatus> getClaimStatuses(String languageCode);
    ClaimStatus getClaimStatus(String code, String languageCode);
    List<FormTemplate> getFormTemplates(String languageCode);
    FormTemplate getFormTemplate(String templateName, String languageCode);
    FormTemplate getDefaultFormTemplate(String languageCode);
    List<FieldType> getFieldTypes(String languageCode);
    List<FieldValueType> getFieldValueTypes(String languageCode);
    List<FieldConstraintType> getFieldConstraintTypes(String languageCode);
    boolean checkFormTemplateHasPayload(String formName);
    FormTemplate saveFormTemplate(FormTemplate form);
    boolean makeFormDefault(String formName);
    List<AdministrativeBoundary> getAdministrativeBoundaries(String statusCode, String typeCode);
}
