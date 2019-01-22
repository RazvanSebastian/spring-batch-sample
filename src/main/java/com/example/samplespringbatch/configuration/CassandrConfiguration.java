package com.example.samplespringbatch.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraEntityClassScanner;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.AsyncCassandraOperations;
import org.springframework.data.cassandra.core.AsyncCassandraTemplate;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import com.datastax.driver.core.Session;

/**
 * 
 * @author razvan.parautiu
 *
 *         Using @CassandraTemplate with object mapping and repository support
 *         requires
 *         a @CassandraTemplate, @CassandraMappingContext, @CassandraConverter,
 *         and enabling repository support.
 * 
 */

@Configuration
@EnableCassandraRepositories(basePackages = { "com.example.samplespringbatch.repository" })
public class CassandrConfiguration {

	@Value("${cassandra.configuration.contactPoint}")
	private String contactPoint;

	@Value("${cassandra.configuration.port}")
	private int port;

	@Value("${cassandra.configuration.keyspace}")
	private String keyspace;

	@Bean
	public CassandraClusterFactoryBean cluster() {
		CassandraClusterFactoryBean factoryBean = new CassandraClusterFactoryBean();
		factoryBean.setContactPoints(contactPoint);
		factoryBean.setPort(port);
		return factoryBean;
	}

	@Bean
	public CassandraMappingContext cassandraMappingContext(CassandraClusterFactoryBean cluster) throws ClassNotFoundException {
		CassandraMappingContext cassandraMappingContext = new CassandraMappingContext();
		cassandraMappingContext.setUserTypeResolver(new SimpleUserTypeResolver(cluster.getObject(), keyspace));
		cassandraMappingContext.setInitialEntitySet(CassandraEntityClassScanner.scan("com.example.samplespringbatch.model"));
		
		return cassandraMappingContext;
	}

	@Bean
	public CassandraConverter cassandraConverter(CassandraMappingContext cassandraMappingContext) {
		return new MappingCassandraConverter(cassandraMappingContext);
	}

	@Bean
	public CassandraSessionFactoryBean session(CassandraClusterFactoryBean cluster,
			CassandraConverter cassandraConverter) {
		CassandraSessionFactoryBean sessionFactoryBean = new CassandraSessionFactoryBean();
		sessionFactoryBean.setCluster(cluster.getObject());
		sessionFactoryBean.setConverter(cassandraConverter);
		sessionFactoryBean.setKeyspaceName(keyspace);
		sessionFactoryBean.setSchemaAction(SchemaAction.CREATE_IF_NOT_EXISTS);

		return sessionFactoryBean;
	}

	@Bean
	public CassandraOperations cassandraTemplate(CassandraSessionFactoryBean session) {
		return new CassandraTemplate(session.getObject());
	}

	@Bean
	public AsyncCassandraOperations asyncCassandraTemplate(CassandraSessionFactoryBean session) {
		return new AsyncCassandraTemplate(session.getObject());
	}
}
