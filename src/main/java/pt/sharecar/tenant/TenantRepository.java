package pt.sharecar.tenant;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TenantRepository {

    @Inject
    EntityManager em;

    public boolean schemaExists(String tenantName) {
        String queryStr = "SELECT COUNT(schema_name) FROM information_schema.schemata WHERE schema_name = :tenantName";
        Query query = em.createNativeQuery(queryStr).setParameter("tenantName", tenantName);
        Long result = (Long) query.getSingleResult();
        return result != null && result > 0;
    }

    @Transactional
    public void createNewSchema(String tenantName) {
        String query = "CREATE SCHEMA " + tenantName;
        em.createNativeQuery(query).executeUpdate();
    }
}
