package pt.sharecar.tenant;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TenantService {

    @Inject
    TenantRepository repository;

    @Transactional
    public void createSchema(String tenantName) throws Exception {
        if (!isValidTenantName(tenantName) || repository.schemaExists(tenantName)) {
            throw new IllegalArgumentException("O nome do tenant é inválido ou já existe.");
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
