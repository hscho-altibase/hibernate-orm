/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.dialect.pagination;

import org.hibernate.engine.spi.RowSelection;

/**
 * Limit handler for Altibase
 *
 * @author YounJung Park
 */
public class AltibaseLimitHandler extends AbstractLimitHandler {
	public static final AltibaseLimitHandler INSTANCE = new AltibaseLimitHandler();

	/**
	 * Constructs a AltibaseLimitHandler
	 */
	private AltibaseLimitHandler() {
		// NOP
	}

	@Override
	public boolean supportsLimit() {
		return true;
	}

	@Override
	public String processSql(String sql, RowSelection selection) {
		if ( LimitHelper.useLimit( this, selection ) ) {
			final boolean useLimitOffset = LimitHelper.hasFirstRow( selection );
			if (useLimitOffset) {
				int firstRow = LimitHelper.getFirstRow(selection);
				selection.setFirstRow(firstRow + 1);   // In Altibase offset starts from 1
			}
			return sql + (useLimitOffset ? " limit ?, ? " : " limit ?");
		}
		else {
			// or return unaltered SQL
			return sql;
		}
	}
}
