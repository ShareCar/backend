package pt.sharecar.tenant;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RealmRepresentation;

@ApplicationScoped
public class TenantRepository {

    @Inject
    EntityManager em;

    @Inject
    Keycloak keycloak;

    public boolean schemaExists(String tenantName) {
        String queryStr = "SELECT COUNT(schema_name) FROM information_schema.schemata WHERE schema_name = :tenantName";
        Query query = em.createNativeQuery(queryStr).setParameter("tenantName", tenantName);
        Long result = (Long) query.getSingleResult();
        return result != null && result > 0;
    }

    @Transactional
    public void createSchema(String tenantName) {
        String query = "CREATE SCHEMA " + tenantName;
        em.createNativeQuery(query).executeUpdate();
    }

    public void createRealm(String tenantName) {
        RealmRepresentation realmRepresentation = new RealmRepresentation();
        realmRepresentation.setRealm(tenantName);
        realmRepresentation.setEnabled(true);
        keycloak.realms().create(realmRepresentation);
    }
}
