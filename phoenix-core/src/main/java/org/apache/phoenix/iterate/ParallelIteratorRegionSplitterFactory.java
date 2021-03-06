/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.phoenix.iterate;

import java.sql.SQLException;

import org.apache.phoenix.compile.StatementContext;
import org.apache.phoenix.parse.HintNode;
import org.apache.phoenix.schema.TableRef;
import org.apache.phoenix.schema.PTable.IndexType;


/**
 * Factory class for the Region Splitter used by the project.
 */
public class ParallelIteratorRegionSplitterFactory {

    public static ParallelIteratorRegionSplitter getSplitter(StatementContext context, TableRef table, HintNode hintNode) throws SQLException {
        if(table.getTable().getIndexType() == IndexType.LOCAL) {
            return LocalIndexParallelIteratorRegionSplitter.getInstance(context, table, hintNode);
        }
        if (context.getScanRanges().useSkipScanFilter()) {
            return SkipRangeParallelIteratorRegionSplitter.getInstance(context, table, hintNode);
        }
        return DefaultParallelIteratorRegionSplitter.getInstance(context, table, hintNode);
    }
}
