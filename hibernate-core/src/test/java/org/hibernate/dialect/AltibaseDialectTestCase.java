/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.dialect;

import junit.framework.TestCase;

/**
 * Testing of AltibaseDialect limit and offset queries
 *
 * @author YounJung Park
 */
public class AltibaseDialectTestCase extends TestCase {
	private Dialect dialect;
	private String query;

	public AltibaseDialectTestCase() {
		dialect = new AltibaseDialect();
		query = "select c1, c2 from t1 order by c1, c2 desc";
	}

	public void testSupportLimits() {
		assertEquals(dialect.supportsLimit(), true);
	}

	public void testSelectWithSingleLimit() {
		assertEquals( "select c1, c2 from t1 order by c1, c2 desc limit 1, 10", dialect.getLimitString( query, 0, 10 ) );
	}

	public void testSelectWithOffsetLimit() {
		assertEquals( "select c1, c2 from t1 order by c1, c2 desc limit 5, 10", dialect.getLimitString( query, 5, 15 ) );
	}
}
