package pt.sharecar.tenant;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RealmRepresentation;
import pt.sharecar.messages.AppMessages;

@ApplicationScoped
public class TenantService {

    @Inject
    TenantRepository repository;

    @Inject
    AppMessages messages;



    @Transactional
    public void createSchema(String tenantName) throws Exception {
        if (!isValidTenantName(tenantName) || repository.schemaExists(tenantName)) {
            throw new IllegalArgumentException(messages.error_invalid_tenant());
        }

        try {
            //TODO: Handle the rollback if any exception occurs during realm creation.
            repository.createSchema(tenantName);
            repository.createRealm(tenantName);
        } catch (Exception e) {
            throw new Exception(messages.error_create_schema(tenantName), e);
        }
    }

    //TODO: Improve the regex, look for postgresql schema name rules
    private boolean isValidTenantName(String tenantName) {
        return tenantName.matches("^[a-zA-Z0-9]*$");
    }

}
