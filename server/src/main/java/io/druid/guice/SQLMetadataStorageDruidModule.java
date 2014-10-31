/*
 * Druid - a distributed column store.
 * Copyright (C) 2014  Metamarkets Group Inc.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package io.druid.guice;

import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.Module;
import io.druid.db.IndexerSQLMetadataStorageCoordinator;
import io.druid.db.MetadataRuleManager;
import io.druid.db.MetadataRuleManagerProvider;
import io.druid.db.MetadataSegmentManager;
import io.druid.db.MetadataSegmentManagerProvider;
import io.druid.db.MetadataSegmentPublisherProvider;
import io.druid.db.MetadataStorageConnector;
import io.druid.db.SQLMetadataConnector;
import io.druid.db.SQLMetadataRuleManager;
import io.druid.db.SQLMetadataRuleManagerProvider;
import io.druid.db.SQLMetadataSegmentManager;
import io.druid.db.SQLMetadataSegmentManagerProvider;
import io.druid.db.SQLMetadataSegmentPublisher;
import io.druid.db.SQLMetadataSegmentPublisherProvider;
import io.druid.db.SQLMetadataStorageActionHandlerFactory;
import io.druid.indexer.MetadataStorageUpdaterJobHandler;
import io.druid.indexer.SQLMetadataStorageUpdaterJobHandler;
import io.druid.indexing.overlord.IndexerMetadataStorageCoordinator;
import io.druid.indexing.overlord.MetadataStorageActionHandlerFactory;
import io.druid.segment.realtime.SegmentPublisher;

public class SQLMetadataStorageDruidModule implements Module
{
  final String type;

  public SQLMetadataStorageDruidModule(String type)
  {
    this.type = type;
  }

  @Override
  public void configure(Binder binder)
  {
    PolyBind.createChoice(
        binder, "druid.db.type", Key.get(MetadataStorageConnector.class), null
    );
    PolyBind.createChoice(
        binder, "druid.db.type", Key.get(SQLMetadataConnector.class), null
    );
    PolyBind.createChoice(
        binder, "druid.db.type", Key.get(MetadataSegmentManager.class), Key.get(SQLMetadataSegmentManager.class)
    );
    PolyBind.createChoice(
        binder, "druid.db.type", Key.get(MetadataSegmentManagerProvider.class), Key.get(SQLMetadataSegmentManagerProvider.class)
    );
    PolyBind.createChoice(
        binder, "druid.db.type", Key.get(MetadataRuleManager.class), Key.get(SQLMetadataRuleManager.class)
    );
    PolyBind.createChoice(
        binder, "druid.db.type", Key.get(MetadataRuleManagerProvider.class), Key.get(SQLMetadataRuleManagerProvider.class)
    );
    PolyBind.createChoice(
        binder, "druid.db.type", Key.get(SegmentPublisher.class), Key.get(SQLMetadataSegmentPublisher.class)
    );
    PolyBind.createChoice(
        binder, "druid.db.type", Key.get(MetadataSegmentPublisherProvider.class), Key.get(SQLMetadataSegmentPublisherProvider.class)
    );
    PolyBind.createChoice(
        binder, "druid.db.type", Key.get(IndexerMetadataStorageCoordinator.class), Key.get(IndexerSQLMetadataStorageCoordinator.class)
    );
    PolyBind.createChoice(
        binder, "druid.db.type", Key.get(MetadataStorageActionHandlerFactory.class), Key.get(SQLMetadataStorageActionHandlerFactory.class)
    );
    PolyBind.createChoice(
        binder, "druid.db.type", Key.get(MetadataStorageUpdaterJobHandler.class), Key.get(SQLMetadataStorageUpdaterJobHandler.class)
    );

    PolyBind.optionBinder(binder, Key.get(MetadataSegmentManager.class))
            .addBinding(type)
            .to(SQLMetadataSegmentManager.class)
            .in(LazySingleton.class);

    PolyBind.optionBinder(binder, Key.get(MetadataSegmentManagerProvider.class))
            .addBinding(type)
            .to(SQLMetadataSegmentManagerProvider.class)
            .in(LazySingleton.class);

    PolyBind.optionBinder(binder, Key.get(MetadataRuleManager.class))
            .addBinding(type)
            .to(SQLMetadataRuleManager.class)
            .in(LazySingleton.class);

    PolyBind.optionBinder(binder, Key.get(MetadataRuleManagerProvider.class))
            .addBinding(type)
            .to(SQLMetadataRuleManagerProvider.class)
            .in(LazySingleton.class);

    PolyBind.optionBinder(binder, Key.get(SegmentPublisher.class))
            .addBinding(type)
            .to(SQLMetadataSegmentPublisher.class)
            .in(LazySingleton.class);

    PolyBind.optionBinder(binder, Key.get(MetadataSegmentPublisherProvider.class))
            .addBinding(type)
            .to(SQLMetadataSegmentPublisherProvider.class)
            .in(LazySingleton.class);

    PolyBind.optionBinder(binder, Key.get(MetadataStorageActionHandlerFactory.class))
            .addBinding(type)
            .to(SQLMetadataStorageActionHandlerFactory.class)
            .in(LazySingleton.class);

    PolyBind.optionBinder(binder, Key.get(IndexerMetadataStorageCoordinator.class))
            .addBinding(type)
            .to(IndexerSQLMetadataStorageCoordinator.class)
            .in(LazySingleton.class);

    PolyBind.optionBinder(binder, Key.get(MetadataStorageUpdaterJobHandler.class))
            .addBinding(type)
            .to(SQLMetadataStorageUpdaterJobHandler.class)
            .in(LazySingleton.class);
  }
}
