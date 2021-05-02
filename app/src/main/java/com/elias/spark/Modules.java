package com.elias.spark;

import org.flywaydb.core.Flyway;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Slf4JSqlLogger;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public final class Modules {

	static class DatabaseModule extends AbstractModule {
		@Override
		protected void configure() {
		}

		@Singleton
		@Provides
		static Jdbi provideDataSource() {
			Jdbi jdbi = Jdbi.create("jdbc:mysql://127.0.0.1:3306/sparkcustomerdb", "root", "root")
			                .setSqlLogger(new Slf4JSqlLogger());
			jdbi.installPlugin(new SqlObjectPlugin());
			return jdbi;
		}

		@Singleton
		@Provides
		private static Flyway provideFlyway()
		        throws InstantiationException, IllegalAccessException, ClassNotFoundException {
			var flyway = Flyway.configure()
			                   .dataSource("jdbc:mysql://127.0.0.1:3306/sparkcustomerdb", "root", "root")
			                   .load();
			return flyway;
		}

	}
}