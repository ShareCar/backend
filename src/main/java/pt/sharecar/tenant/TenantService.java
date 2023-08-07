package pt.sharecar.tenant;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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
            throw new IllegalArgumentException(messages.invalidTenant());
        }

        try {
            repository.createNewSchema(tenantName);
        } catch (Exception e) {
            throw new Exception("Erro ao criar o esquema para o tenant: " + tenantName, e);
        }
    }

    private boolean isValidTenantName(String tenantName) {
        return tenantName.matches("^[a-zA-Z0-9]*$");
    }

}
