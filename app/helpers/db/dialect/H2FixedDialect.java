package helpers.db.dialect;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.metamodel.spi.TypeContributions;
import org.hibernate.service.ServiceRegistry;

public class H2FixedDialect extends H2Dialect {
    @Override
    public void contributeTypes(TypeContributions typeContributions, ServiceRegistry serviceRegistry) {
        super.contributeTypes( typeContributions, serviceRegistry );

        registerHibernateType(-2, H2UUIDType.INSTANCE.getName());

        // HHH-9562
        typeContributions.contributeType( H2UUIDType.INSTANCE );
    }
}
