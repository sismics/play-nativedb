package helpers.db.dialect;

import org.hibernate.metamodel.spi.TypeContributions;
import org.hibernate.service.ServiceRegistry;

public class PostgreSQLFixedDialect extends org.hibernate.dialect.PostgreSQL82Dialect {
    @Override
    public void contributeTypes(TypeContributions typeContributions, ServiceRegistry serviceRegistry) {
        super.contributeTypes( typeContributions, serviceRegistry );

        registerHibernateType(1111, PostgresUUIDType.INSTANCE.getName());

        // HHH-9562
        typeContributions.contributeType( PostgresUUIDType.INSTANCE );
    }
}
