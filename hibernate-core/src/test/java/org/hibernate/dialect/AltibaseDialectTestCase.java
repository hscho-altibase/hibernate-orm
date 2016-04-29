/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.dialect;

import java.util.Locale;
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

	public AltibaseDialectTestCase() {
		dialect = new AltibaseDialect();
	}

	@Test
	public void testSupportLimits() {
		assertEquals(dialect.getLimitHandler().supportsLimit(), true);
	}

	@Test
	public void testSelectWithSingleLimit() {
		assertEquals( "select c1, c2 from t1 order by c1, c2 desc limit ?",
				dialect.getLimitHandler().processSql("select c1, c2 from t1 order by c1, c2 desc",
						toRowSelection( 0, 15 ) ).toLowerCase(Locale.ROOT));
	}

	@Test
	public void testSelectWithOffsetLimit() {
		assertEquals( "select c1, c2 from t1 order by c1, c2 desc limit ?, ?",
				dialect.getLimitHandler().processSql("select c1, c2 from t1 order by c1, c2 desc",
						toRowSelection( 5, 15 ) ).toLowerCase(Locale.ROOT));
	}

	@Test
	public void testSelectWithNoLimit() {
		assertEquals( "select c1, c2 from t1 order by c1, c2 desc",
				dialect.getLimitHandler().processSql("select c1, c2 from t1 order by c1, c2 desc",
						null ).toLowerCase(Locale.ROOT));
	}

	private RowSelection toRowSelection( int firstRow, int maxRows ) {
		RowSelection selection = new RowSelection();
		selection.setFirstRow( firstRow );
		selection.setMaxRows( maxRows );
		return selection;
	}
}