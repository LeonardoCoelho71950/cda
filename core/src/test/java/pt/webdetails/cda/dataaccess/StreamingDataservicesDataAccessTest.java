/*!
 * Copyright 2018 Webdetails, a Hitachi Vantara company. All rights reserved.
 *
 * This software was developed by Webdetails and is provided under the terms
 * of the Mozilla Public License, Version 2.0, or any later version. You may not use
 * this file except in compliance with the license. If you need a copy of the license,
 * please go to  http://mozilla.org/MPL/2.0/. The Initial Developer is Webdetails.
 *
 * Software distributed under the Mozilla Public License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. Please refer to
 * the license for the specific language governing your rights and limitations.
 */

package pt.webdetails.cda.dataaccess;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.reporting.engine.classic.core.modules.misc.datafactory.sql.ConnectionProvider;
import org.pentaho.reporting.engine.classic.core.modules.misc.datafactory.sql.SQLReportDataFactory;
import pt.webdetails.cda.connections.ConnectionCatalog;
import pt.webdetails.cda.connections.InvalidConnectionException;
import pt.webdetails.cda.connections.dataservices.DataservicesConnection;
import pt.webdetails.cda.settings.UnknownConnectionException;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static pt.webdetails.cda.test.util.CdaTestHelper.getMockEnvironment;
import static pt.webdetails.cda.test.util.CdaTestHelper.initBareEngine;


public class StreamingDataservicesDataAccessTest extends TestCase {

  StreamingDataservicesDataAccess da;

  @Before
  public void setUp() {
    initBareEngine( getMockEnvironment() );
    da = new StreamingDataservicesDataAccess();
  }

  @Test
  public void testGetType() {
    assertEquals( da.getType(), "streaming" );
  }

  @Test
  public void testGetLabel() {
    assertEquals( da.getLabel(), "streaming" );
  }

  @Test
  public void testGetConnectionType() {
    assertEquals( da.getConnectionType(), ConnectionCatalog.ConnectionType.DATASERVICES );
  }

  @Test
  public void testGetInterface() {
    List<PropertyDescriptor> daInterface = da.getInterface();

    List<PropertyDescriptor> dataServiceNameProperty = daInterface.stream()
      .filter( p -> p.getName().equals( "streamingDataServiceName" ) ).collect( Collectors.toList() );
    List<PropertyDescriptor> queryProperty = daInterface.stream()
      .filter( p -> p.getName().equals( "query" ) ).collect( Collectors.toList() );
    List<PropertyDescriptor> dataServiceQueryProperty = daInterface.stream()
      .filter( p -> p.getName().equals( "dataServiceQuery" ) ).collect( Collectors.toList() );
    List<PropertyDescriptor> windowModeProperty = daInterface.stream()
            .filter( p -> p.getName().equals( "windowMode" ) ).collect( Collectors.toList() );
    List<PropertyDescriptor> windowSizeProperty = daInterface.stream()
            .filter( p -> p.getName().equals( "windowSize" ) ).collect( Collectors.toList() );
    List<PropertyDescriptor> windowEveryProperty = daInterface.stream()
            .filter( p -> p.getName().equals( "windowEvery" ) ).collect( Collectors.toList() );
    List<PropertyDescriptor> windowLimitProperty = daInterface.stream()
            .filter( p -> p.getName().equals( "windowLimit" ) ).collect( Collectors.toList() );
    List<PropertyDescriptor> cacheProperty = daInterface.stream()
      .filter( p -> p.getName().equals( "cache" ) ).collect( Collectors.toList() );
    List<PropertyDescriptor> cacheDurationProperty = daInterface.stream()
      .filter( p -> p.getName().equals( "cacheDuration" ) ).collect( Collectors.toList() );
    List<PropertyDescriptor> cacheKeysProperty = daInterface.stream()
      .filter( p -> p.getName().equals( "cacheKeys" ) ).collect( Collectors.toList() );

    assertEquals( 1, dataServiceNameProperty.size() );
    assertEquals( 1, dataServiceQueryProperty.size() );
    assertEquals( 1, windowModeProperty.size() );
    assertEquals( 1, windowSizeProperty.size() );
    assertEquals( 1, windowEveryProperty.size() );
    assertEquals( 1, windowLimitProperty.size() );
    assertEquals( 0, cacheProperty.size() );
    assertEquals( 0, cacheDurationProperty.size() );
    assertEquals( 0, cacheKeysProperty.size() );
    assertEquals( 0, queryProperty.size() );
  }

  @Test
  public void testSqlReportingDataFactoryType() throws InvalidConnectionException, UnknownConnectionException {
    DataservicesConnection mockConnection = mock( DataservicesConnection.class );
    ConnectionProvider mockConnectionProvider = mock( ConnectionProvider.class );
    doReturn( mockConnectionProvider ).when( mockConnection ).getInitializedConnectionProvider();
    assertTrue( da.getSQLReportDataFactory( mockConnection ) instanceof SQLReportDataFactory );
  }
}
