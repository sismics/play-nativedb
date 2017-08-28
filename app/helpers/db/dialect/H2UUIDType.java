package helpers.db.dialect;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.java.UUIDTypeDescriptor;
import org.hibernate.type.descriptor.sql.BasicBinder;
import org.hibernate.type.descriptor.sql.BasicExtractor;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class H2UUIDType extends AbstractSingleColumnStandardBasicType<UUID> {
    public static final H2UUIDType INSTANCE = new H2UUIDType();

    public H2UUIDType() {
        super(H2UUIDType.H2UUIDSqlTypeDescriptor.INSTANCE, UUIDTypeDescriptor.INSTANCE);
    }

    public String getName() {
        return "helpers.db.dialect.H2UUIDType";
    }

    public static class H2UUIDSqlTypeDescriptor implements SqlTypeDescriptor {
        public static final H2UUIDType.H2UUIDSqlTypeDescriptor INSTANCE = new H2UUIDType.H2UUIDSqlTypeDescriptor();

        public H2UUIDSqlTypeDescriptor() {
        }

        public int getSqlType() {
            return -2;
        }

        public boolean canBeRemapped() {
            return true;
        }

        public <X> ValueBinder<X> getBinder(final JavaTypeDescriptor<X> javaTypeDescriptor) {
            return new BasicBinder<X>(javaTypeDescriptor, this) {
                protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options) throws SQLException {
                    st.setObject(index, javaTypeDescriptor.unwrap(value, UUID.class, options), H2UUIDType.H2UUIDSqlTypeDescriptor.this.getSqlType());
                }
            };
        }

        public <X> ValueExtractor<X> getExtractor(final JavaTypeDescriptor<X> javaTypeDescriptor) {
            return new BasicExtractor<X>(javaTypeDescriptor, this) {
                protected X doExtract(ResultSet rs, String name, WrapperOptions options) throws SQLException {
                    return javaTypeDescriptor.wrap(rs.getObject(name), options);
                }
            };
        }
    }
}
