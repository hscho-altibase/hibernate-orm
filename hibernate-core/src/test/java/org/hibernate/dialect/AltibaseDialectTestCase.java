/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.dialect;

import org.hibernate.dialect.pagination.AltibaseLimitHandler;
import org.hibernate.engine.spi.RowSelection;
import org.hibernate.testing.junit4.BaseUnitTestCase;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Testing of AltibaseDialect limit and offset queries
 *
 * @author YounJung Park
 */
public class AltibaseDialectTestCase extends BaseUnitTestCase {
	private Dialect dialect;
	private String query;

	public AltibaseDialectTestCase() {
		dialect = new AltibaseDialect();
		query = "select c1, c2 from t1 order by c1, c2 desc";
	}

	@Test
	public void testSupportLimits() {
		AltibaseLimitHandler limitHandler = new AltibaseLimitHandler(dialect, query, null);
		assertEquals(limitHandler.supportsLimit(), true);
	}

	@Test
	public void testSelectWithSingleLimit() {
		AltibaseLimitHandler limitHandler = new AltibaseLimitHandler( dialect, query, toRowSelection( 0, 15 ) );
		assertEquals( "select c1, c2 from t1 order by c1, c2 desc limit ?", limitHandler.getProcessedSql() );
	}

	@Test
	public void testSelectWithOffsetLimit() {
		AltibaseLimitHandler limitHandler = new AltibaseLimitHandler( dialect, query, toRowSelection( 5, 15 ) );
		assertEquals( "select c1, c2 from t1 order by c1, c2 desc limit ?, ?", limitHandler.getProcessedSql() );
	}

	@Test
	public void testSelectWithNoLimit() {
		AltibaseLimitHandler limitHandler = new AltibaseLimitHandler( dialect, query, null );
		assertEquals( "select c1, c2 from t1 order by c1, c2 desc", limitHandler.getProcessedSql() );
	}

	private RowSelection toRowSelection( int firstRow, int maxRows ) {
		RowSelection selection = new RowSelection();
		selection.setFirstRow( firstRow );
		selection.setMaxRows( maxRows );
		return selection;
	}
}
